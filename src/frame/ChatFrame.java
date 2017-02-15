package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import tree.ClientNodeData;

public class ChatFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7331945501820834981L;

	private JPanel contentPane;

	private JLabel nameLabel;

	private JButton btnImage, btnSend, btnClose;

	private JTextPane DisplaytextPane, SendtextPane;

	private JButton btnFonts, btnSize, btnColor;
	
	private JButton  btnRmControl,btnNewButton_2,btnNewButton_3;

	@SuppressWarnings("unused")
	private Socket socket1, socket2;
	
	private InetAddress address;

	private ClientNodeData anode;

	private ChatMessageReceiveThread chatMessageReceiveThread;

	/**
	 * Create the frame.
	 */
	public ChatFrame(ClientNodeData anode) {
		initFrame();

		this.anode = anode;

		chatMessageReceiveThread = new ChatMessageReceiveThread();
		// 显示用户姓名
		nameLabel.setText(anode.toString());
		socket1 = anode.getSocket1();
		socket2 = anode.getSocket2();
		address=socket1.getInetAddress();
		chatMessageReceiveThread.start();

		addListeners();
	}

	/**
	 * 初始化布局
	 */
	private void initFrame() {
		// 设置风格
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ignored) {

		}

		// setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 610, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		btnSend = new JButton("Send");

		btnClose = new JButton("Close");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnClose)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend))
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(1).addComponent(panel_1,
								GroupLayout.PREFERRED_SIZE, 417, Short.MAX_VALUE)))
				.addGap(1).addComponent(panel, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE).addGap(1)));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 105,
												GroupLayout.PREFERRED_SIZE)
										.addGap(1)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 210,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(panel_1, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(1)
										.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 81,
												GroupLayout.PREFERRED_SIZE)
										.addGap(1)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnSend).addComponent(btnClose))).addComponent(panel,
														Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 346,
														Short.MAX_VALUE))));

		SendtextPane = new JTextPane();

		SendtextPane.setBackground(Color.WHITE);
		scrollPane_1.setViewportView(SendtextPane);
		SendtextPane.setText("");

		DisplaytextPane = new JTextPane();
		DisplaytextPane.setEditable(false);
		DisplaytextPane.setBackground(Color.WHITE);
		scrollPane.setViewportView(DisplaytextPane);

		btnNewButton_2 = new JButton("New button2");

		btnNewButton_3 = new JButton("New button3");

		btnRmControl = new JButton("RmoteControl");

		btnImage = new JButton("Image");

		nameLabel = new JLabel("New label");

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup()
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup().addGap(1).addComponent(btnNewButton_2)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton_3)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRmControl))
						.addGroup(gl_panel_2.createSequentialGroup()
								.addComponent(btnImage, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(277, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_2
				.createSequentialGroup()
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnImage, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_2).addComponent(btnNewButton_3).addComponent(btnRmControl))
				.addGap(26)));
		panel_2.setLayout(gl_panel_2);

		btnFonts = new JButton("Fonts");

		btnSize = new JButton("Size ");

		btnColor = new JButton("Color");

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(1).addComponent(btnFonts).addGap(1)
						.addComponent(btnSize).addGap(1).addComponent(btnColor).addGap(192)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(1)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
						.addComponent(btnFonts, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnColor))));
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.height/2,d.width/7);
	}

	/**
	 * 初始化事件监听器
	 */
	private void addListeners() {

		SendActionListener sendActionListener = new SendActionListener();

		btnSend.addActionListener(sendActionListener);

		SendtextPane.addKeyListener(sendActionListener);

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chatMessageReceiveThread.StopThread();
				setVisible(false);
			}
		});

		addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
                
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				chatMessageReceiveThread.StopThread();
				ChatFrame.this.dispose();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		btnRmControl.addActionListener(new RmCtrlListener());
	}

	
	class RmCtrlListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				if(InetAddress.getLocalHost().equals(address)){
					 TipDialog.main(new String[]{});
				}else{
					
					
				}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	class SendActionListener implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
//			if (
//			// e.getModifiers()==InputEvent.CTRL_MASK&&
//			e.getKeyCode() == KeyEvent.VK_ENTER) {
//				sendMessage();
//				SendtextPane.setText("");
//			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
//			if (
//			// e.getModifiers()==InputEvent.CTRL_MASK&&
//			e.getKeyCode() == KeyEvent.VK_ENTER) {
//				SendtextPane.setText("");
//			}
		}
	}

	private class ChatMessageReceiveThread extends Thread {

		private boolean threadRun = true;

		@Override
		public void run() {
			System.out.println("chatMessageReceiveThread running!!");
			while (threadRun) {
				String ms = readMessage();
				if (!ms.equals(""))
					DisplaytextPane.setText(DisplaytextPane.getText() + "ta：" + getTime() + "\n" + "  " + ms + "\n");
				try {
					Thread.sleep(567);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("chatMessageReceiveThread stop!!");
		}

		public void StopThread() {
			threadRun = false;
		}

	};

	private String readMessage() {

		ArrayList<String> Meaasges = anode.getMessages();
		String ms = "";
		for (String message : Meaasges)
			ms += message;
		Meaasges.clear();

		return ms;
	}

	private void sendMessage() {
		String message = SendtextPane.getText();
		SendtextPane.setText("");
		if (!message.equals("")) {
			try {
				socket1.getOutputStream().write(message.getBytes());
				System.out.println("Send " + message +" to " +socket1.getInetAddress()+" success!!");
				String oldms = DisplaytextPane.getText();
				DisplaytextPane.setText(oldms + "我：" + getTime() + "\n" + "  " + message + "\n");
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("Message is empty!!");
		}
	}

	private String getTime() {
		Calendar c = Calendar.getInstance();
		String time = "" + c.get(Calendar.YEAR) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.DATE) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
		return time;
	}

}
