package com.me.lsf.common.utils;

import io.netty.buffer.ByteBuf;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class ByteBufUtils {


    public static String convertByteBufToString(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public static void convertByteBufToString(ByteBuf buf, int size, byte[] targetBytes, int targetStartIndex) {
        int readableBytes = buf.readableBytes();
        if(buf.hasArray()) { // 处理堆缓冲区

            byte[] array = buf.array();
            int startIndex = buf.arrayOffset() + buf.readerIndex();
            arrayCopy(array, startIndex, targetBytes, targetStartIndex, readableBytes);
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[readableBytes];
            buf.getBytes(buf.readerIndex(), bytes);
            arrayCopy(bytes, 0 ,targetBytes, targetStartIndex, readableBytes);

        }
    }

    public static void arrayCopy(byte[] sourceBytes, int sourceIndex, byte[] targetBytes, int targetIndex, int copyLength) {
        for (int i = sourceIndex, j = targetIndex; i < copyLength; i++,j++) {
            targetBytes[j] = sourceBytes[i];
        }
    }

    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

}
