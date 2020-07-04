package dl.core.gui;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import dl.core.gui.menu.MenuModule;
import dl.core.gui.menu.MenuNodeEvent;
import dl.core.gui.menu.MenuNodeListener;
import dl.core.gui.screen.IScreenAdapter;
import dl.core.gui.screen.ScreenTabComponent;
import dl.reflection.ClassId;

import java.util.Arrays;
import javax.swing.JOptionPane;

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
    
    private int openNewTab(IScreenAdapter screen) {
        tabbed.addTab(screen.getTitle(), (JPanel) screen);
        var idx = tabbed.indexOfComponent((JPanel) screen);
        tabbed.setTabComponentAt(idx, new ScreenTabComponent(tabbed));
        return idx;
    }

    @Override
    public void menuNodeClick(MenuNodeEvent event) {
        try {
            var screen = (IScreenAdapter) event.ScreenClass().getDeclaredConstructor().newInstance();
            
            if(!screen.getClass().isAnnotationPresent(ClassId.class)) {
            	var error = screen.getClass() + " is not annotated with ClassId";
            	JOptionPane.showMessageDialog(this, 
            			error,
            			"ERROR ON GUI",
            			JOptionPane.ERROR_MESSAGE);
            	return;
            }
            
            var existingMatchingComponents = Arrays.asList(tabbed.getComponents())
                    .stream()
                    .filter(c -> c instanceof IScreenAdapter && IsSameClassId((IScreenAdapter)c, screen))
                    .findFirst();
            var idx = 0;
            if(existingMatchingComponents.isEmpty()){
                idx = openNewTab(screen);
            } else {
            	idx = screen.getClass().getAnnotation(ClassId.class).IsMutliInstance()
            			? openNewTab(screen)
            			: tabbed.indexOfComponent(existingMatchingComponents.get());
            }
            tabbed.setSelectedIndex(idx);
        } catch (InstantiationException 
                | IllegalAccessException 
                | IllegalArgumentException
                | InvocationTargetException 
                | NoSuchMethodException 
                | SecurityException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        actionPanel.updateUI();
    }
    
    private boolean IsSameClassId(IScreenAdapter screenOpen, IScreenAdapter screenNew) {
    	return screenOpen.getClass().getAnnotation(ClassId.class).Id() == screenNew.getClass().getAnnotation(ClassId.class).Id();
    }
}
