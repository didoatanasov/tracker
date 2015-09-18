package com.omisoft.vitabox.tracker.config;

import com.google.common.collect.Maps;
import com.google.inject.servlet.ServletModule;
import com.omisoft.vitabox.tracker.filters.GZipServletFilter;
import com.omisoft.vitabox.tracker.servlets.AnnounceServlet;
import com.omisoft.vitabox.tracker.servlets.HomeServlet;
import com.omisoft.vitabox.tracker.servlets.ScrapeServlet;
import org.eclipse.jetty.servlets.GzipFilter;

import javax.servlet.Servlet;
import java.util.Map;

/**
 * Holds servlet mappings
 * Created by dido on 9/18/15.
 */

public class ApplicationServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(AnnounceServlet.class);
        bind(ScrapeServlet.class);
        bind(HomeServlet.class);

        serve("/announce").with(AnnounceServlet.class);
        serve("/scrape").with(ScrapeServlet.class);
        serve("/*").with(HomeServlet.class);
        /**
         * Configure GZIp
         */
        final Map<String, String> params = Maps.newHashMap();
        params.put("mimeTypes", "text/html,text/xml,text/plain,application/javascript,application/json");
        params.put("methods", "GET,PUT,POST,DELETE");
        params.put("deflateCompressionLevel", "9");
        filter("/*").through(GZipServletFilter.class);
    }
}