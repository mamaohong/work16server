package com.work16.work16server.proto;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 所有ChannelHandler初始化处理类
public class NioDatagramChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    private List<ChannelHandler> channelHandlers;

    public NioDatagramChannelInitializer() {
        channelHandlers = new ArrayList<>();
    }

    @Override
    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
        sortHandler();
        for (ChannelHandler handler : channelHandlers) {
            nioDatagramChannel.pipeline().addLast(handler.getClass().getSimpleName(), handler);
        }
    }

    public void addHandler(ChannelHandler handler) {
        channelHandlers.add(handler);
    }

    // 按index对handler排序
    // 排序规则是：若未指定索引，则优先级最低；索引越小的优先级约高；优先级相同则按照添加顺序排序
    private void sortHandler() {
        channelHandlers.sort(new Comparator<ChannelHandler>() {
            @Override
            public int compare(ChannelHandler o1, ChannelHandler o2) {
                Pipeline pipeline1 = o1.getClass().getAnnotation(Pipeline.class);
                Pipeline pipeline2 = o2.getClass().getAnnotation(Pipeline.class);
                if (pipeline1 == null && pipeline2 == null) return 0;
                if (pipeline1 == null) return -1;
                if (pipeline2 == null) return 1;

                return pipeline2.index() - pipeline1.index();
            }
        });
    }
}
