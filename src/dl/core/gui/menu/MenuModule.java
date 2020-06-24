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
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import dl.core.gui.CustomScreen;
import dl.core.gui.screen.ScreenMenuNode;

public class MenuModule extends JPanel {

    private static final long serialVersionUID = 1L;
    private List<MenuNodeListener> _listeners = new ArrayList<MenuNodeListener>();

    public MenuModule(String text) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        var btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, btn.getMinimumSize().height));
        var pnl = new JPanel();
        pnl.setLayout(new GridLayout(1, 1));

        var abuelo = new MenuNode("abuelo");
        var padre = new MenuNode("padre");
        var tio = new MenuNode("tio");
        var hijo = new MenuNode("screen test", CustomScreen.class);
        var hija = new MenuNode("hija", ScreenMenuNode.class);
        var primo = new MenuNode("primo", ScreenMenuNode.class);
        var modelo = new DefaultTreeModel(abuelo);
        
        modelo.insertNodeInto(padre, abuelo, 0);
        modelo.insertNodeInto(tio, abuelo, 1);
        modelo.insertNodeInto(hijo, padre, 0);
        modelo.insertNodeInto(hija, padre, 1);
        modelo.insertNodeInto(primo, tio, 0);

        var tr = new JTree();
        tr.setRootVisible(false);
        tr.setModel(modelo);
        
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
