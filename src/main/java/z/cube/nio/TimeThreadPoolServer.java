package z.cube.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

public class TimeThreadPoolServer {
	public static void main(String[] args) {
		int port = 8080;
		
		ServerSocket server = null;
		
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maximumPoolSize = 20;
		long keepAliveTime = 120l;
		int capacity = 10;
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(capacity);
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
				TimeUnit.SECONDS, workQueue);
		try {
			server = new ServerSocket(port);
			System.out.println("Server: start");
			while(true){
				Socket socket = server.accept();
				executor.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(server);
		}
	}
}
