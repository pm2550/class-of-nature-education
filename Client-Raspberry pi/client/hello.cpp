#include "hello.h"
#include<iostream>
#include<string>
#include <json.hpp>
using namespace std;
using json = nlohmann::json;


string helloToJsonString(const sendHello& msg) {
    json data;
    data["name"] = msg.name;
    data["msg"] = msg.msg;
    data["user"] = msg.user;
    data["type"] = "hello";
    return data.dump(); // ʹ��dump()��JSON����ת��Ϊ�ַ���
}
