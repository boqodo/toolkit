package z.cube.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * 类说明
 *
 * @author:zhouzz DATE:2016/11/11 14:57
 */
public class TimeNettyServer {

    public static void main(String[] args) {
        new TimeNettyServer().bind(8080);
    }

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {


        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeNettyServerHandler());
        }
    }

    private class TimeNettyServerHandler extends ChannelHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf readBuf = (ByteBuf) msg;
            byte[] req = new byte[readBuf.readableBytes()];
            readBuf.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("body:" + body);

            ByteBuf resp = Unpooled.copiedBuffer(new Date().toString().getBytes("UTF-8"));
            ctx.write(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

}
