package com.omisoft.vitabox.tracker.messages;

import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.torrent.Peer;
import com.omisoft.vitabox.tracker.torrent.Torrent;
import com.omisoft.vitabox.tracker.services.TorrentsService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Announce response message
 * Created by dido on 9/16/15.
 */
public class HttpAnnounceResponseMessage {
    private final String failureReason;
    private final String warningMessage;
    private final int interval;
    private final int minInterval;
    private final String trackerId;
    private final int complete;
    private final int incomplete;
//    private final byte[] peers;
    private final List<Peer> peers;

    private HttpAnnounceResponseMessage(String failureReason, String warningMessage, int interval, int minInterval, String trackerId, int complete, int incomplete, List<Peer> peers) {
        this.failureReason = failureReason;
        this.warningMessage = warningMessage;
        this.interval = interval;
        this.minInterval = minInterval;
        this.trackerId = trackerId;
        this.complete = complete;
        this.incomplete = incomplete;
        this.peers = peers;
    }

    public static HttpAnnounceResponseMessage createHttpAnnounceResponseMessage(TorrentsService service, Peer peer, Torrent torrent) {
        int interval = TrackerConstants.REQUEST_INTERVAL;
        int minInterval = TrackerConstants.REQUEST_INTERVAL;
        int complete = torrent.getNumberOfSeeders();
        int incomplete = torrent.getNumberOfLeechers();
        String trackerId = TrackerConstants.TRACKER_ID;
       // byte[] peers = getPeersAsByteArray(service, torrent, peer);
        List<Peer> peers = getPeersList(service,torrent,peer);
        HttpAnnounceResponseMessage responseMessage = new HttpAnnounceResponseMessage("","",interval,minInterval,trackerId,complete,incomplete,peers);
        return responseMessage;
    }

    private static List<Peer> getPeersList(TorrentsService service, Torrent torrent, Peer peer) {
        LinkedHashSet<Peer> peersSet = (LinkedHashSet<Peer>) service.getPeers(torrent);
        List<Peer> peers = new ArrayList<>(peersSet);
        return peers;
    }

    @Deprecated
    private static byte[] getPeersAsByteArray(TorrentsService service, Torrent torrent, Peer peer) {
       LinkedHashSet<Peer> peersSet = (LinkedHashSet<Peer>) service.getPeers(torrent);
//        peersSet.remove(peer);
        ByteBuffer byteBuffer = null;
        if (peersSet.size()<TrackerConstants.DEFAULT_NUM_PEERS) {
            byteBuffer = ByteBuffer.allocate(6 * peersSet.size());
        } else {
             byteBuffer = ByteBuffer.allocate(6 * TrackerConstants.DEFAULT_NUM_PEERS);
        }
            int i =0;
        for (Peer currentPeer : peersSet) {
            try {
                i++;
                byteBuffer.put(InetAddress.getByName(peer.getIp()).getAddress());
                byteBuffer.putShort((short)peer.getPort());
                if (i == TrackerConstants.DEFAULT_NUM_PEERS) {
                    break;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

//        peersSet.add(peer);

            return byteBuffer.array();

    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public int getInterval() {
        return interval;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public int getComplete() {
        return complete;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    @Override
    public String toString() {
        return "HttpAnnounceResponseMessage{" +
                "failureReason='" + failureReason + '\'' +
                ", warningMessage='" + warningMessage + '\'' +
                ", interval=" + interval +
                ", minInterval=" + minInterval +
                ", trackerId='" + trackerId + '\'' +
                ", complete=" + complete +
                ", incomplete=" + incomplete +
                ", peers=" + peers +
                '}';
    }
}
