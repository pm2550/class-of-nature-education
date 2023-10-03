package com.program.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

//    private final nettyServer nettyserver;
//    @Autowired
//    public ServerApplication(nettyServer nettyserver) {
//        this.nettyserver = nettyserver;
//    }

    public static void main(String[] args) {
     SpringApplication.run(ServerApplication.class, args);
        System.out.println("启动成功");
    }
//    @PostConstruct
//    public void startNettyServer() {
//        nettyserver.start();
//    }
}
