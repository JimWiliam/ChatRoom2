package remotecontrol;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.imageio.ImageIO;

import database.Constants;

public class RmCtrlServer {
	
	private static ServerSocket server;
	
	private OutputStream output;
	
	private boolean treadRun=true;
	
	@SuppressWarnings("unused")
	private InputStream input;
	
	
	static{
		try {
			server=new ServerSocket(Constants.RmCtlServer_port);
		} catch (IOException e) {
		System.out.println("1");
			e.printStackTrace();
		}
		
	}
		
	public RmCtrlServer(){
		try {
			RmRobot robot=new RmRobot();
			LocateRegistry.createRegistry(Constants.MyRobotRP);
			
			InetAddress address=InetAddress.getLocalHost();
			Naming.bind("rmi://"+address.getHostAddress()
					+ ":"
					+Constants.MyRobotRP+"/robot",robot);
			System.out.println(address.getHostAddress());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		openServer();
	}
	private void openServer(){
		
		try {
			Socket s = server.accept();
			input=s.getInputStream();
			output = s.getOutputStream();
			Robot robot = new Robot();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRect = new Rectangle(d);
			while (treadRun) {
				try {
					BufferedImage screen = robot.createScreenCapture(screenRect);
					ByteArrayOutputStream bb = new ByteArrayOutputStream();
					ImageIO.write(screen, "png", bb);
					byte[] screenbuf = bb.toByteArray();
					sendImage(screenbuf);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			System.out.println("3");
			e.printStackTrace();
		} catch (IOException e1) {
			System.out.println("4");
			e1.printStackTrace();
		} catch (AWTException e2) {
			System.out.println("5");
			e2.printStackTrace();
		}
		
	}

	private  void sendImage(byte[] imagebuff) {
		byte[] bs = new byte[8];
		byte[] data = ImageZip.zip(imagebuff);
		String l = data.length + "";
		for (int i = 8 - l.length(); i < 8; i++) {
			bs[i] = Byte.parseByte(l.charAt(i - (8 - l.length())) + "");
		}
		try {
			output.write(bs);
			output.flush();
			output.write(data);
			output.flush();
		} catch (IOException e) {
			//treadRun=false;
			System.exit(0);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		RmCtrlServer server1=new RmCtrlServer();
	}

}
