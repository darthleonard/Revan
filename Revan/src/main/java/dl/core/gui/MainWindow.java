package dl.core.gui;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import dl.core.gui.menu.MenuModule;
import dl.core.gui.menu.MenuNodeEvent;
import dl.core.gui.menu.MenuNodeListener;
import dl.core.gui.screen.IScreenAdapter;
import dl.core.gui.screen.ScreenTabComponent;

public class MainWindow extends JFrame implements MenuNodeListener {

    private static final long serialVersionUID = 1L;
    private JPanel actionPanel;
    private JTabbedPane tabbed;

    public MainWindow() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(TestDataCreator.createMenuBar());

        actionPanel = new JPanel();
        tabbed = new JTabbedPane();
        actionPanel.add(tabbed);
        var menuPanel = createMenuPanel();
        var splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                menuPanel,
                tabbed);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        add(splitPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var modules = new ArrayList<String>();
        modules.add("module 1");
        modules.add("module 2");
        modules.add("module 3");
        modules.add("module 4");

        for (var module : modules) {
            var panelModule = new MenuModule(module);
            panelModule.addMenuNodeListener(this);
            panel.add(panelModule);
        }
        return panel;
    }

    @Override
    public void menuNodeClick(MenuNodeEvent event) {
        var msj = "empty";
        try {
            var screen = (IScreenAdapter) event.ScreenClass().getDeclaredConstructor().newInstance();
            tabbed.addTab(screen.getTitle(), (JPanel) screen);
            var index = tabbed.indexOfComponent((JPanel) screen);
            tabbed.setTabComponentAt(index, new ScreenTabComponent(tabbed));
            tabbed.setSelectedComponent((JPanel) screen);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            msj = e.getMessage();
            actionPanel.add(new JLabel(msj));
            e.printStackTrace();
        }
        actionPanel.updateUI();
    }
}
