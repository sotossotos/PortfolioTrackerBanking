package foliotracker.gui.listeners;

import java.io.IOException;

import java.awt.event.*;
import javax.swing.*;

import static foliotracker.gui.utils.Dialogs.*;

import foliotracker.gui.views.MainView;

import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.PortfolioAlreadyExistsException;

public class LoadFolioListener implements ActionListener {
    private MainView mainView;

    public LoadFolioListener(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser picker = new JFileChooser();
        picker.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (picker.showOpenDialog(null) != JFileChooser.APPROVE_OPTION){
            return;
        }
        try {
            Portfolio p = this.mainView.getPortfolioManager().load(picker.getSelectedFile().toPath());
            this.mainView.addPortfolioTab(p);
        }
        catch (PortfolioAlreadyExistsException pe) {
            displayPortfolioAlreadyExists();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            displayPortfolioLoadIOException();
        }
    }
}
