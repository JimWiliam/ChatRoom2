package remotecontrol;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import database.Constants;

public class RmCtrlClient {

	private boolean threadRun = true;

	private Socket clientSocket;

	private OutputStream output;

	private InputStream input;

	private JFrame ctrlFrame;

	private RmPanel screenPanel;

	private InetAddress address;

	private RobotInterface robot;

	public RmCtrlClient(InetAddress address) {
		this.address = address;
		try {
			robot = (RobotInterface) Naming
					.lookup("rmi://" + address.getHostAddress() + ":" + Constants.MyRobotRP + "/robot");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initFrame();
		request();

	}

	private void request() {

		try {
			clientSocket = new Socket(address, Constants.RmCtlServer_port);
			input = clientSocket.getInputStream();
			output = clientSocket.getOutputStream();
			while (threadRun) {
				try {
					BufferedImage image = readImg();
					screenPanel.updateImage(image);
				} catch (Exception e) {
					threadRun=false;
					ctrlFrame.dispose();
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initFrame() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		ctrlFrame = new JFrame();
		ctrlFrame.setVisible(true);
		ctrlFrame.setSize(dm.width / 2, dm.height / 2);
		screenPanel = new RmPanel();
		ctrlFrame.getContentPane().add(screenPanel, "Center");

		CtrlLisener cc = new CtrlLisener();
		ctrlFrame.addWindowListener(new WinLisener());
		// ctrlFrame.addMouseListener(cc);
		//
		// ctrlFrame.addMouseMotionListener(cc);
		ctrlFrame.addKeyListener(cc);
		// ctrlFrame.addMouseWheelListener(cc);

		screenPanel.addMouseListener(cc);
		screenPanel.addMouseMotionListener(cc);
		screenPanel.addMouseWheelListener(cc);
		// screenPanel.addKeyListener(cc);
	}

	private BufferedImage readImg() throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append(input.read() + "");
		}
		int size = Integer.parseInt(sb.toString());
		byte[] bs = new byte[10240];
		int count = 0;
		int len = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			len = input.read(bs, 0, ((size - count) < 10240) ? (size - count) : 10240);
			baos.write(bs, 0, len);
			count += len;
			if (count == size) {
				break;
			}
		}
		return ImageIO.read(new ByteArrayInputStream(ImageZip.unZip(baos.toByteArray())));
	}

	/**
	 * 鼠标键盘事件
	 * 
	 * @author Administrator
	 *
	 */
	private class CtrlLisener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			try {
				robot.keyPress(e.getKeyCode());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			try {
				robot.keyRelease(e.getKeyCode());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// 鼠标
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			try {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					robot.mousePress(InputEvent.BUTTON1_MASK);
					break;
				case MouseEvent.BUTTON2:
					robot.mousePress(InputEvent.BUTTON2_MASK);
				case MouseEvent.BUTTON3:
					robot.mousePress(InputEvent.BUTTON3_MASK);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			try {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					robot.mouseRelease(MouseEvent.BUTTON1_MASK);
					break;
				case MouseEvent.BUTTON2:
					robot.mouseRelease(InputEvent.BUTTON2_MASK);
					break;
				case MouseEvent.BUTTON3:
					robot.mouseRelease(InputEvent.BUTTON3_MASK);
					break;
				}

			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				robot.mouseMove(e.getX(), e.getY());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			try {
				robot.mouseMove(e.getX(), e.getY());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			try {
				// robot.m
				robot.mouseWheel(e.getWheelRotation());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 窗口事件
	 * 
	 * @author Administrator
	 *
	 */
	private class WinLisener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			// System.exit(0);
			ctrlFrame.dispose();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			threadRun = false;
			try {
				input.close();
				//output.write(0);
				output.close();
				clientSocket.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ctrlFrame.dispose();
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

	}
	
	public static void main(String[] args) throws Exception {
		InetAddress address;
		if (args.length != 0) {
			address = InetAddress.getByName(args[0]);
		} else {
			//用于测试
			address = InetAddress.getByName("10.0.0.109");
		}
		new RmCtrlClient(address);
	}
	
	

}
