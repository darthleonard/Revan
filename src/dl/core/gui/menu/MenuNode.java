package dl.core.gui.menu;

import javax.swing.tree.DefaultMutableTreeNode;

public class MenuNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1L;
	private String title;
	
	public MenuNode(String title) {
		this.title = title;
	}
	
	public MenuNode(String title, Object userObject) {
		this.title = title;
		this.userObject = userObject;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
