package lab4;

import java.net.ServerSocket;
import java.net.Socket;

import lab4.webServer.runner;

public class Unittest {
	public static void main(String argv[]) throws Exception {
		ServerSocket sSock = new ServerSocket(1000);

		for(;;) {
			Socket cSock = sSock.accept();
		
			new Thread(new runner(cSock)).start();	
		}
	}
}
