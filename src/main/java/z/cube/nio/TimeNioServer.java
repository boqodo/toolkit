package z.cube.nio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeNioServer {
    public static void main(String[] args) {
        int port = 8080;
        System.out.println("Server: start");
        new Thread(new MultiplexerTimeServer(port)).start();
    }

    public static class MultiplexerTimeServer implements Runnable {
        private Selector selector;
        private ServerSocketChannel channel;
        private int port;

        private volatile boolean isStop;

        public MultiplexerTimeServer(int port) {
            this.port = port;
            try {
                this.selector = Selector.open();
                this.channel = ServerSocketChannel.open();
                this.channel.configureBlocking(false);
                this.channel.socket().bind(new InetSocketAddress(this.port),
                        1024);
                this.channel.register(this.selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    this.selector.select(1000);
                    Set<SelectionKey> keys = this.selector.selectedKeys();
                    Iterator<SelectionKey> it = keys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        try {
                            handle(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                IOUtils.closeQuietly(key.channel());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            IOUtils.closeQuietly(this.selector);
        }

        private void handle(SelectionKey key) throws IOException {
            if (key.isValid()) {
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(this.selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");

                        System.out.println("body:" + body);

                        doWrite(sc, new Date() + "");

                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        // ignore
                    }
                }
            }
        }

        private void doWrite(SocketChannel sc, String resp) throws IOException {
            byte[] resps = resp.getBytes("UTF-8");
            ByteBuffer src = ByteBuffer.allocate(resps.length);
            src.put(resps);
            src.flip();
            sc.write(src);
            System.out.println("write:" + resp);
        }
    }
}
