#include <gst/gst.h>
#include <string>
#include <iostream>
using namespace std;

class videoTrans{
	GstElement *pipeline;
     GstBus* bus;
     public:
           void pushVideo(string url,string port,int argc, char* argv[]);
           void startVideo();
            void stopVideo();
     static void error_cb(GstBus* bus, GstMessage* msg, gpointer user_data);
     static void eos_cb(GstBus* bus, GstMessage* msg, gpointer user_data);
	};
