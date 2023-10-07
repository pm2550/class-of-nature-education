//package com.program.server.rtsp;
//
//import com.program.server.handler.ForwardHandler;
//import io.netty.bootstrap.ServerBootstrap;
//        import io.netty.channel.ChannelInitializer;
//        import io.netty.channel.ChannelOption;
//        import io.netty.channel.EventLoopGroup;
//        import io.netty.channel.nio.NioEventLoopGroup;
//        import io.netty.channel.socket.SocketChannel;
//        import io.netty.channel.socket.nio.NioServerSocketChannel;
//        import org.springframework.stereotype.Component;
//
//@Component
//public class nettyServer {
//
//    public void start() {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            // 添加处理器来处理 RTSP 流
//                            ch.pipeline().addLast(new ForwardHandler());
//                        }
//                    })
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//
//            serverBootstrap.bind(8080).sync().channel().closeFuture().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//}
