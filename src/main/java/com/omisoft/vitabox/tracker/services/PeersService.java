package com.omisoft.vitabox.tracker.services;

import com.omisoft.vitabox.tracker.torrent.Peer;

/**
 * Created by dido on 9/17/15.
 */
public interface PeersService {

    void updatePeerStats(Peer peer);
}
