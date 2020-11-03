package foliotracker.gui.listeners;

import javax.swing.*;
import java.awt.event.*;

import foliotracker.gui.views.MainView;
import static foliotracker.gui.utils.Dialogs.*;

import foliotracker.portfolios.Portfolio;

public class NewPortfolioListener implements ActionListener {
    private MainView mainView;

    public NewPortfolioListener(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog(
            null,
            "Name:",
            "New portfolio",
            JOptionPane.PLAIN_MESSAGE
        );
        if (name == null) {
            return;
        }
        if (name.trim().equals("")) {
            displayInvalidPortfolioName();
            return;
        }
        Portfolio p = this.mainView.getPortfolioManager().get(name);
        if (p == null) {
            p = this.mainView.getPortfolioManager().getOrCreate(name);
            mainView.addPortfolioTab(p);
        } else {
            displayPortfolioAlreadyExists();
        }
    }
}
