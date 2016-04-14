import java.io.*;
import java.net.*;

class Client {
	public static void main(String argv[]) throws Exception {

		try (Socket clientSocket = new Socket("localhost", 6578);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		) {
			Thread messageWriter = new Thread(new MessageWriter(outToServer));
			Thread messageReader = new Thread(new MessageReader(clientSocket));
			messageWriter.start();
			messageReader.start();

			messageReader.join();
			messageWriter.join();
		}
	}
}