package tests;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class SimpleFrameworkHttp implements Container {

	private static int counter = 0;

	public void handle(Request request, Response response) {
		try {
			PrintStream body = response.getPrintStream();
			long time = System.currentTimeMillis();

			response.setValue("Content-Type", "text/html; charset=utf-8");
			response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
			response.setDate("Date", time);
			response.setDate("Last-Modified", time);

			String content = "<html><body><h2>Hello, world!</h2>This is an http-server based on SimpleFramework. It's answered "
					+ counter + " requests already.</body></html>\r\n";
			counter++;

			body.println(content);
			body.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] list) throws Exception {
		Container container = new SimpleFrameworkHttp();
		Server server = new ContainerServer(container);

		Connection connection = new SocketConnection(server);
		SocketAddress address = new InetSocketAddress(8080);

		System.out.println("before connect");
		connection.connect(address);
		System.out.println("bye!");
	}
}