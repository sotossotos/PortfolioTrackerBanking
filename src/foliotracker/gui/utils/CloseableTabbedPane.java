package foliotracker.gui.utils;

import java.awt.*;
import javax.swing.*;

public class CloseableTabbedPane extends JTabbedPane {
    @Override
    public void addTab(String name, Component c) {
        super.addTab(name, c);
        this.setTabComponentAt(this.getTabCount()-1, new ButtonTabComponent(this));
    }
}
