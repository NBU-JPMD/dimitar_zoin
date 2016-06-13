import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReader implements Runnable {
	private Socket clientSocket;

	MessageReader(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		String modifiedSentence;
		try(BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			/*
			 * I don't know how to handle the writing process. When there is nothing to read
			 * it keeps reading nulls. I tried null checking but it didn't work out.
			 * (�� ���� ��� �� �� �������� ��������, ���� �� �� ������ ���� ���� ��� �����.
			 * ����� �� ��� ������� � null ��� �������, �� �� �� ������.)
			 */
			while (true) {
				modifiedSentence = inFromServer.readLine();
				System.out.println("FROM SERVER: " + modifiedSentence);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
