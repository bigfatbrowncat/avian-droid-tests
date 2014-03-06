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
public class SocketsTestDDoS {

	public static void issue() throws IOException {
		int i = 0;
		while (true) {
			try (Socket sock = new Socket("127.0.0.1", 8080))
			{
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
				String request = "GET /?gws_rd=cr HTTP/1.1\r\n" + "Host: localhost\r\n"
						+ "Accept: * /*\r\n" + "User-Agent: Java\r\n"
						+ "Connection: close\r\n" + "\r\n";
				bw.write(request);
				bw.flush();
	
				BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				String read = null;
				while ((read = br.readLine()) != null) {
					System.out.println(read);
				}
				
				System.out.print("Closing. Socket bound to " + sock.getLocalSocketAddress().toString());
				System.out.println(" was connected to " + sock.getRemoteSocketAddress().toString());
				
				System.out.print("[" + i + "] ");
				i++;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		issue();
	}

}