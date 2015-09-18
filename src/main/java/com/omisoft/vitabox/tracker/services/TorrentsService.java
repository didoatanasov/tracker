package com.omisoft.vitabox.tracker.services;

import com.omisoft.vitabox.tracker.messages.RequestEvent;
import com.omisoft.vitabox.tracker.torrent.Peer;
import com.omisoft.vitabox.tracker.torrent.Torrent;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dido on 9/16/15.
 */
public interface TorrentsService {
    /**
     * Add torrent
     * @param torrent
     * @param peer
     */
    void addTorrent(Torrent torrent, Peer peer);

    /**
     * Check if torrent is already added.
     * @param torrent
     * @return
     */
    boolean torrentExists(Torrent torrent);

    /**
     * Clear all torrents
     */
    void resetAllTorrents();

    /**
     * Get peers seeding a torrent
     * @param torrent
     * @return
     */
    Set<Peer> getPeers(Torrent torrent);

    /**
     * Get peers seeding a torrent by torrents info hash
     * @param infoHash
     * @return
     */
    Set<Peer> getPeers(String infoHash);

    /**
     * Get all peers
     * @return
     */
    Set<Peer> getAllPeers();

    /**
     * Clear all peers from torrent
     * @param torrent
     */
    void clearAllPeersForTorrent(Torrent torrent);

    /**
     * Get torrent by info hash
     * @return
     */
    Torrent getTorrentByHash(String hash);
    Set<Torrent> getAllTorrentsForPeer(Peer peer);



    /**
     * Delete torrent
     * @param torrent
     * @return
     */
    boolean deleteTorrent(Torrent torrent);

    /**
     * delete torrent by hash
     * @param infoHash
     * @return
     */
    boolean deleteTorrent(String infoHash);

    /**
     * Checks if peer is seeding the torrent
     * @param peer
     * @param torrent
     * @return
     */
boolean peerIsSeedingTorrent(Peer peer, Torrent torrent);

    /**
     * Get peer by providing peer id
     * @param peerId
     * @return
     */
    Peer getPeerByPeerId(String peerId);

    /**
     * Update torrent statistics.
     * @param torrent
     * @param peer
     * @param event
     */
    void updateTorrentAndPeerStatistics(Torrent torrent, Peer peer, RequestEvent event);

    List<Torrent> getAllTorrents();
}
