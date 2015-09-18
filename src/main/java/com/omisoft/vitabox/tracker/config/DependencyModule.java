package com.omisoft.vitabox.tracker.config;

import com.google.inject.AbstractModule;
import com.omisoft.vitabox.tracker.services.ScrapeService;
import com.omisoft.vitabox.tracker.services.ScrapeServiceImpl;
import com.omisoft.vitabox.tracker.services.TorrentsService;
import com.omisoft.vitabox.tracker.services.TorrentsServiceImpl;

/**
 * Created by dido on 9/18/15.
 */
public class DependencyModule extends AbstractModule{
    protected void configure() {
        bind(TorrentsService.class).to(TorrentsServiceImpl.class);
        bind(ScrapeService.class).to(ScrapeServiceImpl.class);
    }
}
