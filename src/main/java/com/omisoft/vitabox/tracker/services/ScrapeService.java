package com.omisoft.vitabox.tracker.services;

import com.omisoft.vitabox.tracker.bcodec.BEValue;

import java.util.Map;

/**
 * Created by dido on 9/18/15.
 */
public interface ScrapeService {

    Map<String,BEValue> generateFullScrapeMessage();
    Map<String,BEValue> generateSpecificScrapeMessage(String[] hashes);

}
