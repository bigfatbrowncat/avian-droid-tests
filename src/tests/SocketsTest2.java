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
 * Echo-server/client talks to itself
 */
public class SocketsTest2 {

	public static void issue() throws UnknownHostException, IOException {

		Thread serv = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("[SERVER] Starting");
				try (ServerSocket ss = new ServerSocket(1234)) {
					System.out.println("[SERVER] Listening...");
					Socket sc = ss.accept();

					BufferedReader br = new BufferedReader(
							new InputStreamReader(sc.getInputStream()));
					String read = null;
					System.out.println("[SERVER] Reading on server:");
					while ((read = br.readLine()) != null) {
						System.out.println(read);
					}
					System.out.println("[SERVER] Everything is read");
					sc.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread clnt = new Thread(new Runnable() {

			@Override
			public void run() {
				try (Socket cs = new Socket("127.0.0.1", 1234)) {

					System.out.println("[CLIENT] Writing on client:");

					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(cs.getOutputStream()));
					bw.write("This is a message from client.\r\n");
					bw.flush();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bw.write("The small tail after one second...");
					bw.flush();
					System.out.println("[CLIENT] Everything is written");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		serv.start();
		clnt.start();

		try {
			serv.join();
			clnt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test3() throws UnknownHostException, IOException {
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