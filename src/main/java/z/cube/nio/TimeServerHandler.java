package z.cube.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class TimeServerHandler implements Runnable {
	private Socket socket;
	public TimeServerHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			
			while(true){
				String body = in.readLine();
				if(StringUtils.isNotBlank(body)){
					System.out.println("body:"+body);
				}else{
					break;
				}
				out.println("copy:"+new Date());
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(socket);
		}
	}

}
