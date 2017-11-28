package z.cube.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class TimeClient {

	public static void main(String[] args) {
		int port = 8080;

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			String address = "127.0.0.1";
			socket = new Socket(address, port);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			out.println("Query");
			String resp = in.readLine();
			System.out.println("resp:" + resp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(socket);
		}
	}
}
