package com.checo.chat.server.handler;

import com.checo.chat.message.ChatRequestMessage;
import com.checo.chat.message.ChatResponseMessage;
import com.checo.chat.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatRequestMessage chatRequestMessage) throws Exception {
        String to = chatRequestMessage.getTo();
        Channel toChannel = SessionFactory.getSession().getChannel(to);
        if (toChannel != null) {
            toChannel.writeAndFlush(new ChatResponseMessage(chatRequestMessage.getFrom(), chatRequestMessage.getContent()));
        } else {
            channelHandlerContext.writeAndFlush(new ChatResponseMessage(false, "用户不存在或对方不在线"));
        }
    }
}
