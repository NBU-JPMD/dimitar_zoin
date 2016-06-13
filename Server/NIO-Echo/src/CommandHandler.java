
import java.util.Scanner;

public class CommandHandler implements Runnable {

	
	public CommandHandler() {}
	
	@Override
	public void run() {
		/*
		byte[] data = new byte[numRead];
		System.arraycopy(buf.array(), 0, data, 0, numRead);
		System.out.println("Got: " + new String(data));
		
		
		buf.flip();
		try {
			socketChannel.write(buf);
		} catch (IOException e) {
			System.out.println("Failed writing to channel!");
			e.printStackTrace();
		}

		
		
		*/
		try (Scanner scan = new Scanner(System.in);) {

			while (true) {
				String input = scan.nextLine();
				String[] args = input.split(" ");
				
				if (args[0].substring(0,1).equals("-")) {
					String command = args[0].substring(1);
					if(command.equals("echo")) {
						for (int i = 1; i < args.length; i++) {
						System.out.println(args[i]);
						}	
					} else if(command.equals("exit")) {
						
					}
					else {
						System.out.println("Command not valid!");
					}
				} else {
					System.out.println("First argument is not a valid command syntax!\n"
							+ "Please start commands with '-'\n");
				}
			}
		}
	}
}
