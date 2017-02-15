package remotecontrol;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmRobot extends UnicastRemoteObject implements RobotInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4570333850828108707L;

	private Robot robot;

	public RmRobot() throws RemoteException {
		// TODO Auto-generated constructor stub
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public RmRobot(GraphicsDevice screen) throws RemoteException {
		try {
			robot = new Robot(screen);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mouseMove(int x, int y) throws RemoteException {
		robot.mouseMove(x, y);
	}

	public void mousePress(int buttons) throws RemoteException {
		robot.mousePress(buttons);
	}

	public void mouseRelease(int buttons) throws RemoteException {
		robot.mouseRelease(buttons);
	}

	public void mouseWheel(int wheelAmt) throws RemoteException {
		robot.mouseWheel(wheelAmt);
	}

	public void keyPress(int keycode) throws RemoteException {
		robot.keyPress(keycode);
	}

	public void keyRelease(int keycode) throws RemoteException {
		robot.keyRelease(keycode);
	}

	public Color getPixelColor(int x, int y) throws RemoteException {
		return robot.getPixelColor(x, y);
	}

	public BufferedImage createScreenCapture(Rectangle screenRect) throws RemoteException {
		return robot.createScreenCapture(screenRect);
	}

	public boolean isAutoWaitForIdle() throws RemoteException {

		return robot.isAutoWaitForIdle();
	}

	public void setAutoWaitForIdle(boolean isOn) throws RemoteException {
		robot.setAutoWaitForIdle(isOn);
	}

	public int getAutoDelay() throws RemoteException {
		return robot.getAutoDelay();
	}

	public void setAutoDelay(int ms) throws RemoteException {
		robot.setAutoDelay(ms);
	}

	public void delay(int ms) throws RemoteException {
		robot.delay(ms);
	}

	public void waitForIdle() throws RemoteException {

		robot.waitForIdle();
	}

	public String toString() {
		return robot.toString();
	}

}
