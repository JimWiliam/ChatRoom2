package tree;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class ClientNodeData extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8119882387705470236L;

	protected Socket socket1, socket2;

	protected InetAddress address;

	protected ArrayList<String> Messages;

	protected ReceiveThread receiveThread;

	protected boolean threadRun = true;

	public ClientNodeData(UserInfo userInfo, Socket socket1, Socket socket2) {
		super(userInfo);
		this.socket1 = socket1;
		this.socket2 = socket2;
		Messages = new ArrayList<String>();
	}

	public Socket getSocket1() {

		return socket1;
	}

	public Socket getSocket2() {
		return socket2;
	}

	public void setInetAddress(InetAddress address) {
		this.address = address;
	}

	public InetAddress getInetAddress() {

		return address;
	}

	public void setSocket2(Socket socket2) {

		this.socket2 = socket2;
	}

	public void setSocket1(Socket socket1) {

		this.socket1 = socket1;
	}

	public void closeSockets() {

		try {
			socket1.close();
			socket2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void WaitThread() {
//		try {
//			System.out.println("3.1");
//			receiveThread.wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			System.out.println("3.2");
//			e.printStackTrace();
//		}
//		System.out.println("Stopping receiveThread");
//	}

	public void StartThread() {
		receiveThread = new ReceiveThread();
		receiveThread.start();
	}

	// public void ResumeThread() {
	// receiveThread.notify();
	// System.out.println("receiveThread notified!!");
	// }

	public void addMessage(String message) {
		Messages.add(message);
	}

	public ArrayList<String> getMessages() {

		return Messages;
	}

	private class ReceiveThread extends Thread {

		@Override
		public void run() {
			super.run();
			System.out.println("receiveThread running!!");
			try {
				byte[] buff;
				String ms;
				int length = 0;
				InputStream in = socket2.getInputStream();
				while (true) {
					length = in.available();
					if (length != 0) {
						buff = new byte[length];
						socket2.getInputStream().read(buff);
						ms = new String(buff);
						addMessage(ms);
						System.out.println("Receive " + ms + " from " + socket2.getInetAddress() + "!!");
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("receiveThread stop");
		}

	};

	@Override
	public String toString() {
		return ((UserInfo) getUserObject()).getUserName();
	}

}
