package z.cube.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("Server: start");
			while(true){
				Socket socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(server);
		}
	}
}
