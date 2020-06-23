package dl.core.gui.screen;

import javax.swing.JPanel;

public class ScreenMenuNode extends JPanel implements IScreenAdapter {

    private static final long serialVersionUID = 1L;
    private boolean isMultiInstance;
    public String Title;

    public void setMultiInstance(boolean isMultiInstance) {
        this.isMultiInstance = isMultiInstance;
    }
    
    public boolean isMultiInstance() {
        return isMultiInstance;
    }
    
    @Override
    public String getTitle() {
        return Title != null ? Title : "Unknown";
    }
}
