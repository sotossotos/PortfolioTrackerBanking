package foliotracker.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import foliotracker.gui.views.MainView;
import foliotracker.gui.views.PortfolioView;
import static foliotracker.gui.utils.Dialogs.*;

import foliotracker.portfolios.Portfolio;

public class SaveFolioListener implements ActionListener{
    private MainView mainView;

    public SaveFolioListener(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser picker = new JFileChooser(){
            @Override
            public void approveSelection(){
        	    File folio = getSelectedFile();
        	    if(folio.exists()){
        	        int userChoice = displayFileAlreadyExists();
        	        switch(userChoice){
        	            case JOptionPane.YES_OPTION:
        	                super.approveSelection();
        	                return;
        	            case JOptionPane.NO_OPTION:
        	                return;
        	            case JOptionPane.CANCEL_OPTION:
        	            cancelSelection();
        	                return;
        	            case JOptionPane.CLOSED_OPTION:
        	                return;
        	            }
        	        }
        	        super.approveSelection();
        	}
        };
        if (picker.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        PortfolioView pv = this.mainView.getCurrentPortfolioTab();
        if (pv == null) {
            displayNoPortfolioToSave();
            return;
        }
        Portfolio p = pv.getPortfolio();
        try {
            this.mainView.getPortfolioManager().save(p, picker.getSelectedFile().toPath());
        } catch (IOException exp){
            exp.printStackTrace();
            displayPortfolioSaveIOException();
        }
    }

}


