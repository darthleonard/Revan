package dl.main;

import dl.core.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });
    }
}
