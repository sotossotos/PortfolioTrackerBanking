package foliotracker.gui.listeners;

import java.io.IOException;

import java.awt.event.*;
import javax.swing.*;

import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.InsufficientSharesException;
import foliotracker.quotes.NoSuchTickerException;


public class SellOutStockListener implements ActionListener{
    private Portfolio portfolio;
    private String ticker;
    private JFrame parent;

    public SellOutStockListener(Portfolio portfolio, String ticker, JFrame parent) {
        this.portfolio = portfolio;
        this.ticker = ticker;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int confirmation = JOptionPane.showConfirmDialog(
             null,
             "Are you sure you want to sell out all the stock?",
             "Confirm",
             JOptionPane.YES_NO_OPTION
        );
        if (confirmation == 1) { return; }

        try {
            this.portfolio.sellAll(this.ticker);
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                null,
                "There was an IO error when fetching the current value for the ticker.",
                "IO error when fetching value",
                JOptionPane.ERROR_MESSAGE
            );
        }
        catch (NoSuchTickerException nste) {
        }
        this.parent.dispose();
    }

}
