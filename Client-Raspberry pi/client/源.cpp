#include <websocketpp/config/asio_no_tls_client.hpp>
#include <websocketpp/client.hpp>
#include <iostream>
#include<string>
#include "hello.h"
#include <json.hpp>
#include "request.h"
using namespace std;
using json = nlohmann::json;
typedef websocketpp::client<websocketpp::config::asio_client> client;

using websocketpp::lib::placeholders::_1;
using websocketpp::lib::placeholders::_2;
using websocketpp::lib::bind;
std::condition_variable cv;
std::mutex mtx;
bool connected = false;

// pull out the type of messages sent by our config
typedef websocketpp::config::asio_client::message_type::ptr message_ptr;

// This message handler will be invoked once for each incoming message. It
// prints the message and then sends a copy of the message back to the server.
void on_message(client* c, websocketpp::connection_hdl hdl, message_ptr msg) {
    std::cout << "on_message called with hdl: " << hdl.lock().get()
        << " and message: " << msg->get_payload()
        << std::endl;
}

void send_order(client* c, websocketpp::connection_hdl con_hdl) {
    websocketpp::lib::error_code ec;
        char a;
        sendOrder Msg; // WebSocket端点路径
        cout << "请输入指令：1.open 2.close 3.向上 4.向下 5.向左 6.向右";
        while (cin >> a)
        {
            switch (a)
            {
            case '1':Msg.orderMsg = VIDEO_OPEN; break;
            case '2':Msg.orderMsg = VIDEO_CLOSE; break;
            case '3':Msg.orderMsg = GO_UP; break;
            case '4':Msg.orderMsg = GO_DOWN; break;
            case '5':Msg.orderMsg = GO_LEFT; break;
            case '6':Msg.orderMsg = GO_RIGHT; break;
            default:cout << "输入错误\n";
            }

            Msg.target = "camera";
            Msg.source = "player";
            string orderMessage = OrderToJsonString(Msg);
            c->send(con_hdl, orderMessage, websocketpp::frame::opcode::text, ec);
            if (ec) {
                std::cout << "发送消息到 /hello 失败: " << ec.message() << std::endl;
            }
            else
                std::cout << "发送消息到 /hello 成功: " << ec.message() << std::endl;
            std::this_thread::sleep_for(std::chrono::seconds(1));
        }
}
void send_hello(client* c, websocketpp::connection_hdl con_hdl) {
    websocketpp::lib::error_code ec;

   sendHello Msg; // WebSocket端点路径
    Msg.name = "player";
    Msg.user = "machine";
    Msg.msg = "hello";
    string helloMessage = helloToJsonString(Msg);
    c->send(con_hdl, helloMessage, websocketpp::frame::opcode::text, ec);
    if (ec) {
        std::cout << "发送消息到 /hello 失败: " << ec.message() << std::endl;
    }
    else
        std::cout << "发送消息到 /hello 成功: " << ec.message() << std::endl;
    std::this_thread::sleep_for(std::chrono::seconds(1));
}

void on_open(client* c, websocketpp::connection_hdl hdl) {
    std::cout << "连接成功\n";
    websocketpp::lib::error_code ec;
    std::string msg = "hello";
    c->send(hdl, msg, websocketpp::frame::opcode::text,ec);
    if (ec) {
        std::cout << "初始消息发送失败: " << ec.message() << std::endl;
    }
    connected = true;
}

int main(int argc, char* argv[]) {
    // Create a client endpoint
    client c;
    std::string uri = "ws://localhost:8082/hello";

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
        c.set_message_handler(bind(&on_message, &c, ::_1, ::_2));

        websocketpp::lib::error_code ec;
        std::cout << "开始连接\n";
        client::connection_ptr con = c.get_connection(uri, ec);
        if (ec) {
            std::cout << "could not create connection because: " << ec.message() << std::endl;
            return 0;
        }
        c.set_open_handler(bind(&on_open, &c,:: _1));

        // 请求连接
        c.connect(con);
 
        std::cout << "开始运行";
        // 运行事件循环以处理消息

        std::thread t([&c]() {
            c.run();
            });

        // Wait for the WebSocket connection to be established
        std::this_thread::sleep_for(std::chrono::seconds(1));


        // 发送到 /hello 路径
        std::thread hi(send_hello, &c, con->get_handle());

        std::thread order(send_order, &c, con->get_handle());
            // 休眠一段时间后再发送下一条消息
       std::this_thread::sleep_for(std::chrono::seconds(1));

        // Join the thread when done
        t.join();
        std::this_thread::sleep_for(std::chrono::seconds(2));
        hi.join();
        std::this_thread::sleep_for(std::chrono::seconds(2));
        order.join();
        // 等待线程结束
       
     
    }
    catch (websocketpp::exception const& e) {
        std::cout << e.what() << std::endl;
    }
}