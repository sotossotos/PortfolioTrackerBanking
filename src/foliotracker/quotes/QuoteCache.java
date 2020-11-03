package foliotracker.quotes;

import java.io.IOException;
import java.util.Set;
import java.util.Observer;

/**
 * Fetches, stores and updates values and names of stock.
 */
public interface QuoteCache {

    /**
     * requires: ticker != null
     * effects: returns null if the given ticker has not been fetched before; or
     *          returns the name for the given ticker.
     */
    public String getName(String ticker);

    /**
     * requires: ticker != null
     * effects: returns null if the given ticker has not been fetched before; or
     *          returns the value for the given ticker.
     */
    public Double getValue(String ticker);

    /**
     * requires: ticker != null
     * modifies: this
     * effects: throws IOException if there was any IO error when fetching; or
     *          throws NoSuchTickerException if the given ticker is invalid; or
     *          stores the name and value for the given ticker and signals
     *          observers.
     */
    public void fetch(String ticker) throws IOException, NoSuchTickerException;

    /**
     * modifies: this
     * effects: throws IOException if there was any IO error when fetching; or
     *          fetches all the tickers fetched previously and signals
     *          observers.
     */
    public void updateAll() throws IOException;

    /**
     * requires: observer != null
     * modifies: this
     * effects: marks an observer as to be called whenever a bulk update
     *          happens.
     */
    public void addObserver(Observer observer);

    /**
     * requires: observer != null
     * modifies: this
     * effects: forgets about calling the given observer when a bulk update
     *          happens.
     */
    public void deleteObserver(Observer observer);
}
