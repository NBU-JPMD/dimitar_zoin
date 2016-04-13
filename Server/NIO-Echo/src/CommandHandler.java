import java.util.Scanner;

public class CommandHandler implements Runnable {
	/*
	private SocketChannel socketChannel;
	private ByteBuffer buf = ByteBuffer.allocate(4096);
	SelectionKey key;
	
	public CommandHandler(SelectionKey key) {
		this.key = key;
		SocketChannel socketChannel = (SocketChannel) key.channel();
	}
	*/
	
	@Override
	public void run() {
		
		/*
		this.buf.clear();
		int numRead;
		
		try  {
			numRead = socketChannel.read(this.buf);
		} catch (IOException e) {
			key.cancel();
			socketChannel.close();
			return;
		}
		if (numRead == 0){
			key.cancel();
			socketChannel.close();
			return;
		}
		if (numRead == -1) {
			key.channel().close();
			key.cancel();
			return;
		}
	
		buf.flip();
		socketChannel.write(buf);

		byte[] data = new byte[numRead];
		System.arraycopy(buf.array(), 0, data, 0, numRead);
		System.out.println("Got: " + new String(data));
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
