package com.program.server.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
@Service
public class videoService {

    private Pipeline pipeline;
    private Element source, convert, encoder, pay, sink;



        public void setupPipeline(String url,String port) {
            Gst.init();
            pipeline = new Pipeline();
            Element udpsrc = ElementFactory.make("udpsrc", "udpsrc");
            udpsrc.set("port", 5000);

            Element udpsink = ElementFactory.make("udpsink", "udpsink");
            udpsink.set("host", "192.168.0.101");
            udpsink.set("port", 6000);

            pipeline.addMany(udpsrc, udpsink);
            Pipeline.linkMany(udpsrc, udpsink);
        }

        public void startForwarding() {
            pipeline.play();
        }

        public void stopForwarding() {
            pipeline.stop();
        }
}

