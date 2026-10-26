package secret;

import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Request {
	private PrintWriter output;
	public Request(PrintWriter output) {
		this.output = output;
	}
	public void respond(int code, String type, String data) {
		String statusMessage /* this is very simplified but is fine for this */ = code == 200 ? "OK" : "ERR";
		output.println("HTTP/1.1 " + code + " " + statusMessage);
		output.println("Content-Length: " + data.length());
		output.println("Content-Type: " + type);
		output.println();
		output.println(data);
		output.println();
		output.println();
	}
	public void respondFile(int code, String filePath) {
		Path path = Paths.get(filePath);
		try {
			String mime = Files.probeContentType(path);
			String content = Files.readString(path);
			respond(code, mime, content);			
		} catch (IOException e) {
			respond(404, "text/plain", "404 not found");
		}
	}
	public String method;
	public String path;
	public String body;
	public Map<String, String> headers = new HashMap<>();
}
