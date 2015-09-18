package com.omisoft.vitabox.tracker.messages;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.messages.RequestEvent;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * Announce request message. Not BENCODED
 * Created by dido on 9/16/15.
 */
public class HttpAnnounceRequestMessage {

    private final byte[] infoHash;

     private final String peerId;

     private final int port;
    private final long uploaded;
    private final long downloaded;
    private final long left;
    private final boolean compact;
    private final boolean noPeerid;
    private final RequestEvent event;
    private final String ip;
    private final int numWant;
    private final String key;
    private final String trackerId;

    private HttpAnnounceRequestMessage(byte[] infoHash, String peerId, int port, long uploaded, long downloaded, long left, boolean compact, boolean noPeerid, RequestEvent event, String ip, int numWant, String key, String trackerId) {
        this.infoHash = infoHash;
        this.peerId = peerId;
        this.port = port;
        this.uploaded = uploaded;
        this.downloaded = downloaded;
        this.left = left;
        this.compact = compact;
        this.noPeerid = noPeerid;
        this.event = event;
        this.ip = ip;
        this.numWant = numWant;
        this.key = key;
        this.trackerId = trackerId;
    }

    public byte[] getInfoHash() {
        return infoHash;
    }

    public String getInfoHashAsHexString() {
        return Hex.encodeHexString(this.infoHash);
    }

    public String getPeerId() {
        return peerId;
    }

    public int getPort() {
        return port;
    }

    public long getUploaded() {
        return uploaded;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public long getLeft() {
        return left;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isNoPeerid() {
        return noPeerid;
    }

    public RequestEvent getEvent() {
        return event;
    }

    public String getIp() {
        return ip;
    }

    public int getNumWant() {
        return numWant;
    }

    public String getKey() {
        return key;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public static HttpAnnounceRequestMessage createHttpAnnounceRequestMessage(Map<String, String> params) throws UnsupportedEncodingException {
        int downloaded =0,left =0,uploaded =0,numwant= TrackerConstants.DEFAULT_NUM_PEERS;
        boolean noPeerID=false,compact=false;
        RequestEvent requestEvent = null;


        if (!StringUtils.isEmpty(params.get(ProtocolSpec.DOWNLOADED))) {
            downloaded = Integer.parseInt(params.get(ProtocolSpec.DOWNLOADED));
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.UPLOADED))) {
            uploaded = Integer.parseInt(params.get(ProtocolSpec.UPLOADED));
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.LEFT))) {
            left = Integer.parseInt(params.get(ProtocolSpec.LEFT));
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.NUM_WANT))) {
            numwant = Integer.parseInt(params.get(ProtocolSpec.NUM_WANT));
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.NO_PEER_ID))) {
            if ("1".equals(params.get(ProtocolSpec.NO_PEER_ID))) {
                noPeerID=true;
            }
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.COMPACT))) {
            if ("1".equals(params.get(ProtocolSpec.COMPACT))) {
                compact=true;
            }
        }
        if (!StringUtils.isEmpty(params.get(ProtocolSpec.EVENT))) {
            requestEvent = RequestEvent.valueOf(params.get(ProtocolSpec.EVENT).toUpperCase());
        }
        return new HttpAnnounceRequestMessage(params.get(ProtocolSpec.INFO_HASH).getBytes(TrackerConstants.BYTE_ENCODING),params.get(ProtocolSpec.PEER_ID),Integer.parseInt(params.get(ProtocolSpec.PORT)),uploaded,downloaded,left,compact,noPeerID,requestEvent,params.get(ProtocolSpec.IP),numwant,params.get(ProtocolSpec.KEY),params.get(ProtocolSpec.TRACKER_ID));
    }

    @Override
    public String toString() {
        return "HttpAnnounceRequestMessage{" +
                "infoHash='" + getInfoHashAsHexString() + '\'' +
                ", peerId='" + peerId + '\'' +
                ", port=" + port +
                ", uploaded=" + uploaded +
                ", downloaded=" + downloaded +
                ", left=" + left +
                ", compact=" + compact +
                ", noPeerid=" + noPeerid +
                ", event=" + event +
                ", ip='" + ip + '\'' +
                ", numWant=" + numWant +
                ", key='" + key + '\'' +
                ", trackerId='" + trackerId + '\'' +
                '}';
    }
}
