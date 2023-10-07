package com.program.server.service;

import org.springframework.stereotype.Component;

@Component
public class RtspService {
    static {
        System.loadLibrary("RtspService");
    }
    public native int startServer(String srcport,String sinkport,String msg);
    public native void stopServer();
}