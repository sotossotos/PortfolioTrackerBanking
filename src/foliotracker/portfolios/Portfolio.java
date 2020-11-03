package foliotracker.portfolios;

import java.io.IOException;
import java.util.Set;
import java.util.List;

import foliotracker.quotes.NoSuchTickerException;
import foliotracker.quotes.QuoteCache;

/**
 * Tracks shares of stock values over time.
 * You can:
 *  - Inspect the contents of the portfolio.
 *  - Buy and sell stock.
 *  - Compute the benefit.
 *  - Get a historical of the transactions made.
 */
public interface Portfolio extends java.io.Serializable {

    /**
     * effects: returns the name of the portfolio.
     */
    public String getName();

    /**
     * effects: returns the tickers of stock that has been bought into the
     *          portfolio, even if it has been sold later.
     */
    public Set<String> getTickers();

    /**
     * effects: returns the reference to the cache of quotes.
     */
    public QuoteCache getQuoteCache();

    /**
     * requires: cache != null
     * modifies: this
     * effects: throws IOException if there is any error populating the cache
     *          with the tickers in the portfolio; or
     *          populates the cache with the tickers in the portfolio and uses
     *          the cache for any future operations on quotes.
     */
    public void setQuoteCache(QuoteCache cache) throws IOException;

    /**
     * requires: ticker != null
     * effects: returns an empty list if the given ticker has had no
     *          transactions; or the list of transactions for the given quote
     *          ordered in time.
     */
    public List<StockTransaction> getTransactions(String ticker);

    /**
     * requires: ticker != null
     * effects: returns the total number of shares for the given ticker.
     */
    public int getShares(String ticker);

    /**
     * requires: ticker != null
     * modifies: this
     * effects: does nothing if shares == 0; or
     *          throws IOException if there was an error getting the current
     *          price of the ticker; or
     *          throws NoSuchTickerException if there exists no stock with the
     *          given ticker; or
     *          records a purchase with the current price of the given ticker
     *          and with the given shares.
     */
    public void buy(String ticker, int shares)
        throws IOException, NoSuchTickerException;

    /**
     * requires: ticker != null
     * modifies: this
     * effects: does nothing if shares == 0; or
     *          throws IOException if there was an error getting the current
     *          price of the ticker; or
     *          throws NoSuchTickerException if there exists no stock with the
     *          given ticker; or
     *          throws InsufficientSharesException if shares >
     *          getShares(ticker); or
     *          records a sell with the current price of the given ticker and
     *          with the given shares.
     */
    public void sell(String ticker, int shares)
        throws IOException, NoSuchTickerException, InsufficientSharesException;

    /**
     * requires: ticker != null
     * modifies: this
     * effects: does nothing if getShares(ticker)  == 0; or
     *          throws IOException if there was an error getting the current
     *          price of the ticker; or
     *          throws NoSuchTickerException if there exists no stock with the
     *          given ticker; or
     *          records a sell transaction with the current price for the
     *          given ticker and with all the shares of stock currently in the
     *          portfolio.
     */
    public void sellAll(String ticker)
        throws IOException, NoSuchTickerException;

    /**
     * requires: ticker != null
     * effects: returns the total value of all the shares of the given ticker.
     */
    public double getValue(String ticker);

    /**
     * effects: returns the total value of the portfolio.
     */
    public double getValue();

    /**
     * requires: ticker != null
     * effects: returns the total price paid for all the shares of the given
     *          ticker.
     */
    public double getCost(String ticker);

    /**
     * effects: returns the total price paid for all the stock in the
     *          portfolio.
     */
    public double getCost();

    /**
     * requires: ticker != null
     * effects: returns the absolute profit made with the given quote; where
     *          a negative value represents loss.
     */
    public double getBenefit(String ticker);

    /**
     * effects: returns the absolute profit made with the portfolio.
     */
    public double getBenefit();
}
