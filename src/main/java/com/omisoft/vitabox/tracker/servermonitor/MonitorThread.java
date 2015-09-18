package com.omisoft.vitabox.tracker.servermonitor;

/**
 * Created by dido on 9/5/15.
 */

import org.eclipse.jetty.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class stops the server. To use send a single line to the socket
 */
public class MonitorThread extends Thread {

    private ServerSocket socket;
    private Server server;

    public MonitorThread(Server serverInstance) {
        server  = serverInstance;
        setDaemon(true);
        setName("StopMonitor");
        try {
            socket = new ServerSocket(8079, 1, InetAddress.getByName("127.0.0.1"));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Socket accept;
        try {
            accept = socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            reader.readLine();
            System.out.println("*** stopping jetty embedded server");
            server.stop();
            accept.close();
            socket.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

