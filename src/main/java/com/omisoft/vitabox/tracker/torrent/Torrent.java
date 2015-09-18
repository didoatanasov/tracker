package com.omisoft.vitabox.tracker.torrent;

/**
 * Created by dido on 9/15/15.
 */
public class Torrent {

  private final String infoHash;
  private int numberOfSeeders;
  private int numberOfLeechers;
  private final TorrentStatistics statistics;

    public Torrent(String infoHash) {
        this.infoHash = infoHash;
        numberOfLeechers = 0;
        numberOfSeeders = 0;
        statistics = new TorrentStatistics();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Torrent torrent = (Torrent) o;

        return !(infoHash != null ? !infoHash.equals(torrent.infoHash) : torrent.infoHash != null);

    }

    @Override
    public int hashCode() {
        return infoHash != null ? infoHash.hashCode() : 0;
    }

    public String getInfoHash() {
        return infoHash;
    }


    public int getNumberOfSeeders() {
        return numberOfSeeders;
    }


    public int getNumberOfLeechers() {
        return numberOfLeechers;
    }


    synchronized public void increaseNumberOfLeechers() {
        this.numberOfLeechers++;
    }
    synchronized public void   increaseNumberOfSeeders() {
        this.numberOfSeeders++;
    }


    synchronized public void decreaseNumberOfLeechers() {
        this.numberOfLeechers++;
    }
    synchronized public void   decreaseNumberOfSeeders() {
        this.numberOfSeeders++;
    }

    public TorrentStatistics getStatistics() {
        return statistics;
    }
}
