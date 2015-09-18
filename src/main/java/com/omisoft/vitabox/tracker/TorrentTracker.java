package com.omisoft.vitabox.tracker;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.omisoft.vitabox.tracker.config.ApplicationServletModule;
import com.omisoft.vitabox.tracker.config.DependencyModule;
import com.omisoft.vitabox.tracker.constants.TrackerConstants;
import com.omisoft.vitabox.tracker.servermonitor.MonitorThread;
import com.omisoft.vitabox.tracker.servlets.AnnounceServlet;
import com.omisoft.vitabox.tracker.servlets.HomeServlet;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.servlets.GzipFilter;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Main class
 */
public class TorrentTracker {


    public static void main(String[] args) throws IOException {


        initJetty();
    }

    /**
     * Init jetty
     */
    private static void initJetty() {
        //Setup Jetty
        Server server = new Server();

//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(TrackerConstants.PORT);
        HttpConfiguration httpConfig = new HttpConfiguration();
        HttpConnectionFactory httpFactory = new HttpConnectionFactory( httpConfig );
        ServerConnector httpConnector = new ServerConnector( server,httpFactory );
        httpConnector.setPort(TrackerConstants.PORT);
        server.setConnectors(new Connector[] {httpConnector});

//        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(server,"/",ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));



//        // Register servlet
//        context.addServlet(new ServletHolder(new HomeServlet()), "/");
        // Add the filter, and then use the provided FilterHolder to configure it
        GzipHandler gzipHandlerRES = new GzipHandler();
        gzipHandlerRES.setHandler(context);
        context.addFilter(CrossOriginFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

//        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
//        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
//        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
//        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
//        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");

        // You MUST add DefaultServlet or your server will always return 404s
        context.addServlet(DefaultServlet.class, "/");
        // Init guice
        initGuice();
        try {
            server.setHandler(context);
            Thread monitor = new MonitorThread(server);
            monitor.start();
            server.start();
            server.join();

        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void initGuice() {
        DependencyModule dependencyModule = new DependencyModule();
        ApplicationServletModule applicationServletModule = new ApplicationServletModule();
        Injector injector = Guice.createInjector(dependencyModule, applicationServletModule);
    }

}
