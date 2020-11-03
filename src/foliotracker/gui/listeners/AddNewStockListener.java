package foliotracker.gui.listeners;

import java.io.IOException;

import java.awt.event.*;
import javax.swing.*;

import foliotracker.gui.views.PortfolioView;
import static foliotracker.gui.utils.Dialogs.*;

import foliotracker.portfolios.Portfolio;
import foliotracker.quotes.NoSuchTickerException;

public class AddNewStockListener implements ActionListener {
    private PortfolioView portfolioView;

    public AddNewStockListener(PortfolioView portfolioView) {
        this.portfolioView = portfolioView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ticker = JOptionPane.showInputDialog(
                null,
                "Ticker:",
                "Add stock",
                JOptionPane.INFORMATION_MESSAGE);
        if (ticker == null) {
            return;  // Action canceled
        }

        ticker = ticker.trim();
        try {
            this.portfolioView.getPortfolio().getQuoteCache().fetch(ticker);
            this.portfolioView.displayStockView(ticker);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            displayQuoteFetchIOException();
        } catch (NoSuchTickerException nste) {
            displayNoSuchTicker();
        }

    }

}
