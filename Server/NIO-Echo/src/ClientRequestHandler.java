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
