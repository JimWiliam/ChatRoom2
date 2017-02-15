package serverAndClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import database.Constants;
import tree.ClientNodeData;
import tree.UserInfo;

public class MultiCastGroup {

	private List<InetAddress> onlinelist;

	private static InetAddress group;

	private static MulticastSocket s;

	private static DatagramPacket hi;

	private static DatagramPacket byby;

	private static Thread refreashThread;

	private static String hello = "Hello", Byby = "Byby";

	private JTree tree;

	/**
	 * 加入组播
	 */
	static {

		try {
			group = InetAddress.getByName(Constants.MultiCastAddress);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			hi = new DatagramPacket("Hello".getBytes(), "Hello".length(), group, 6789);
			byby = new DatagramPacket("Byby".getBytes(), "Byby".length(), group, 6789);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MultiCastGroup(JTree tree,List<InetAddress> onlinelist) {
		this.tree = tree;
		this.onlinelist=onlinelist;
	}

	/**
	 * 发送hello
	 */
	public void sayHello() {
		try {
			System.out.println("Send hello!!");
			s.send(hi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新列表收到hello加入其ip,并回hello，收到byby删除其ip
	 */
	public void RefreshList() {
		refreashThread = new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					byte[] buf = new byte[10];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);

					try {
						s.receive(recv);
						String msg = new String(buf).trim();
						InetAddress address = recv.getAddress();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getPathForRow(0)
								.getPathComponent(0);
						if (msg.equals(hello)) {//接收到广播Hello
							System.out.println("Receive Hello!!");
							if (!onlinelist.contains(address)) {
								onlinelist.add(address);
								UserInfo userInfo = new UserInfo(null, address.getHostName(), null);
								ClientNodeData node=new ClientNodeData(userInfo,null,null);
								root.insert(node, root.getChildCount());
								node.setInetAddress(address);
								Socket socket1 = new Socket(address, Constants.SERVER_PORT);
								
							//	socket1.setSendBufferSize(1000);
							//	socket1.getOutputStream().write(211213);
							
								node.setSocket1(socket1);
								
								//sayHello();//广播Hello
								
								SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										tree.updateUI();
									}
								});
							} 
						} else if (msg.equals(Byby)) {//接收到广播Byby
							System.out.println("Receive Byby!!");
							if (onlinelist.contains(address)) {
								onlinelist.remove(address);
								for(int i=0,count=root.getChildCount();i<count;i++){
									ClientNodeData node=(ClientNodeData) root.getChildAt(i);
									if(node.getInetAddress().equals(address)){
										node.closeSockets();
										root.remove(i);
										break;
									}
								}
								SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										tree.updateUI();
									}
								});
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			
		};
		refreashThread.start();
	}

	/**
	 * 发送Byby,并且离开组播组
	 */
	public void sayByby() {
		try {
			System.out.println("Send Byby!!");
			s.send(byby);
			s.leaveGroup(group);
		} catch (IOException e) {
			
			System.out.println("here！");
			e.printStackTrace();
		}
	}
}
