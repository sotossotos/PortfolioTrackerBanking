package foliotracker.quotes;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public abstract class AbstractQuoteCache extends Observable implements QuoteCache {
    protected Map<String, String> names;
    protected Map<String, Double> values;

    public AbstractQuoteCache() {
        this.names = new HashMap<>();
        this.values = new HashMap<>();
    }

    public String getName(String ticker) {
        return this.names.getOrDefault(ticker, null);
    }

    public Double getValue(String ticker) {
        return this.values.getOrDefault(ticker, null);
    }

    protected abstract void doFetch(String ticker) throws IOException, NoSuchTickerException;

    public synchronized void fetch(String ticker) throws IOException, NoSuchTickerException {
        this.doFetch(ticker);
        this.setChanged();
        this.notifyObservers();
    }

    public synchronized void updateAll() throws IOException {
        for (String t : this.names.keySet()) {
            try {
                this.doFetch(t);
            } catch (NoSuchTickerException n) {
                // The ticker has been fetched before, this should not happen
                throw new RuntimeException(n);
            }
        }
        this.setChanged();
        this.notifyObservers();
    }
}
