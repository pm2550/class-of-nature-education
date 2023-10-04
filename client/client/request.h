#ifndef REQUEST_H
#define REQUEST_H

#include <string>

#define VIDEO_OPEN "open"
#define VIDEO_CLOSE "close"
#define GO_UP "goUp"
#define GO_DOWN "goDown"
#define GO_LEFT "goLeft"
#define GO_RIGHT "goRight"


struct sendOrder {
    std::string orderType;
    std::string orderMsg;
    std::string orderTo;
    std::string type;
    std::string target;
};

std::string OrderToJsonString(const sendOrder& msg);

#endif // REQUEST_H