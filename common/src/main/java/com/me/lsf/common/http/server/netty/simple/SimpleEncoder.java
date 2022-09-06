package com.me.lsf.common.http.server.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class SimpleEncoder extends MessageToByteEncoder<SimpleMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SimpleMessage message, ByteBuf byteBuf) throws Exception {
        byte[] bytes = message.getContent().getBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
