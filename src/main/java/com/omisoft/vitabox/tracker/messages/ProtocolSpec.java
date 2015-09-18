package com.omisoft.vitabox.tracker.messages;

/**
 * Holds protocol key specs
 * Created by dido on 9/16/15.
 */
public class ProtocolSpec {
    /**
     * Request part
     */
    public static final String INFO_HASH="info_hash";

    public static final String PEER_ID = "peer_id";

    public static final String PORT="port";
    public static final String UPLOADED = "uploaded";
    public static final String DOWNLOADED = "downloaded";
    public static final String LEFT = "left";
    public static final String COMPACT = "compact";
    public static final String NO_PEER_ID = "no_peer_id";
    public static final String EVENT = "event";
    public static final String IP= "ip";
    public static final String NUM_WANT="numwant";
    public static final String KEY="key";
    public static final String TRACKER_ID = "trackerid";
    /**
     * Response part
     */
    public static final String FAILURE_REASON = "failure reason";
    public static final String WARNING_MESSAGE = "warning message";
    public static final String INTERVAL = "interval";
    public static final String MIN_INTERVAL = "min interval";
    public static final String COMPLETE = "complete";
    public static final String INCOMPLETE = "incomplete";
    public static final String PEERS = "peers";
    /**
     * Scrape
     */
    public static final String FILES="files";


}
