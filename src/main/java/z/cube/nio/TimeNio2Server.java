package z.cube.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;

public class TimeNio2Server {
	public static void main(String[] args) {
		int port = 8080;
		System.out.println("Server: start");
		new Thread(new AsyncTimeServerHandler(port)).start();
	}

	public static class AsyncTimeServerHandler implements Runnable {
		
		private int port ;
		private AsynchronousServerSocketChannel asyncChannel;
		private CountDownLatch cdl;
		
		public AsyncTimeServerHandler(int port) {
			this.port = port;
			try {
				this.asyncChannel = AsynchronousServerSocketChannel.open();
				this.asyncChannel.bind(new InetSocketAddress(this.port));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		@Override
		public void run() {
			cdl = new CountDownLatch(1);
			doAccept();
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void doAccept() {
			this.asyncChannel.accept(this, new AcceptCompletionHandler());
		}
	}
	
	public static class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>{

		@Override
		public void completed(AsynchronousSocketChannel result,
				AsyncTimeServerHandler attachment) {
			attachment.asyncChannel.accept(attachment,this);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			result.read(buffer, buffer, new ReadCompletionHandler(result));
		}

		@Override
		public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
			
		}
	}
	
	public static class ReadCompletionHandler implements  CompletionHandler<Integer,ByteBuffer>{

		private AsynchronousSocketChannel channel;
		
		public ReadCompletionHandler(AsynchronousSocketChannel channel){
			if(this.channel == null){
				this.channel = channel;
			}
		}
		
		@Override
		public void completed(Integer result, ByteBuffer attachment) {
			attachment.flip();
			byte[] bytes = new byte[attachment.remaining()];
			attachment.get(bytes);
			
			try {
				String body = new String(bytes,"UTF-8");
				System.out.println("body:"+body);
				doWrite(new Date().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			IOUtils.closeQuietly(this.channel);
		}
		
		public void doWrite(String resp) throws IOException {
			byte[] bytes = resp.getBytes("UTF-8");
			ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
			buffer.put(bytes);
			buffer.flip();
			
			this.channel.write(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer buffer) {
					if(buffer.hasRemaining()){
						channel.write(buffer,buffer,this);
					}
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					IOUtils.closeQuietly(channel);
				}
			});
		}
	}
}
