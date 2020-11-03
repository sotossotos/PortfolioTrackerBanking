package foliotracker.gui.views;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import foliotracker.portfolios.Portfolio;
import foliotracker.quotes.QuoteCache;

class PortfolioStockTableModel extends AbstractTableModel {
    private Portfolio portfolio;
    private static final String[] columnNames = {
        "Symbol",
        "Name",
        "Value",
        "Shares",
        "Total value",
        "Total cost",
        "Total benefit"
    };

    public PortfolioStockTableModel(Portfolio portfolio) {
        super();
        this.portfolio = portfolio;
    }

    private List<String> getTickers() {
        return this.portfolio.getTickers()
                             .stream()
                             .sorted()
                             .filter(t -> this.portfolio.getShares(t) != 0)
                             .collect(Collectors.toList());
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getRowCount() {
        return this.getTickers().size();
    }

    @Override
    public int getColumnCount() {
        return PortfolioStockTableModel.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return PortfolioStockTableModel.columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        try {
            String ticker = this.getTickers().get(row);
            QuoteCache cache = this.portfolio.getQuoteCache();
            switch (column) {
                case 0: return ticker.toString();
                case 1: return cache.getName(ticker);
                case 2: return cache.getValue(ticker);
                case 3: return this.portfolio.getShares(ticker);
                case 4: return Math.round(this.portfolio.getValue(ticker)*100)/ 100d;
                case 5: return Math.round(this.portfolio.getCost(ticker)*100)/ 100d;
                case 6: return Math.round(this.portfolio.getBenefit(ticker)*100)/ 100d;
                default: return null;
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return Double.class;
            case 3: return Integer.class;
            case 4: return Double.class;
            case 5: return Double.class;
            case 6: return Double.class;
            default: return null;
        }
    }
};
