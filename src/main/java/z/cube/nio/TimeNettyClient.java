package z.cube.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * 类说明
 *
 * @author:zhouzz DATE:2016/11/11 15:18
 */
public class TimeNettyClient {
    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeNettyClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TimeNettyClient().connect(8080, "127.0.0.1");
    }

    private class TimeNettyClientHandler extends ChannelHandlerAdapter {

        private final ByteBuf writeBuf;

        public TimeNettyClientHandler() {
            byte[] bytes = new byte[0];
            try {
                bytes = "Query".getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            writeBuf = Unpooled.copiedBuffer(bytes);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(writeBuf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf readBuf = (ByteBuf) msg;
            byte[] dst = new byte[readBuf.readableBytes()];
            readBuf.readBytes(dst);
            String body = new String(dst, "UTF-8");
            System.out.println("body:" + body);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

}
