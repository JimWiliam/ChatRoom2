package remotecontrol;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RobotInterface extends Remote {

	public void mouseMove(int x, int y) throws RemoteException;

	public void mousePress(int buttons) throws RemoteException;

	public void mouseRelease(int buttons) throws RemoteException;

	public void mouseWheel(int wheelAmt) throws RemoteException;

	public void keyPress(int keycode) throws RemoteException;

	public void keyRelease(int keycode) throws RemoteException;

	public Color getPixelColor(int x, int y) throws RemoteException;

	public BufferedImage createScreenCapture(Rectangle screenRect) throws RemoteException;

	public boolean isAutoWaitForIdle() throws RemoteException;

	public void setAutoWaitForIdle(boolean isOn) throws RemoteException;

	public int getAutoDelay() throws RemoteException;

	public void setAutoDelay(int ms) throws RemoteException;

	public void delay(int ms) throws RemoteException;

	public void waitForIdle() throws RemoteException;

}
