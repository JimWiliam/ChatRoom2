package frame;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import serverAndClient.MultiCastGroup;
import serverAndClient.Server;
import tree.ClientNodeData;

public class ClientListFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6001222579618300024L;

	private JPanel contentPane;

	private JTextField SearchTextField, SignatureTxt;

	private JButton btnImage, btnSearch, btnSettings;

	private JLabel nameLabel;

	private JScrollPane FriendsPane, GroupPane, RecentPane;

	private JTree tree;

	private DefaultMutableTreeNode root;

	private MultiCastGroup Castgroup;
	
	private List<InetAddress> onlinelist;
	

	/**
	 * Create the frame.
	 */
	public ClientListFrame() {
		onlinelist=new ArrayList<InetAddress>();
		
		initFrame();
		initTree();
		
		new Server(tree,onlinelist).start();
		joinGoup();
		addListeners();
	}

	/**
	 * 加入组监听消息
	 */
	private void joinGoup() {
		Castgroup = new MultiCastGroup(tree,onlinelist);
		Castgroup.sayHello();
		Castgroup.RefreshList();
	}

	private void initTree() {
		root = new DefaultMutableTreeNode("在线的");
		tree = new JTree(root);
		FriendsPane.setViewportView(tree);
	}

	/**
	 * 初始化布局
	 */
	private void initFrame() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ignored) {

		}

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnImage = new JButton("New button");

		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel panel_2 = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnImage, GroupLayout.PREFERRED_SIZE, 82,
												GroupLayout.PREFERRED_SIZE)
										.addGap(1).addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
										.addGap(1))
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)).addGap(1)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)));

		btnSettings = new JButton("settings");

		JButton btnNewButton_4 = new JButton("New button");

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(103, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2
						.createSequentialGroup().addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSettings).addComponent(btnNewButton_4))
				.addContainerGap(29, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		nameLabel = new JLabel("New label");

		SignatureTxt = new JTextField();
		SignatureTxt.setText("hello");
		SignatureTxt.setColumns(10);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(SignatureTxt, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE).addGap(1))
				.addComponent(nameLabel, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE));
		gl_panel_1
				.setVerticalGroup(
						gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 51,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(SignatureTxt, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		FriendsPane = new JScrollPane();
		tabbedPane.addTab("Friends", null, FriendsPane, null);

		GroupPane = new JScrollPane();
		tabbedPane.addTab("Group", null, GroupPane, null);

		RecentPane = new JScrollPane();
		tabbedPane.addTab("Recent", null, RecentPane, null);

		SearchTextField = new JTextField();
		SearchTextField.setColumns(10);

		btnSearch = new JButton("Search");

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addComponent(SearchTextField, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSearch)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup().addGap(1).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(SearchTextField).addComponent(btnSearch))));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	/**
	 * 初始化事件监听器
	 */
	private void addListeners() {
		tree.addMouseListener(ml);

		/**
		 * 窗口退出的时候离开组播组
		 */
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {

				Castgroup.sayByby();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}
	
	/**
	 * 树叶的事件监听器
	 */
	MouseListener ml = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {

			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			try {
				ClientNodeData node = (ClientNodeData) selPath.getLastPathComponent();
				if (!node.isRoot())
					if (selRow != -1) {
						if (e.getClickCount() == 1) {
							// System.out.println(selRow);
						} else if (e.getClickCount() == 2) {
							// 被动打开窗口
							new ChatFrame(node).setVisible(true);
						}
					}
			} catch (Exception ee) {
			}
		}

	};



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientListFrame frame = new ClientListFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
