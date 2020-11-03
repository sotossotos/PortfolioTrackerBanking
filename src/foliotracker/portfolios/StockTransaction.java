package foliotracker.portfolios;

import java.util.Date;

/**
 * Represents an exchange of shares at a certain price in some moment in time.
 * Once constructed, transactions are immutable.
 */
public interface StockTransaction {

    /**
     * effects: returns the number of shares bought in the transaction;
     *          a positive integer represents a purchase,
     *          a negative integer represents a sell.
     */
    public int getShares();

    /**
     * effects: returns the price of each of the shares.
     */
    public double getPrice();

    /**
     * effects: returns the date the transaction was created.
     */
    public Date getTimestamp();

    /**
     * effects: returns the total value of the transaction.
     */
    public double getValue();
}
