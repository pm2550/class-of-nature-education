#include <websocketpp/config/asio_no_tls_client.hpp>
#include <websocketpp/client.hpp>
#include <iostream>
#include <string>
#include <gst/gst.h>
#include "hello.h"
#include <json.hpp>
#include "request.h"
#include "pipeline_push.h"
using namespace std;
using json = nlohmann::json;
typedef websocketpp::client<websocketpp::config::asio_client> client;

using websocketpp::lib::placeholders::_1;
using websocketpp::lib::placeholders::_2;
using websocketpp::lib::bind;
std::condition_variable cv;
std::mutex mtx;
std::queue<json> messageQueue;
bool connected = false;
client c;
websocketpp::lib::error_code ec;
    std::string uri = "ws://192.168.20.102:8082/hello";
    std::string videoSrc="ws://192.168.20.102:8082/sendReply";
// pull out the type of messages sent by our config
typedef websocketpp::config::asio_client::message_type::ptr message_ptr;

// This message handler will be invoked once for each incoming message. It
// prints the message and then sends a copy of the message back to the server.
void send_reply(client* c, websocketpp::connection_hdl hdl, json jsonmsg,int argc, char* argv[]) 
{
    websocketpp::lib::error_code ec;
        string a;
        sendReply Msg; 
       static videoTrans *v=new videoTrans;
        cout<<"replying\n"<<std::endl;
        a=jsonmsg["msg"];
           if(a.find("video send to:")!=std::string::npos)
          { 
            string prefix="video send to:";
            string portStr = a.substr(prefix.length());
    
            Msg.source="machine";
            Msg.port=portStr;
            Msg.type="reply";
            Msg.target=jsonmsg["source"];
            string stringMsg=ReplyToJsonString(Msg);
            std::cout<<"send reply:"+stringMsg<<endl;
           c->send(hdl, stringMsg, websocketpp::frame::opcode::text, ec);
            if (ec) {
        std::cout << "failed to send reply\n" << ec.message() << std::endl;
    }
            v->pushVideo("192.168.20.102",portStr,argc, argv);
            v->startVideo();
        }
           else if(a.find("stop video")!=std::string::npos)
         { 
           v->stopVideo();
       }
           else
           cout << "wrong msg\n"<<std::endl;
           
            std::this_thread::sleep_for(std::chrono::seconds(1));
        
}
void on_message(client* c, websocketpp::connection_hdl hdl, message_ptr msg,int argc, char* argv[]) {
    std::cout << "on_message called with hdl: " << hdl.lock().get()
        << " and message: " << msg->get_payload()
        << std::endl;
        try{
      json jsonMsg=json::parse(msg->get_payload());
         if (jsonMsg["type"]=="order")
       {   std::lock_guard<std::mutex> lock(mtx);
            messageQueue.push(jsonMsg);
            cv.notify_one();
        }
    }catch(const std::exception &e)
    {cout<<"none";
      }
   


}

void video_process(client* c, websocketpp::connection_hdl hdl, int argc, char* argv[]) 
{
    while(true){
        std::unique_lock<std::mutex> lock(mtx);
        cv.wait(lock,[]{return !messageQueue.empty();});
        json jsonmsg=messageQueue.front();
        messageQueue.pop();
        lock.unlock();
        send_reply(c, hdl,jsonmsg, argc, argv);
    }
}
void send_hello(client* c, websocketpp::connection_hdl con_hdl) {

   sendHello Msg; 
    Msg.name = "camera";
    Msg.user = "machine";
    Msg.msg = "hello";
    string helloMessage = helloToJsonString(Msg);
    c->send(con_hdl, helloMessage, websocketpp::frame::opcode::text, ec);
    if (!ec) {
        std::cout << "succeeded to send hello\n" << ec.message() << std::endl;
    }
    else
        std::cout << "failed to send hello\n" << ec.message() << std::endl;
    std::this_thread::sleep_for(std::chrono::seconds(1));
}

void on_open(client* c, websocketpp::connection_hdl hdl) {
    std::cout << "success---------------------------\n"<<std::endl;
    std::string msg = "hello";
    c->send(hdl, msg, websocketpp::frame::opcode::text,ec);
    if (ec) {
        std::cout << "failure" << ec.message() << std::endl;
    }
    connected = true;
}

int main(int argc, char* argv[]) {
    if (argc == 2) {
        uri = argv[1];
    }

    try {
        // Set logging to be pretty verbose (everything except message payloads)
        c.set_access_channels(websocketpp::log::alevel::all);
        c.clear_access_channels(websocketpp::log::alevel::frame_payload);
 
        // Initialize ASIO
        c.init_asio();

        // Register our message handler
        c.set_message_handler(bind(&on_message, &c, ::_1, ::_2, argc, argv));

        
        std::cout << "start to connect"<<std::endl;
        client::connection_ptr con = c.get_connection(uri, ec);
        if (ec) {
            std::cout << "could not create connection because: " << ec.message() << std::endl;
            return 0;
        }
        c.set_open_handler(bind(&on_open, &c,:: _1));

        // 请求连接
        c.connect(con);
         client::connection_ptr conVideo = c.get_connection(videoSrc, ec);
             if (ec) {
            std::cout << "could not create video connection because: " << ec.message() << std::endl;
        }
            c.connect(conVideo);
        
        std::cout << "succeed to connect"<<std::endl;
        // 运行事件循环以处理消息

        std::thread t([&c]() {
            c.run();
            });

        // Wait for the WebSocket connection to be established
        std::this_thread::sleep_for(std::chrono::seconds(1));


        // 发送到 /hello 路径
        std::thread hi(send_hello, &c, con->get_handle());

       std::thread videoThread(video_process, &c, conVideo->get_handle(), argc, argv);
            // 休眠一段时间后再发送下一条消息
       std::this_thread::sleep_for(std::chrono::seconds(1));

        // Join the thread when done
        hi.join();
            std::this_thread::sleep_for(std::chrono::seconds(2));
        videoThread.detach();
        t.join();
        std::this_thread::sleep_for(std::chrono::seconds(2));
        // 等待线程结束
       
     
    }
    catch (websocketpp::exception const& e) {
        std::cout << e.what() << std::endl;
    }
}
