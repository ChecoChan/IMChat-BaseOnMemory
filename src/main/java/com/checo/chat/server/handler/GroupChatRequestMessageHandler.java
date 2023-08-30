package com.checo.chat.server.handler;


import com.checo.chat.message.GroupChatRequestMessage;
import com.checo.chat.message.GroupChatResponseMessage;
import com.checo.chat.server.session.GroupSessionFactory;
import com.checo.chat.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());

        Channel fromChannel = ctx.channel();
        for (Channel channel : channels) {
            if (channel != fromChannel)
                channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
    }
}
