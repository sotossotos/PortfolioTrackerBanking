package foliotracker.gui.views;

import foliotracker.gui.utils.CloseableTabbedPane;
import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.PortfolioManager;

public interface MainView extends Runnable {
    public PortfolioManager getPortfolioManager();
    public PortfolioView getCurrentPortfolioTab();
    public PortfolioView addPortfolioTab(Portfolio p);
}
