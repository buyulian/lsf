package com.me.lsf.center;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CenterHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger = LoggerFactory.getLogger(CenterHttpHandler.class);

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("class:" + msg.getClass().getName());

        String result = dealMessage(msg);

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(result.getBytes())); // 2

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    /*
     * 收到消息时，返回信息
     */
    public String dealMessage(FullHttpRequest httpRequest) throws Exception {
        String result = "error";
        try{
            String path=httpRequest.uri();          //获取路径
            String body = getBody(httpRequest);     //获取参数
            HttpMethod method=httpRequest.method();//获取请求方法
            //如果不是这个路径，就直接返回错误
            if(!"/".equalsIgnoreCase(path)){
                result="非法请求!";
                return result;
            }
            logger.debug("接收到:"+method+" 请求");
            //如果是GET请求
            if(HttpMethod.GET.equals(method)){
                //接受到的消息，做业务逻辑处理...
                System.out.println("body:"+body);
                result="GET请求";
                return result;
            }
            //如果是POST请求
            if(HttpMethod.POST.equals(method)){
                //接受到的消息，做业务逻辑处理...
                logger.debug("body:"+body);

                result = Center.dealRequest(body);

                logger.debug("result:"+body);

                return result;
            }

            //如果是PUT请求
            if(HttpMethod.PUT.equals(method)){
                //接受到的消息，做业务逻辑处理...
                System.out.println("body:"+body);
                result="PUT请求";
                return result;
            }
            //如果是DELETE请求
            if(HttpMethod.DELETE.equals(method)){
                //接受到的消息，做业务逻辑处理...
                System.out.println("body:"+body);
                result="DELETE请求";
                return result;
            }
        }catch(Exception e){
            System.out.println("处理请求失败!");
            e.printStackTrace();
        }finally{
            //释放请求
//            httpRequest.release();
        }
        return "error";
    }
    /**
     * 获取body参数
     * @param request
     * @return
     */
    private String getBody(FullHttpRequest request){
        ByteBuf buf = request.content();
        return buf.toString(CharsetUtil.UTF_8);
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
