package foliotracker.gui.views;

import java.util.Observable;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import foliotracker.gui.listeners.StockRowClicked;
import foliotracker.gui.listeners.AddNewStockListener;

import foliotracker.portfolios.Portfolio;

public class PortfolioViewImpl extends JPanel implements PortfolioView {
    private Portfolio portfolio;
    private PortfolioStockTableModel tableModel;
    private JLabel title;

    public PortfolioViewImpl(Portfolio portfolio) {
        portfolio.getQuoteCache().addObserver(this);
        this.portfolio = portfolio;
        this.tableModel = new PortfolioStockTableModel(this.portfolio);
        this.title = new JLabel();
        this.updateTitle(title);

        this.setLayout(new BorderLayout());
        this.add(this.createHeader(this.title), BorderLayout.NORTH);
        this.add(this.createContent(this.tableModel), BorderLayout.CENTER);
    }

    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    public void displayStockView(String ticker) {
        new StockViewImpl(ticker, this.portfolio).run();
    }

    @Override
    public void update(Observable source, Object obj) {
        // Update the content of the title
        this.updateTitle(this.title);
        // Update the contents of the table
        this.tableModel.fireTableDataChanged();
        // This could be further limited to the title and table
        this.repaint();
        this.revalidate();
    }

    private void updateTitle(JLabel title) {
        title.setText(String.format("%s  Value: %.2f  Cost: %.2f  Benefit: %.2f",
                        this.portfolio.getName(),
                        this.portfolio.getValue(),
                        this.portfolio.getCost(),
                        this.portfolio.getBenefit()
                        ));
    }

    private JPanel createHeader(JLabel title) {
        JButton button = new JButton("Add new stock");
        button.addActionListener(new AddNewStockListener(this));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        header.add(title, BorderLayout.LINE_START);
        header.add(Box.createHorizontalGlue());
        header.add(button, BorderLayout.LINE_END);
        return header;
    }

    private JScrollPane createContent(TableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new StockRowClicked(table, this));
        table.setAutoCreateRowSorter(true);
        return new JScrollPane(table);
    }
}
