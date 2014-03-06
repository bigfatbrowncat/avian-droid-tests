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
public class SocketsTestEcho {

	public static void issue() throws UnknownHostException, IOException, InterruptedException {

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
					System.out.println("[SERVER] Stacktrace:");
					e.printStackTrace();
				}
				System.out.println("[SERVER] Stopping");
			}
		});

		Thread clnt = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("[CLIENT] Starting");
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
						System.out.println("[Client] Stacktrace:");
						e.printStackTrace();
					}
					bw.write("The small tail after one second...");
					bw.flush();
					System.out.println("[CLIENT] Everything is written");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("[Client] Stacktrace:");
					e.printStackTrace();
				}
				System.out.println("[Client] Stopping");
			}
		});

		System.out.println(" ... server starting ...");
		serv.start();
		Thread.sleep(500);
		System.out.println(" ... client starting ...");
		clnt.start();

		try {
			serv.join();
			clnt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" ... stopping ...");
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {
		issue();
	}

}