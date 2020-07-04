package dl.core.gui.screen;

import javax.swing.JPanel;

import dl.reflection.ClassId;

@ClassId(Id = "cbf83296-6351-435a-b733-8d76071bd713")
public class ScreenMenuNode extends JPanel implements IScreenAdapter {

    private static final long serialVersionUID = 1L;
    public String Title;
    
    @Override
    public String getTitle() {
        return Title != null ? Title : getClass().getAnnotation(ClassId.class).Id();
    }
}
