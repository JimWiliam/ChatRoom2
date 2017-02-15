package serverAndClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import database.Constants;
import tree.ClientNodeData;
import tree.UserInfo;

public class Server extends Thread {

	private List<InetAddress> onlinelist;

	private JTree tree;

	private ServerSocket serverSocket;

	public Server(JTree tree, List<InetAddress> onlinelist) {
		this.tree = tree;
		this.onlinelist = onlinelist;
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("打开服务器失败");
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				Socket socket2 = serverSocket.accept();
				InetAddress address = socket2.getInetAddress();
				// System.out.println(socket2.getInputStream().read()+"dfghjkl;");
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getPathForRow(0).getPathComponent(0);
				if (onlinelist.contains(address)) {// 地址列表中有此地址
					for (int i = 0, count = root.getChildCount(); i < count; i++) {
						ClientNodeData node = (ClientNodeData) root.getChildAt(i);
						if (node.getInetAddress().equals(address) && node.getSocket2() == null) {
							node.setSocket2(socket2);
							node.StartThread();
							break;
						}
					}
				} else {// 地址列表中无此地址
					onlinelist.add(address);
					UserInfo userInfo = new UserInfo(null, address.getHostName(), null);
					ClientNodeData node = new ClientNodeData(userInfo, null, null);
					root.insert(node, root.getChildCount());
					Socket socket1 = new Socket(address, Constants.SERVER_PORT);
					node.setSocket1(socket1);
					node.setInetAddress(address);
					node.setSocket2(socket2);
					node.StartThread();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							tree.updateUI();
						}
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

}
