package com.checo.chat.test;

import com.checo.chat.message.LoginRequestMessage;
import com.checo.chat.protocol.MessageCodec;
import com.checo.chat.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0,0),
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodecSharable()
//                new MessageCodec()
        );

        // 编码
        LoginRequestMessage message = new LoginRequestMessage("Jack", "123");
        channel.writeOutbound(message);

        // 解码
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        channel.writeInbound(buf);
    }
}
