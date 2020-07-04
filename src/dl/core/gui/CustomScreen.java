package dl.core.gui;

import dl.core.gui.screen.ScreenMenuNode;
import dl.reflection.ClassId;

@ClassId(Id = "152fb4dc-fc5e-48ca-819a-edb07603c7bf", IsMutliInstance = true)
public class CustomScreen extends ScreenMenuNode {
    private static final long serialVersionUID = 1L;

    @Override
    public String getTitle() {
        return "custom screen title";
    }
}
