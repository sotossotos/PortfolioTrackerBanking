package foliotracker;

import javax.swing.SwingUtilities;

import foliotracker.gui.views.MainViewImpl;

import foliotracker.quotes.YahooQuoteCache;
import foliotracker.portfolios.PortfolioManager;
import foliotracker.portfolios.PortfolioManagerImpl;

public class Main {
    public static void main(String[] argv) {
        PortfolioManager portfolios = new PortfolioManagerImpl(new YahooQuoteCache());
        SwingUtilities.invokeLater(new MainViewImpl(portfolios));
    }
}
