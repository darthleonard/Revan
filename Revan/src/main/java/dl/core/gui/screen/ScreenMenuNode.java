package dl.core.gui.screen;

import javax.swing.JPanel;

public class ScreenMenuNode extends JPanel implements IScreenAdapter {
	private static final long serialVersionUID = 1L;
	public String Title;
	
	@Override
	public String getTitle() {
		return Title != null ? Title : "Unknown";
	}
}
