package com.omisoft.vitabox.tracker.torrent;

/**
 * Created by dido on 9/18/15.
 */
public class TorrentStatistics {
    int complete;
    int downloaded;
    int incomplete;

    public TorrentStatistics() {
        complete=0;
        downloaded=0;
        incomplete=0;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(int incomplete) {
        this.incomplete = incomplete;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }
}
