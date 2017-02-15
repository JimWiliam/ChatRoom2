package remotecontrol;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RmPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1879777182112284259L;
	
	BufferedImage image;
	
	
	public void updateImage(BufferedImage image){
		this.image=image;
		this.updateUI();
	}


	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image,  0, 0,this);
	}
	
	
	
	public static void main(String args[]) throws AWTException{
		Robot robot = new Robot();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// System.out.println(d);
		Rectangle screenRect = new Rectangle(d);
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(d.width/2, d.height/2);
		
		RmPanel jj = new RmPanel();
		frame.getContentPane().add(jj, "Center");
		
		BufferedImage screen; 
		while (true) {
			screen= robot.createScreenCapture(screenRect);
			jj.image = screen;
			jj.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	
	
}

