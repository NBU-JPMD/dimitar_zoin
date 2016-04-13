import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageWriter implements Runnable {
	private DataOutputStream outputStream;
	
	public MessageWriter(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	@Override
	public void run() {
		String message;
		BufferedReader inFromUser;
		
		while (true) {
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
			try {
				message = inFromUser.readLine();
				outputStream.writeBytes(message + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}