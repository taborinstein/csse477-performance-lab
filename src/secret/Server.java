package secret;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket server;
	public void listen(int port) throws IOException {
		System.out.println("Starting server on " + port);
		server = new ServerSocket(port);
    }
	public Request acceptConnection() throws IOException {
		Socket client = server.accept();
		  BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
          PrintWriter out = new PrintWriter(client.getOutputStream(), true);
          Request req = new Request(out);
          String requestLine = in.readLine();
          String[] requestLineParts = requestLine.split(" ");
          req.method = requestLineParts[0];
          req.path = requestLineParts[1];
         while (true) {
        	 String line = in.readLine();
        	 if (line.isEmpty()) break;
        	 String[] parts = line.split(": ", 2);
        	 req.headers.put(parts[0], parts[1]);
         }
         if (req.method.equals("POST")) {
        	 int content_len = Integer.parseInt(req.headers.get("Content-Length"));
        	 char[] buffer = new char[content_len];
        	 int num_read = 0;
        	 while (num_read < content_len) {
        		 int n = in.read(buffer);
        		 if (n < 0) break;
        		 num_read += n;
        	 }
        	 req.body = new String(buffer);
         }
          return req;
	}



}
