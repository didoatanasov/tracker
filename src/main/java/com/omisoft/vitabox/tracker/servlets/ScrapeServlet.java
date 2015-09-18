package com.omisoft.vitabox.tracker.servlets;

import com.omisoft.vitabox.tracker.bcodec.BEValue;
import com.omisoft.vitabox.tracker.bcodec.BEncoder;
import com.omisoft.vitabox.tracker.messages.ProtocolSpec;
import com.omisoft.vitabox.tracker.services.ScrapeService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dido on 9/18/15.
 */
@Singleton
public class ScrapeServlet extends HttpServlet {
    private final ScrapeService scrapeService;
    @Inject
    public ScrapeServlet(ScrapeService scrapeService) {
        this.scrapeService=scrapeService;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String [] hashes = request.getParameterValues(ProtocolSpec.INFO_HASH);
        Map<String,BEValue> torrentScrapes  = null;
        if (hashes!=null) {
        scrapeService.generateSpecificScrapeMessage(hashes);
        } else { // full scrape
            torrentScrapes=  scrapeService.generateFullScrapeMessage();
        }
        BEValue encodedTorrentScrapes = new BEValue(torrentScrapes);
        OutputStream os = response.getOutputStream();
        Map<String,BEValue> responseMap =  new HashMap<>();
        responseMap.put(ProtocolSpec.FILES,encodedTorrentScrapes);
        BEncoder.bencode(responseMap,os);
        os.flush();
    }
}
