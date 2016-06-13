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
				/*
				 * Тук си модифицирам изходящото съобщение, така че да почва
				 * с броя на байтовете. Взимам ги + 1 за да хвана и новия ред.
				 */
				String modMessage = (message.getBytes().length + 1) + message + "\n";
				outputStream.writeBytes(modMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
