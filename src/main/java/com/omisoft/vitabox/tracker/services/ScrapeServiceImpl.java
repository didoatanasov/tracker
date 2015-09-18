package com.omisoft.vitabox.tracker.services;

import com.omisoft.vitabox.tracker.bcodec.BEValue;
import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.messages.ProtocolSpec;
import com.omisoft.vitabox.tracker.torrent.Torrent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements scrape service
 * Created by dido on 9/18/15.
 */
public class ScrapeServiceImpl implements ScrapeService {
    private final TorrentsService torrentService;
    @javax.inject.Inject
    public ScrapeServiceImpl(TorrentsService torrentService) {
        this.torrentService = torrentService;
    }

    @Override
    public Map<String, BEValue> generateFullScrapeMessage() {
        Map<String,BEValue> respMap = new HashMap<>();
        List<Torrent> torrentsList = torrentService.getAllTorrents();
        for (Torrent torrent : torrentsList) {
            BEValue value = generateMapForSingleTorrent(torrent);
            respMap.put(torrent.getInfoHash(),value);


        }

        return respMap;
    }

    private BEValue generateMapForSingleTorrent(Torrent torrent) {
        Map <String,BEValue> torrentsStatistics = new HashMap<>();
        torrentsStatistics.put(ProtocolSpec.COMPLETE,new BEValue(torrent.getStatistics().getComplete()));
        torrentsStatistics.put(ProtocolSpec.DOWNLOADED,new BEValue(torrent.getStatistics().getDownloaded()));
        torrentsStatistics.put(ProtocolSpec.INCOMPLETE,new BEValue(torrent.getStatistics().getIncomplete()));
        return new BEValue(torrentsStatistics);

    }

    @Override
    public Map<String, BEValue> generateSpecificScrapeMessage(String[]hashes) {
        List<Torrent> torrents = new ArrayList<>(hashes.length);
        Map<String,BEValue> respMap = new HashMap<>(hashes.length);

        for (String paramValue: hashes) {
            try {
                String hash = URLDecoder.decode(paramValue, TrackerConstants.BYTE_ENCODING);
                Torrent t = torrentService.getTorrentByHash(hash);
                if (t!=null) {

                    respMap.put(t.getInfoHash(), generateMapForSingleTorrent(t));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }


        return null;
    }
}
