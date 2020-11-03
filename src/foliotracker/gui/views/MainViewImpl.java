package foliotracker.gui.views;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;
import javax.swing.*;

import foliotracker.gui.utils.CloseableTabbedPane;
import foliotracker.gui.listeners.LoadFolioListener;
import foliotracker.gui.listeners.NewPortfolioListener;
import foliotracker.gui.listeners.PortfolioTabsListener;
import foliotracker.gui.listeners.SaveFolioListener;
import foliotracker.gui.listeners.QuitListener;

import foliotracker.portfolios.Portfolio;
import foliotracker.portfolios.PortfolioManager;

public class MainViewImpl implements MainView {
    private final static String WINDOW_NAME = "Foliotracker";
    private PortfolioManager portfolios;
    private CloseableTabbedPane tabs;

    public MainViewImpl(PortfolioManager portfolios) {
        this.portfolios = portfolios;
    }

    @Override
    public PortfolioManager getPortfolioManager() {
        return this.portfolios;
    }

    private CloseableTabbedPane getTabs() {
        if (this.tabs == null) {
            this.tabs = new CloseableTabbedPane();
            this.tabs.addContainerListener(new PortfolioTabsListener(this));
        }
        return this.tabs;
    }

    @Override
    public PortfolioView getCurrentPortfolioTab() {
        return (PortfolioView) this.getTabs().getSelectedComponent();
    }

    @Override
    public PortfolioView addPortfolioTab(Portfolio p) {
        PortfolioViewImpl pv = new PortfolioViewImpl(p);
        this.getTabs().addTab(p.getName(), pv);
        return pv;
    }

    private JMenuBar createMenuBar() {
        JMenuItem create = new JMenuItem("New");
        create.addActionListener(new NewPortfolioListener(this));

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new LoadFolioListener(this));

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new SaveFolioListener(this));

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new QuitListener());

        JMenu portfolio = new JMenu("File");
        portfolio.add(create);
        portfolio.add(load);
        portfolio.add(save);
        portfolio.add(quit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(portfolio);
        return menuBar;
    }

    public void run() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                try {
                    portfolios.getQuoteCache().updateAll();
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        }, 5000, 5000);

        for (Portfolio p : this.portfolios.getAll()) {
            this.getTabs().addTab(p.getName(), new PortfolioViewImpl(p));
        }

        JFrame frame = new JFrame(WINDOW_NAME);
        frame.setJMenuBar(this.createMenuBar());
        frame.getContentPane().add(this.getTabs());

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setMinimumSize(new Dimension(400, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
