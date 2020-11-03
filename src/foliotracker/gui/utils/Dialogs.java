package foliotracker.gui.utils;

import javax.swing.JOptionPane;

public class Dialogs {
    public static void displayQuoteFetchIOException() {
        JOptionPane.showMessageDialog(
                null,
                "An IO error occurred when fetching information about the given ticker.",
                "Quote fetch IO error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void displayNoSuchTicker() {
        JOptionPane.showMessageDialog(
                null,
                "The ticker you entered does not exist",
                "Ticker does not exist",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void displayInsufficientShares() {
        JOptionPane.showMessageDialog(
                null,
                "You don't have as many shares as you are trying to sell.",
                "Transaction error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void displayPortfolioAlreadyExists() {
        JOptionPane.showMessageDialog(
                null,
                "A portfolio with the given name is already loaded.",
                "Portfolio already loaded",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void displayInvalidPortfolioName() {
        JOptionPane.showMessageDialog(
                null,
                "The name of the portfolio cannot be empty",
                "Invalid portfolio name",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void displayPortfolioLoadIOException() {
        JOptionPane.showMessageDialog(
                null,
                "An IO error occurred when trying to load the portfolio, " +
                "does the selected file contain a portfolio?",
                "Portfolio load IO error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void displayPortfolioSaveIOException() {
        JOptionPane.showMessageDialog(
                null,
                "An IO error occurred when trying to save the portfolio.",
                "Portfolio save IO error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void displayNoPortfolioToSave() {
        JOptionPane.showMessageDialog(
                null,
                "If you want to save a portfolio, first create one.",
                "No portfolio to save",
                JOptionPane.ERROR_MESSAGE);
    }

    public static int displayFileAlreadyExists() {
        return JOptionPane.showConfirmDialog(null,
               "A file with that name already exists, would you like to overwrite?",
    	       "File already exists",
    	       JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static int displayQuitConfirmation() {
        return JOptionPane.showConfirmDialog(
                null,
                "You are about to lose all portfolios you didn't save",
                "Confirm close",
                JOptionPane.YES_NO_OPTION);
    }
}
