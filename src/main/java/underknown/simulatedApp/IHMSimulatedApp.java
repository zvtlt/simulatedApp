package underknown.simulatedApp;

import javax.swing.*;

import underknown.simulatedApp.panels.TabManager;

import java.awt.*;

public class IHMSimulatedApp extends JFrame {

    protected IHMSimulatedApp(){
        super("SimulatedApp");
        TabManager control = new TabManager();

        final JPanel gui = new JPanel(new BorderLayout(5,5));
        gui.add(control, BorderLayout.NORTH);
//        gui.add(vue, BorderLayout.CENTER);
        setContentPane(gui);

        setLocation(200, 20);
        setSize(1200, 750);
        setResizable(false);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        pack();
    }

}
