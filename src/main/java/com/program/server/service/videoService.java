package com.program.server.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class videoService {

    private Process gstProcess;

        public void startForwarding(String srcPort,String sinkPort) {
            if (gstProcess != null) {
                gstProcess.destroy();
            }
            String command = "gst-launch-1.0 udpsrc port=10000 ! application/x-rtp,encoding-name=JPEG," + "payload=26 ! rtpjpegdepay ! jpegdec ! autovideosink";
            try {
                gstProcess = Runtime.getRuntime().exec(command);
                // handle the process output or errors if needed
                System.out.println("在"+sinkPort+"端口播放");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void stopForwarding() {
            if (gstProcess != null) {
                gstProcess.destroy();
                gstProcess = null;
            }
        }
}

