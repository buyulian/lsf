package com.me.lsf.common.http.server.netty.simple;

import com.me.lsf.common.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class SimpleDecoder  extends ByteToMessageDecoder {

    private int size = -1;

    private int remainderSize = 0;

    private byte[] contentBytes ;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int n = in.readableBytes();
        if (size < 0) {
            if (n < 4) {
                return;
            } else {
                ByteBuf byteBuf = in.readBytes(4);
                size = byteBuf.getInt(byteBuf.readerIndex());
                remainderSize = size;
                contentBytes = new byte[size];
            }
        } else {
            if (n >= remainderSize) {
                ByteBuf byteBuf = in.readBytes(remainderSize);
                int targetStartIndex = size - remainderSize;
                ByteBufUtils.convertByteBufToString(byteBuf,
                        remainderSize,
                        contentBytes,
                        targetStartIndex);
                String content = new String(contentBytes);
                SimpleMessage message = new SimpleMessage();
                message.setContent(content);
                contentBytes = null;
                remainderSize = 0;
                size = -1;
                out.add(message);
            } else {
                ByteBuf byteBuf = in.readBytes(n);
                int targetStartIndex = size - remainderSize;
                ByteBufUtils.convertByteBufToString(byteBuf,
                        remainderSize,
                        contentBytes,
                        targetStartIndex);
                remainderSize -= n;
            }
        }


    }
}
