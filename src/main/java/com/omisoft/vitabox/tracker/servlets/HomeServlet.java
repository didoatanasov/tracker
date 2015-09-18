package com.omisoft.vitabox.tracker.servlets;

import com.omisoft.vitabox.tracker.constants.TrackerConstants;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dido on 9/4/15.
 * Serves home page
 */
@Singleton
public class HomeServlet extends HttpServlet {
    @Inject
    public HomeServlet() {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(TrackerConstants.BYTE_ENCODING);
        response.getWriter().print("VITABOX TRACKER");


    }
}
