package com.omisoft.vitabox.tracker.torrent;

import java.util.Date;

/**
 * Created by dido on 9/17/15.
 */
public class PeerStatistics {
    private Date lastAnnounceTime;
    private long totalBytesDownloaded;
    private long totalBytesUploaded;

    public Date getLastAnnounceTime() {
        return lastAnnounceTime;
    }

    public void setLastAnnounceTime(Date lastAnnounceTime) {
        this.lastAnnounceTime = lastAnnounceTime;
    }

    public long getTotalBytesDownloaded() {
        return totalBytesDownloaded;
    }

    public void setTotalBytesDownloaded(long totalBytesDownloaded) {
        this.totalBytesDownloaded = totalBytesDownloaded;
    }

    public long getTotalBytesUploaded() {
        return totalBytesUploaded;
    }

    public void setTotalBytesUploaded(long totalBytesUploaded) {
        this.totalBytesUploaded = totalBytesUploaded;
    }
}
