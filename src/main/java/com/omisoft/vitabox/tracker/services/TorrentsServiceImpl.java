package com.omisoft.vitabox.tracker.services;

import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.messages.RequestEvent;
import com.omisoft.vitabox.tracker.torrent.Peer;
import com.omisoft.vitabox.tracker.torrent.Torrent;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dido on 9/15/15.
 */
@Singleton
public class TorrentsServiceImpl implements TorrentsService{

    private static TorrentsService service;
    /**
     * The torrent hash is the key.
     */
    private final Map<Torrent,Set<Peer>> hostedTorrents;
    private final Map <Peer,Set<Torrent>> peers;


    public TorrentsServiceImpl() {
        hostedTorrents = new ConcurrentHashMap<Torrent, Set<Peer>>(TrackerConstants.INITIAL_TORRENTS_COUNT);
        peers = new ConcurrentHashMap<Peer, Set<Torrent>>(TrackerConstants.INITIAL_TORRENTS_COUNT);;
    }
    public void addTorrent(Torrent torrent, Peer peer) {
        torrent.increaseNumberOfLeechers();
        if (!torrentExists(torrent)) {
            LinkedHashSet peers = new LinkedHashSet<Peer>(TrackerConstants.DEFAULT_NUM_PEERS);
            peers.add(peer);

            hostedTorrents.put(torrent, peers);
            addTorrentToPeer(peer, torrent);

        } else {
            addPeerToTorrent(torrent, peer);
        }
    }

    private void addTorrentToPeer(Peer peer, Torrent torrent) {
        if (peers.containsKey(peer)) {
            peers.get(peer).add(torrent);
        } else {
            Set<Torrent> torrentSet = new LinkedHashSet<Torrent>();
            torrentSet.add(torrent);
            peers.put(peer, torrentSet);
        }
    }

    public boolean torrentExists(Torrent torrent) {
        return hostedTorrents.containsKey(torrent);
    }


    public void resetAllTorrents() {

    hostedTorrents.clear();
        peers.clear();
    }



    public Set<Peer> getPeers(Torrent torrent) {
        return hostedTorrents.get(torrent);
    }

    public Set<Peer> getPeers(String infoHash) {
        for (Torrent t : hostedTorrents.keySet()) {
            if (infoHash.equals(t.getInfoHash())) {
                return hostedTorrents.get(t);
            }
        }
        return null;
    }

    public Set<Peer> getAllPeers() {

        return peers.keySet();
    }

    public void clearAllPeersForTorrent(Torrent torrent) {
        hostedTorrents.remove(torrent);

    }

    public Torrent getTorrentByHash(String infoHash) {
        for (Torrent t : hostedTorrents.keySet()) {
            if (infoHash.equals(t.getInfoHash())) {
                return t;
            }
        }
        return null;

    }


    public Set<Torrent> getAllTorrentsForPeer(Peer peer) {
        return peers.get(peer);
    }

    private void addPeerToTorrent(Torrent torrent, Peer peer) {

        hostedTorrents.get(torrent).add(peer);


    }

    public boolean deleteTorrent(Torrent torrent) {

        return false;
    }

    public boolean deleteTorrent(String infoHash) {
        return false;
    }

    public boolean peerIsSeedingTorrent(Peer peer, Torrent torrent) {
        if (peers.get(peer).contains(torrent)) {
            return true;
        } else {
            return false;
        }
    }

    public Peer getPeerByPeerId(String peerId) {
        for (Peer peer : peers.keySet()) {
            if (peerId.equals(peer.getPeerId())) {
                return peer;
            }
        }
        return null;
    }

    public void updateTorrentAndPeerStatistics(Torrent torrent, Peer peer,RequestEvent event) {
        if (RequestEvent.COMPLETED.equals(event)) {
            torrent.increaseNumberOfSeeders();
        }
    }

    @Override
    public List<Torrent> getAllTorrents() {
        List<Torrent> torrentList = new ArrayList<>(hostedTorrents.keySet());
        return torrentList;
    }


}