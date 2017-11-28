package z.cube.nio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;


public class TimeNio2Client {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        System.out.println("client: start");
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port)).start();
    }

    public static class AsyncTimeClientHandler implements Runnable, CompletionHandler<Void, AsyncTimeClientHandler> {
        private int port;
        private String host;
        private AsynchronousSocketChannel client;
        private CountDownLatch cdl;

        public AsyncTimeClientHandler(String host, int port) throws IOException {
            this.port = port;
            this.host = host;
            this.client = AsynchronousSocketChannel.open();
        }

        @Override
        public void run() {
            cdl = new CountDownLatch(1);
            client.connect(new InetSocketAddress(this.host, this.port),
                    this, this);
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void completed(Void result, AsyncTimeClientHandler attachment) {
            try {
                byte[] req = "Query".getBytes("UTF-8");
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                this.client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        if (buffer.hasRemaining()) {
                            client.write(buffer, buffer, this);
                        } else {
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                            client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer buffer) {
                                    buffer.flip();
                                    byte[] resp = new byte[buffer.remaining()];
                                    buffer.get(resp);
                                    try {
                                        String body = new String(resp, "UTF-8");
                                        System.out.println("body:" + body);
                                        cdl.countDown();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer buffer) {
                                    IOUtils.closeQuietly(client);
                                    cdl.countDown();
                                }
                            });
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer buffer) {
                        IOUtils.closeQuietly(client);
                        cdl.countDown();
                    }
                });

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
            exc.printStackTrace();
            IOUtils.closeQuietly(client);
            cdl.countDown();
        }
    }
}
