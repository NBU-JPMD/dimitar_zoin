import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientRequestHandler implements Runnable {
	private SocketChannel socketChannel;
	
	 public ClientRequestHandler(SocketChannel socketChannel) {

         this.socketChannel = socketChannel;
    }

    @Override
    public void run()  {
    	ByteBuffer buf = ByteBuffer.allocate(1024);
   
    	buf.clear();

		int numRead;
		try {
			numRead = socketChannel.read(buf);
		} catch (IOException e) {
			try {
				this.socketChannel.close();
			} catch (IOException e1) {
				return;
			}
			return;
		}
		if (numRead <= 0){
			try {
				socketChannel.close();
			} catch (IOException e) {
				return;
			}
			return;
		}
		
		/* 
		 * Опитвам се да взема броя на байтовете, които съм изпратил
		 * (протоколът ми работи, като съобщението почва с един Int,
		 * броят на байтовете в съобщението),
		 * но нещо не се получава взима рандом стойност с getInt.
		 * Проверих стринга, който изпращам и пращам каквото трябва.
		 * Дори не съм сигурен, че така трябва да се имплементира.
		 * След като го извлека чета толкова байта от буфера, но
		 * сякаш нямам гаранция, че всичко, което съм пратил,
		 * ще се озове в буфера.
		 */
		int packageSize = buf.getInt(0); 
		if (packageSize > numRead){
			System.out.println("Invalid package size");
			return;
		}
		byte[] data = new byte[numRead - 4];
		System.arraycopy(buf.array(), 4, data, 0, packageSize);
		System.out.println("Got: " + new String(data));
		
		
		buf.flip();
		try {
			socketChannel.write(buf);
		} catch (IOException e) {
			System.out.println("Failed writing to channel!");
			e.printStackTrace();
		}
    }
}
