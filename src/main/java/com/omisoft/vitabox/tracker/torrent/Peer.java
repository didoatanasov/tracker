package com.omisoft.vitabox.tracker.torrent;

import java.util.Date;

/**
 * Holds one peer
 * Created by dido on 9/15/15.
 */
public class Peer {
    private String ip;
    private String peerId;
    private int port;
    private final PeerStatistics statistics;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Peer(int port, String ip, String peerId) {
        this.port = port;
        this.ip = ip;
        this.peerId = peerId;
        this.statistics= new PeerStatistics();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peer peer = (Peer) o;

        if (port != peer.port) return false;
        if (!ip.equals(peer.ip)) return false;
        return peerId.equals(peer.peerId);

    }

    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + peerId.hashCode();
        result = 31 * result + port;
        return result;
    }


}
