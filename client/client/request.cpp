#include"request.h"
#include<iostream>
#include <json.hpp>
using namespace std;
using json = nlohmann::json;


string OrderToJsonString(const sendOrder& msg) {
    json data;
    data["msg"] = msg.orderMsg;
    data["type"] = "order";
    data["target"] = msg.target;
    return data.dump(); // 使用dump()将JSON对象转换为字符串
}