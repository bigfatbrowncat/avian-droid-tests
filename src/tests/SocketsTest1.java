package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * HTTP-client
 */
public class SocketsTest1 {

	public static void issue() throws IOException {
		System.out.println("Requesting...");
		try (Socket sock = new Socket("intel.com", 80)) {
			System.out.println("Connected");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			String request = "GET /?gws_rd=cr HTTP/1.1\r\n" + "Host: ya.ru\r\n"
					+ "Accept: */*\r\n" + "User-Agent: Java\r\n"
					+ "Connection: close\r\n" + "\r\n";
			System.out.println("Writing request...");
			bw.write(request);
			System.out.println("Flushing");
			bw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String read = null;
			System.out.println("Reading response...");
			while ((read = br.readLine()) != null) {
				System.out.println(read);
			}
			System.out.print("Closing. Socket bound to " + sock.getLocalSocketAddress().toString());
			System.out.println(" was connected to " + sock.getRemoteSocketAddress().toString());
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		issue();
	}

}