package lab4;
import java.io.*;
import java.net.*;


public final class webServer {


	private static void respondContent(String inString, DataOutputStream out) throws Exception {
		String method = inString.substring(0, inString.indexOf("/")-1);
		String file = inString.substring(inString.indexOf("/")+1, inString.lastIndexOf("/")-5);
		

		if(method.equals("GET")) {
			try {
				byte[] f_bytes = null;
				InputStream inS = new FileInputStream(file);
				f_bytes = new byte[inS.available()];
				inS.read(f_bytes);
				out.writeBytes("HTTP/1.1 " + 200 + " OK\r\n");
				out.writeBytes("Content-Type: " + "html" + "\r\n");
				out.writeBytes("Content-Length: " + f_bytes.length + "\r\n"); 
				out.writeBytes("Server: Jastonex/0.1");
				out.writeBytes("\r\n\r\n");
				out.write(f_bytes);
			
			} catch(FileNotFoundException e) {
				try {
					byte[] f_bytes = null;
					InputStream inS = new FileInputStream("404.html");
					f_bytes = new byte[inS.available()];
					inS.read(f_bytes);
					out.writeBytes("HTTP/1.1 " + 404 + " NOT FOUND\r\n");
					out.writeBytes("Content-Type: " + "html" + "\r\n");
					out.writeBytes("Content-Length: " + f_bytes.length + "\r\n"); 
					out.writeBytes("Server: Jastonex/0.1");
					out.writeBytes("\r\n\r\n");
					out.write(f_bytes);
					
				} catch(FileNotFoundException e2) {
					String responseString = "Server is running. File not found!!!";
					out.writeBytes("HTTP/1.1 " + 404 + " NOT FOUND\r\n");
					out.writeBytes("Content-Type: " + "html" + "\r\n");
					out.writeBytes("Content-Length: " + responseString.length() + "\r\n"); 
					out.writeBytes("Server: Jastonex/0.1");
					out.writeBytes("\r\n\r\n");
					out.write(responseString.getBytes());
				}
			}
			
		} 
		
else if(method.equals("HEAD")) {
			
			out.writeBytes("HTTP/1.1 " + 200 + " OK\r\n");
			out.writeBytes("Content-Type: " + "html" + "\r\n");
			out.writeBytes("Content-Length: " + 0 + "\r\n"); 
			out.writeBytes("Server: Jastonex/0.1");
			out.writeBytes("\r\n\r\n");
			
		}
		else if(method.equals("POST")) {

		}  else {
			out.writeBytes("HTTP/1.1 " + 500 + " INTERNAL SERVER ERROR\r\n");
			out.writeBytes("Content-Type: " + "html" + "\r\n");
			out.writeBytes("Content-Length: " + 0 + "\r\n"); 
			out.writeBytes("Server: Jastonex/0.1");
			out.writeBytes("\r\n\r\n");
			
		}
		
	}

	public static class runner implements Runnable {

		protected Socket sock = null;
		String str;
		BufferedReader input;
		DataOutputStream output;
		

		public runner(Socket connectionSocket) throws Exception {
			this.sock = connectionSocket;
			this.input = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
			this.output = new DataOutputStream(this.sock.getOutputStream());
			this.str = this.input.readLine();
			System.out.println("Server is running!!!");
	
		}

		public void run() {
			try{
				if(this.str != null)
					respondContent(this.str, this.output);

				this.output.flush();
				this.output.close();
				this.input.close();

			} catch (Exception e) { 
				System.out.println("Error flushing and closing");				
			}
		}
	}

	
}
