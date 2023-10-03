#ifndef MESSAGE_H
#define MESSAGE_H

#include <string>


struct sendHello {
    std::string name;
    std::string msg;
    std::string type;
    std::string user;
};

std::string helloToJsonString(const sendHello& msg);

#endif // MESSAGE_H