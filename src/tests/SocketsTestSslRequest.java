package tests;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

public class SocketsTestSslRequest {

	public static void main(String[] args) throws UnknownHostException,
	IOException {
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		System.out.println("created SSL factory");

		try (SSLSocket sock = (SSLSocket) factory.createSocket("google.com", 443)) {
			System.out.println("Connected");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			String request = "GET / HTTP/1.1\r\n" + "Host: google.com\r\n"
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

}
