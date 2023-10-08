#ifndef REQUEST_H
#define REQUEST_H

#include <string>

#define VIDEO_OPEN "open"
#define VIDEO_CLOSE "close"
#define GO_UP "goUp"
#define GO_DOWN "goDown"
#define GO_LEFT "goLeft"
#define GO_RIGHT "goRight"


struct sendReply {
    std::string videoMsg;
    std::string sensorMsg;
    std::string source;
    std::string type;
    std::string target;
     std::string port;
};

std::string ReplyToJsonString(const sendReply& msg);

#endif // REQUEST_H
