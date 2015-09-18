package com.omisoft.vitabox.tracker.servlets;

import com.omisoft.vitabox.tracker.bcodec.BEValue;
import com.omisoft.vitabox.tracker.bcodec.BEncoder;
import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.exceptions.MessageValidationException;
import com.omisoft.vitabox.tracker.messages.HttpAnnounceResponseMessage;
import com.omisoft.vitabox.tracker.messages.HttpAnnounceRequestMessage;
import com.omisoft.vitabox.tracker.messages.ProtocolSpec;
import com.omisoft.vitabox.tracker.torrent.Peer;
import com.omisoft.vitabox.tracker.torrent.Torrent;
import com.omisoft.vitabox.tracker.services.TorrentsService;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by dido on 9/4/15.
 * Serves home page
 */
@Singleton
public class AnnounceServlet extends HttpServlet {
    private final TorrentsService torrentsService;
    @Inject
    public AnnounceServlet(TorrentsService torrentsService) {

        this.torrentsService = torrentsService;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding(TrackerConstants.BYTE_ENCODING);
        try {
            System.out.println(request.getQueryString());
            HttpAnnounceRequestMessage requestMessage = parseQuery(request);

            System.out.println(requestMessage);

            Peer peer = addTorrentToTrackerService(requestMessage);
            HttpAnnounceResponseMessage responseMessage = generateResponseMessage(requestMessage,peer);
            System.out.println("RESPONSE MESSAGE:" + responseMessage);
            OutputStream os = response.getOutputStream();
            bencodeResponse(responseMessage, os);
            os.flush();

        } catch (MessageValidationException e) {
            e.printStackTrace();
        }


    }

    private void bencodeResponse(HttpAnnounceResponseMessage responseMessage, OutputStream os) {
        try {
            Map<String, BEValue> respDictionary = new HashMap<String, BEValue>();
            if (!StringUtils.isEmpty(responseMessage.getFailureReason())) {
                respDictionary.put(ProtocolSpec.FAILURE_REASON, new BEValue(responseMessage.getFailureReason()));
            }
            if (!StringUtils.isEmpty(responseMessage.getWarningMessage())) {
                respDictionary.put(ProtocolSpec.WARNING_MESSAGE, new BEValue(responseMessage.getWarningMessage()));

            }
            respDictionary.put(ProtocolSpec.INTERVAL,new BEValue(responseMessage.getInterval()));
            respDictionary.put(ProtocolSpec.MIN_INTERVAL,new BEValue(responseMessage.getMinInterval()));
            respDictionary.put(ProtocolSpec.COMPLETE,new BEValue(responseMessage.getComplete()));
            respDictionary.put(ProtocolSpec.INCOMPLETE,new BEValue(responseMessage.getComplete()));
            List<BEValue> peersEncodedList = new ArrayList<BEValue>(responseMessage.getPeers().size());
            for (Peer peer : responseMessage.getPeers()) {
                Map<String,BEValue> encodingMap = new HashMap<>(3);
                encodingMap.put(ProtocolSpec.PEER_ID,new BEValue(peer.getPeerId()));
                encodingMap.put(ProtocolSpec.IP,new BEValue(peer.getIp()));
                encodingMap.put(ProtocolSpec.PORT,new BEValue(peer.getPort()));
            BEValue value = new BEValue(encodingMap);
                peersEncodedList.add(value);
            }


            respDictionary.put(ProtocolSpec.PEERS,new BEValue(peersEncodedList));
            BEncoder.bencode(respDictionary,os);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Peer addTorrentToTrackerService(HttpAnnounceRequestMessage requestMessage) {
        Torrent torrent = torrentsService.getTorrentByHash(requestMessage.getInfoHashAsHexString());
        Peer peer = torrentsService.getPeerByPeerId(requestMessage.getPeerId());
        if (torrent != null && peer != null) {
            System.out.print("WE HAVE BOTH PEER AND TORRENT");
            torrentsService.updateTorrentAndPeerStatistics(torrent,peer,requestMessage.getEvent());
        } else if (torrent != null && peer == null) {
            peer = new Peer(requestMessage.getPort(), requestMessage.getIp(), requestMessage.getPeerId());
        } else { // both are null
            peer = new Peer(requestMessage.getPort(), requestMessage.getIp(), requestMessage.getPeerId());
            torrent = new Torrent(requestMessage.getInfoHashAsHexString());
        }
        // Update statistics. Push to service
        torrentsService.addTorrent(torrent, peer);
        return peer;
    }

    private HttpAnnounceResponseMessage generateResponseMessage(HttpAnnounceRequestMessage requestMessage, Peer peer) {
        // TODO Refactor
        HttpAnnounceResponseMessage message= HttpAnnounceResponseMessage.createHttpAnnounceResponseMessage(torrentsService, peer, torrentsService.getTorrentByHash(requestMessage.getInfoHashAsHexString()));
        return message;
    }


    private HttpAnnounceRequestMessage parseQuery(HttpServletRequest request)

            throws IOException, MessageValidationException {
        Map<String, String> params = new HashMap<String, String>();

        Enumeration<String> paramsEnumeration = request.getParameterNames();
        while (paramsEnumeration.hasMoreElements()) {
            String paramName = paramsEnumeration.nextElement();
            params.put(paramName, URLDecoder.decode(request.getParameter(paramName), TrackerConstants.BYTE_ENCODING));
        }

        // Always set the ip from the request
        params.put("ip",
                getIpAddress(request));


        return HttpAnnounceRequestMessage.createHttpAnnounceRequestMessage(params);
    }


    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("Remote_Addr");
        if (StringUtils.isEmpty(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (StringUtils.isEmpty(ipAddress)) {
                ipAddress = request.getRemoteAddr();

            }

        }
        return ipAddress;
    }

}
