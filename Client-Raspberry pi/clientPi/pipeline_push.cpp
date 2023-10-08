#include <gst/gst.h>
#include <string>
#include <iostream>
#include "pipeline_push.h"
#include <thread>
using namespace std;

void videoTrans::error_cb(GstBus* bus, GstMessage* msg, gpointer user_data) {
	g_printerr("error");
    GMainLoop* loop = (GMainLoop*)user_data;
    g_main_loop_quit(loop);
}

void videoTrans::eos_cb(GstBus* bus, GstMessage* msg, gpointer user_data) {
	g_printerr("eos");
    GMainLoop* loop = (GMainLoop*)user_data;
    g_main_loop_quit(loop);
}

void videoTrans::pushVideo(string url,string port,int argc, char* argv[])
{ 
    GstElement * source, * convert, * encoder, * pay, * sink;
    //GstMessage* msg;

    gst_init(&argc, &argv);

    // 创建GStreamer元素
    this->pipeline = gst_pipeline_new("sender-pipeline");
    source = gst_element_factory_make("v4l2src", "source");
    convert = gst_element_factory_make("videoconvert", "convert");
    encoder = gst_element_factory_make("jpegenc", "encoder");
    pay = gst_element_factory_make("rtpjpegpay", "pay");
    sink = gst_element_factory_make("udpsink", "sink");

    if (!this->pipeline || !source || !convert || !encoder || !pay || !sink) {
        g_printerr("Not all elements could be created.\n");
        return;
    }

    g_object_set(source, "device", "/dev/video0", NULL);
    if(!port.empty())
    g_object_set(sink, "host", url.c_str(), "port", std::stoi(port), NULL);
    else
    g_object_set(sink, "host", url.c_str(), "port", 10000, NULL);
    gst_bin_add_many(GST_BIN(this->pipeline), source, convert, encoder, pay, sink, NULL);
    gst_element_link_many(source, convert, encoder, pay, sink, NULL);
    g_printerr("connected successfully\n");
}

void videoTrans::startVideo()
{
     gst_element_set_state(this->pipeline, GST_STATE_PLAYING);
GstState current_state, pending_state;
gst_element_get_state(this->pipeline, &current_state, &pending_state, GST_CLOCK_TIME_NONE);
g_printerr("Pipeline state: %s (pending: %s)\n", gst_element_state_get_name(current_state), gst_element_state_get_name(pending_state));
    GMainLoop* loop = g_main_loop_new(NULL, FALSE);

    this->bus = gst_element_get_bus(this->pipeline);
    gst_bus_add_signal_watch(bus);
    g_signal_connect(bus, "message::error", G_CALLBACK(error_cb), loop);
    g_signal_connect(bus, "message::eos", G_CALLBACK(eos_cb), loop);
  std::thread loopThread([&](){
       g_main_loop_run(loop); 
  });
  loopThread.detach();
   g_printerr("start to push.\n");

}

void videoTrans::stopVideo()
{
     g_printerr("stop to push.\n");
    if (this->pipeline) {
        gst_element_set_state(pipeline, GST_STATE_NULL);
        gst_object_unref(pipeline);
        pipeline = NULL;
    }
    if (this->bus) {
        gst_bus_remove_signal_watch(bus);
        gst_object_unref(bus);
        bus = NULL;
    }
}
