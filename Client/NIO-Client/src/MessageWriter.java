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
				/* Modifying the outgoing message to start with the number of bytes in it.
				 * I am sending message length + 1 bytes to account for the new line.s
				 * (��� �� ����������� ���������� ���������, ���� �� �� �����
				 * � ���� �� ���������. ������ �� + 1 �� �� ����� � ����� ���.)
				 */
				String modMessage = (message.getBytes().length + 1) + message + "\n";
				outputStream.writeBytes(modMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
