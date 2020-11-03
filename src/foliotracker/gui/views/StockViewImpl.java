package foliotracker.gui.views;

import java.util.List;

import java.awt.*;
import javax.swing.*;

import foliotracker.gui.listeners.TransactionListener;
import foliotracker.gui.listeners.SellOutStockListener;

import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.StockTransaction;


public class StockViewImpl extends JFrame implements StockView {
    private static final long serialVersionUID = 1L;
    private static final String WINDOW_NAME = "Folio tracker | %s | %s";
    private String ticker;
    private Portfolio portfolio;

    public StockViewImpl(String ticker, Portfolio portfolio) {
        super(String.format(WINDOW_NAME, portfolio.getName(), ticker));
        this.ticker = ticker;
        this.portfolio = portfolio;
        this.setLayout(new BorderLayout());
        this.add(this.createHeader(), BorderLayout.WEST);
        this.add(this.createHistoryTable(), BorderLayout.CENTER);
        this.add(this.createTransactionControls(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel grid = new JPanel(new GridLayout(0, 2, 0, 0));
        grid.add(new JLabel("Ticker:"));
        grid.add(new JLabel(this.ticker));
        grid.add(new JLabel("Name:"));
        grid.add(new JLabel(this.portfolio.getQuoteCache().getName(this.ticker)));
        grid.add(new JLabel("Price:"));
        grid.add(new JLabel(String.format("%.2f", this.portfolio.getQuoteCache().getValue(this.ticker))));
        grid.add(new JLabel("Shares:"));
        grid.add(new JLabel(String.format("%d", this.portfolio.getShares(this.ticker))));
        grid.add(new JLabel("Total value:"));
        grid.add(new JLabel(String.format("%.2f", this.portfolio.getValue(this.ticker))));
        grid.add(new JLabel("Total cost:"));
        grid.add(new JLabel(String.format("%.2f", this.portfolio.getCost(this.ticker))));
        grid.add(new JLabel("Total benefit:"));
        grid.add(new JLabel(String.format("%.2f", this.portfolio.getBenefit(this.ticker))));
        JPanel content = new JPanel(new FlowLayout());
        content.add(grid);
        return content;
    }

    private JPanel createTransactionControls() {
        JSpinner shares = new JSpinner(new SpinnerNumberModel(1.0, 1.0, Integer.MAX_VALUE, 1.0));
        JRadioButton buy = new JRadioButton("Buy");
        JRadioButton sell = new JRadioButton("Sell");
        ButtonGroup options = new ButtonGroup();
        options.add(buy);
        options.add(sell);
        JButton submit = new JButton("Complete Transaction");
        submit.addActionListener(new TransactionListener(portfolio, this.ticker, this, buy, sell, shares));

        JButton sellAll = new JButton("Sell all");
        sellAll.addActionListener(new SellOutStockListener(portfolio, this.ticker, this));

        JPanel controls = new JPanel(new FlowLayout());
        controls.add(shares);
        controls.add(buy);
        controls.add(sell);
        controls.add(submit);
        controls.add(sellAll);
        return controls;
    }

    private JPanel createHistoryTable() {
        JPanel content = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Stock Transaction History");

        String[] columnNames={
            "Timestamp",
            "Type",
            "Shares",
            "Share price",
            "Total value",
            "Profit"
        };

        List<StockTransaction> transactions = portfolio.getTransactions(this.ticker);
        Object[][] cells = new Object[transactions.size()][columnNames.length];

        Double currentValue = this.portfolio.getQuoteCache().getValue(this.ticker);
        int i = 0;
        for (StockTransaction t : transactions) {
            for (int j = 0; j < columnNames.length+1; j++) {
                switch (j) {
                    case 0: cells[i][j] = t.getTimestamp().toString();
                            break;
                    case 1: cells[i][j] = t.getShares() > 0 ? "Purchase": "Sell" ;
                            break;
                    case 2: cells[i][j] = Math.abs(t.getShares());
                            break;
                    case 3: cells[i][j] = Math.round(t.getPrice() * 100) / 100d;
                            break;
                    case 4: cells[i][j] = Math.round(t.getValue() * 100) / 100d;
                            break;
                    case 5: cells[i][j] = Math.round((currentValue * t.getShares() - t.getValue()) * 100) / 100d;
                            break;
                }
            }
            i++;
        }

        JTable table = new JTable(cells, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);

        content.add(title, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        return content;
    }

    @Override
    public void run() {
        this.setMinimumSize(new Dimension(600, 200));
        this.pack();
        this.setVisible(true);
    }
}
