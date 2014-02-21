package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Test HTTP-server
 * @author imizus
 */
public class SocketsTest3 {

	public static void issue() throws UnknownHostException, IOException {
		int counter = 0;

		try (ServerSocket sock = new ServerSocket(8080)) {

			while (true) {
				try (Socket conn = sock.accept()) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					int len = 0;

					String read = null;
					while ((read = br.readLine()) != null && !read.equals("")) {
						System.out.println(read);
					}

					
				     /*int rr;
				     StringBuilder sb = new StringBuilder();
				     char p = 0;
				     char pp = 0;
				     char ppp = 0;
				     while ((rr = conn.getInputStream().read()) != -1) {
				      sb.append((char)rr);
				      System.out.print((char)rr);
				      if ((char)rr == '\n' && p == '\r' && pp == '\n' && ppp == '\r') break;
				      ppp = pp;
				      pp = p;
				      p = (char)rr;
				     }*/
					
					
					System.out.println("Request read successfully. " + len
							+ " chars read. Responding...");

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(conn.getOutputStream()));

					String content = "<html><body><h2>Hello, world!</h2>This is small Avian-based super dumb http-server. It's answered "
							+ counter + " requests already.</body></html>\r\n";
					counter++;

					String header = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: text/html; charset=utf-8\r\n"
							+ "Server: Avian-powered demo server\r\n"
							+ "Allow: GET\r\n" + "Content-Length: "
							+ content.length() + "\r\n"
							+ "Connection: close\r\n" + "\r\n";
					bw.write(header);
					bw.write(content);
					System.out.println("Closing the output");
					bw.close();
				}
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		issue();
	}

}