package com.program.server.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramSocket;

@Component
public class webService {
    static int port=10000;
    public int selectAvailablePort() {
        if(port==20000)
            port=10000;
        for (; port < 20000; port++) {
            try (DatagramSocket socket = new DatagramSocket(port)) {
                port++;
                return port;  // 如果成功，则这个端口是可用的
            } catch (IOException e) {
                // 如果捕获到异常，则此端口不可用，尝试下一个端口
            }
        }
        throw new RuntimeException("No available udp port found");
    }
}
