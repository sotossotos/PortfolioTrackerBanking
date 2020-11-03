package foliotracker.gui.listeners;

import java.io.IOException;

import java.awt.event.*;
import javax.swing.*;

import static foliotracker.gui.utils.Dialogs.*;

import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.InsufficientSharesException;
import foliotracker.quotes.NoSuchTickerException;


public class TransactionListener implements ActionListener{
    private Portfolio portfolio;
    private String ticker;
    private JFrame parent;
    private JRadioButton buy;
    private JRadioButton sell;
    private JSpinner shares;

    public TransactionListener(Portfolio portfolio, String ticker, JFrame parent, JRadioButton buy, JRadioButton sell, JSpinner shares) {
        this.parent = parent;
        this.portfolio = portfolio;
        this.ticker = ticker;
        this.shares = shares;
        this.buy = buy;
        this.sell = sell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.buy.isSelected() && !this.sell.isSelected()) {
            JOptionPane.showMessageDialog(
                null,
                "You have to select either buy or sell",
                "Transaction Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
             null,
             "Are you sure you want to proceed with the Transaction?",
             "Confirm",
             JOptionPane.YES_NO_OPTION
        );

        if (confirmation == 1) { return; }

        int shares = ((Double) this.shares.getValue()).intValue();
        try {
            if (this.buy.isSelected()) {
                this.portfolio.buy(this.ticker, shares);
            }
            else {
                this.portfolio.sell(this.ticker, shares);
            }
            this.parent.dispose();
        }
        catch (NoSuchTickerException nste) {
            throw new RuntimeException(nste);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            displayQuoteFetchIOException();
        }
        catch (InsufficientSharesException exp) {
            displayInsufficientShares();
        }
    }

}
