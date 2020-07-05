package dl.core.gui.menu;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import dl.core.gui.screen.ScreenMenuFactory;

public class MenuModule extends JPanel {

    private static final long serialVersionUID = 1L;
    private List<MenuNodeListener> listeners = new ArrayList<MenuNodeListener>();

    public MenuModule(String title, String xmlPath) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        var btn = new JButton(title);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getMinimumSize().height));
        var pnl = new JPanel();
        pnl.setLayout(new GridLayout(1, 1));
        
        //var menuLoader = new ScreenMenuFactory("/home/user/workspace/java/Revan/Revan/MenuDefinitions.xml");
        var menuLoader = new ScreenMenuFactory(xmlPath);
        var tr = menuLoader.Create();
        tr.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                var tp = tr.getPathForLocation(me.getX(), me.getY());
                if (tp == null) {
                    return;
                }
                var node = (MenuNode) tp.getLastPathComponent();
                if (node.isLeaf()) {
                    fireMenuNodeEvent((Class<?>) node.getUserObject());
                }
            }
        });

        pnl.add(tr);
        pnl.setVisible(false);
        add(btn);
        add(pnl);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                pnl.setVisible(!pnl.isVisible());
            }
        });
    }
    
    public synchronized void addMenuNodeListener(MenuNodeListener l) {
        listeners.add(l);
    }

    public synchronized void removeMoodListener(MenuNodeListener l) {
        listeners.remove(l);
    }

    private synchronized void fireMenuNodeEvent(Class<?> screen) {
        var menuNodeEvent = new MenuNodeEvent(this, screen);
        var registeredListeners = listeners.iterator();
        while (registeredListeners.hasNext()) {
            ((MenuNodeListener) registeredListeners.next()).menuNodeClick(menuNodeEvent);
        }
    }
}
