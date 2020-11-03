package foliotracker.gui.listeners;

import java.awt.event.*;
import javax.swing.*;

import foliotracker.gui.views.PortfolioView;

public class StockRowClicked extends MouseAdapter {
    private JTable table;
    private PortfolioView portfolioView;

    public StockRowClicked(JTable table, PortfolioView portfolioView) {
        this.table = table;
        this.portfolioView = portfolioView;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            try {
                String ticker = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
                this.portfolioView.displayStockView(ticker);
            } catch (IndexOutOfBoundsException ioobe) {
                // The row is no longer there
                return;
            }
        }
    }
}
