package com.work16.work16server.proto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NettyServer {

    private ChannelInitializer channelInitializer;

    // 构建此类的时候必须传入ChannelInitializer
    public NettyServer(ChannelInitializer channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    // 禁用无参构造
    private NettyServer() {
    }

    public void startWithPort(int port) {
        // UDP不必处理连接，只需要读写数据
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group).option(ChannelOption.SO_BROADCAST, false)
                    .channel(NioDatagramChannel.class).handler(channelInitializer)
                    .bind(port).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
            e.printStackTrace();
        }
    }
}
