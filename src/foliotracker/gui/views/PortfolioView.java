package foliotracker.gui.views;

import java.util.Observer;

import foliotracker.portfolios.Portfolio;

public interface PortfolioView extends Observer {
    public Portfolio getPortfolio();
    public void displayStockView(String ticker);
}
