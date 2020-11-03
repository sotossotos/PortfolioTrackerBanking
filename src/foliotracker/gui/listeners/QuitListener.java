package foliotracker.gui.listeners;

import java.awt.event.*;

import static foliotracker.gui.utils.Dialogs.*;

public class QuitListener implements ActionListener {
    public QuitListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (displayQuitConfirmation() == 0) {
            System.exit(0);
        }
    }
}
