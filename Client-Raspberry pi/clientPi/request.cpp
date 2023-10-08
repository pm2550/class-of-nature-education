#include"request.h"
#include<iostream>
#include <nlohmann/json.hpp>
using namespace std;
using json = nlohmann::json;


string ReplyToJsonString(const sendReply& msg) {
    json data;
    data["videoMsg"] = msg.videoMsg;
    data["sensorMsg"] = msg.sensorMsg;
    data["type"] = "reply";
    data["port"] = msg.port;
    data["target"] = msg.target;
    data["source"]=msg.source;
    return data.dump(); 
}
