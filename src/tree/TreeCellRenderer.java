package tree;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTree;

/**
 * 树结点渲染器未实现
 * 
 * @author Administrator
 *
 */
public class TreeCellRenderer extends JLabel implements javax.swing.tree.TreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5680604758972197358L;

	public TreeCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics g) {

	}

}
