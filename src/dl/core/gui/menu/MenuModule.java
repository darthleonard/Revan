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

import dl.core.gui.screen.ScreenMenuLoader;

public class MenuModule extends JPanel {

    private static final long serialVersionUID = 1L;
    private List<MenuNodeListener> _listeners = new ArrayList<MenuNodeListener>();

    public MenuModule(String text) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        var btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getMinimumSize().height));
        var pnl = new JPanel();
        pnl.setLayout(new GridLayout(1, 1));
        
        var menuLoader = new ScreenMenuLoader("/home/user/workspace/java/Revan/Revan/MenuDefinitions.xml");
        var tr = menuLoader.Load();
        tr.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                var tp = tr.getPathForLocation(me.getX(), me.getY());
                if (tp == null) {
                    return;
                }
                var node = (MenuNode) tp.getLastPathComponent();
                if (node.isLeaf()) {
                    _fireMenuNodeEvent((Class<?>) node.getUserObject());
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
        _listeners.add(l);
    }

    public synchronized void removeMoodListener(MenuNodeListener l) {
        _listeners.remove(l);
    }

    private synchronized void _fireMenuNodeEvent(Class<?> screen) {
        var menuNodeEvent = new MenuNodeEvent(this, screen);
        var listeners = _listeners.iterator();
        while (listeners.hasNext()) {
            ((MenuNodeListener) listeners.next()).menuNodeClick(menuNodeEvent);
        }
    }
}
