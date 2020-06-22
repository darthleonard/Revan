package dl.core.gui.menu;
import java.util.EventObject;

public class MenuNodeEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private Class<?> _screenClass;
    
    public MenuNodeEvent(Object source, Class<?> screenClass) {
        super(source);
        _screenClass = screenClass;
    }
    
    public Class<?> ScreenClass() {
        return _screenClass;
    }
    
}