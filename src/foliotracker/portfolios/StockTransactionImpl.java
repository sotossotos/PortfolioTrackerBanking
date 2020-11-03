package foliotracker.portfolios;

import java.util.Date;

class StockTransactionImpl implements java.io.Serializable, StockTransaction {
    private int shares;
    private double price;
    private Date timestamp;

    /**
     * modifies: this
     * effects: constructs a new transaction with the given number of shares and
     *          price and records the time of construction.
     */
    public StockTransactionImpl(int shares, double price) {
        this.shares = shares;
        this.price = price;
        this.timestamp = new Date();
    }

    public int getShares() {
        return this.shares;
    }

    public double getPrice() {
        return this.price;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public double getValue() {
    	return this.shares * this.price;
    }
}
