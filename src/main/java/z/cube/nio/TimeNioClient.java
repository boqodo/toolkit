package z.cube.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class TimeNioClient {
	
	private static volatile boolean isStop = false ;
	public static void main(String[] args) {
		Selector selector ;
		SocketChannel channel;
		String host ="127.0.0.1";
		int port =8080;
		try {
			selector = Selector.open();
			channel = SocketChannel.open();
			channel.configureBlocking(false);
			
			if(channel.connect(new InetSocketAddress(host,port))){
				channel.register(selector, SelectionKey.OP_READ);
				doWirte(channel);
			}else{
				channel.register(selector, SelectionKey.OP_CONNECT);
			}
			
			while(!isStop){
				
				try{
					selector.select(1000);
					Set<SelectionKey> keys =selector.selectedKeys();
					Iterator<SelectionKey> it = keys.iterator();
					SelectionKey key = null;
					while (it.hasNext()) {
						key = it.next();
						it.remove();
						try{
							handle(selector,key);
						}catch(Exception e){
							if (key != null) {
								key.cancel();
								IOUtils.closeQuietly(key.channel());
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					System.exit(1);
				}
				
			}
			
			IOUtils.closeQuietly(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void handle(Selector selector,SelectionKey key) throws IOException {
		if(key.isValid()){
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()){
				
				if(sc.finishConnect()){
					sc.register(selector, SelectionKey.OP_READ);
					doWirte(sc);
				}else{
					System.exit(1);
				}
			}
			
			if(key.isReadable()){
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes>0){
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("client:"+body);
					isStop = true;
				}else if(readBytes <0){
					key.cancel();
					sc.close();
				}else{
					//ignore
				}
			}
		}
		
	}

	private static void doWirte(SocketChannel sc) throws IOException {
		byte[] resp = "Query".getBytes("UTF-8");
		ByteBuffer src = ByteBuffer.allocate(resp.length);
		src.put(resp);
		src.flip();
		sc.write(src);
		if(src.hasRemaining()){
			System.out.println("send order 2 server succed");
		}
	}
}
