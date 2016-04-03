import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable {

	private InetAddress hostAddress;
	private int port;

	private ServerSocketChannel servSoCh; 
	private Selector selector;
	private ByteBuffer buf = ByteBuffer.allocate(1024);

	private Selector initSelector() throws IOException {

		Selector socketSelector = Selector.open();
		this.servSoCh = ServerSocketChannel.open();
		servSoCh.configureBlocking(false);


		InetSocketAddress isa = new InetSocketAddress(this.port);
		servSoCh.socket().bind(isa);


		servSoCh.register(socketSelector, SelectionKey.OP_ACCEPT);

		return socketSelector;
	}

	public Server(InetAddress hostAddress, int port) throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;
		this.selector = this.initSelector();

	} 



	public void run() {
		while (true) {
			try {

				this.selector.select();

				Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					if (key.isAcceptable()) {
						this.accept(key);
					} else if (key.isReadable()) {
						this.read(key);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void accept(SelectionKey key) throws IOException {

		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);

		socketChannel.register(this.selector, SelectionKey.OP_READ);
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		this.buf.clear();

		int numRead;
		try {
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

	}

	public static void main(String[] args) throws Exception {
		try {
			new Thread(new Server(null, 6578)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}