package foliotracker.gui.listeners;

import java.awt.event.*;
import javax.swing.*;

import foliotracker.gui.views.PortfolioView;
import foliotracker.gui.views.MainView;


public class PortfolioTabsListener implements ContainerListener {
    private MainView mainView;

    public PortfolioTabsListener(MainView mainView) {
        this.mainView = mainView;
    }

    public void componentAdded(ContainerEvent e) {}

    public void componentRemoved(ContainerEvent e) {
        PortfolioView pv = (PortfolioView) e.getChild();
        this.mainView.getPortfolioManager().remove(pv.getPortfolio().getName());
    }
}
