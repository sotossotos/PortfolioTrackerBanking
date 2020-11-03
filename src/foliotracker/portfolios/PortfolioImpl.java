package foliotracker.portfolios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import foliotracker.quotes.NoSuchTickerException;
import foliotracker.quotes.QuoteCache;

class PortfolioImpl implements Portfolio  {
    private final static long serialVersionUID = 2L;
    private String name;
    private Map<String, List<StockTransaction>> transactions;

    // Prevent the cache of quotes from being serialized
    private transient QuoteCache quotes;

    public PortfolioImpl(String name, QuoteCache quotes) {
        this.name = name;
        this.transactions = new HashMap<>();
        this.quotes = quotes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<String> getTickers() {
        return this.transactions.keySet();
    }

    @Override
    public QuoteCache getQuoteCache() {
        return this.quotes;
    }

    @Override
    public void setQuoteCache(QuoteCache cache) throws IOException {
        this.quotes = cache;
        // We changed our cache, we have to populate it with our tickers
        for (String ticker : this.getTickers()) {
            try {
                this.quotes.fetch(ticker);
            }
            // At least one of the tickers doesn't make reference to a real quote
            catch (NoSuchTickerException e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    public List<StockTransaction> getTransactions(String ticker) {
        return this.transactions.getOrDefault(ticker, new ArrayList<>());
    }

    @Override
    public int getShares(String ticker) {
        return this.transactions.getOrDefault(ticker, new ArrayList<>())
                                .stream()
                                .mapToInt(t -> t.getShares())
                                .sum();
    }

    @Override
    public void buy(String ticker, int shares) throws IOException, NoSuchTickerException {
        // Avoid registering an empty transaction
        if (shares == 0) { return; }
        // Fetch the latest quote for this ticker
        this.quotes.fetch(ticker);
        // We just fetched the quote for the ticker, and didn't get any errors
        // The value for the ticker should be in the cache
        Double value = this.quotes.getValue(ticker);
        assert value != null : String.format("The cache of quotes does not contain a value for %s.", ticker);
        // Add a transaction with the latest price
        this.transactions.computeIfAbsent(ticker, t -> new ArrayList<>())
                         .add(new StockTransactionImpl(shares, value));
    }

    @Override
    public void sell(String ticker, int shares)
        throws IOException, NoSuchTickerException, InsufficientSharesException
    {
        if (shares > this.getShares(ticker)) {
            throw new InsufficientSharesException(String.format("%d %s shares are available, you cannot sell %d.",
                        this.getShares(ticker), ticker, shares));
        }
        this.buy(ticker, -shares);
    }

    @Override
    public void sellAll(String ticker) throws IOException, NoSuchTickerException {
        // Do not call this.sell, we want to bypass InsufficientShares
        this.buy(ticker, -this.getShares(ticker));
    }

    @Override
    public double getValue(String ticker) {
        Double value = this.quotes.getValue(ticker);
        if (value == null) { return 0; }
        return value * this.getShares(ticker);
    }

    @Override
    public double getValue() {
        assert this.getTickers().stream().allMatch(e -> quotes.getValue(e) != null) : "At least one ticker is not in the cache";
        return this.getTickers()
                   .stream()
                   .mapToDouble(t -> this.getValue(t))
                   .sum();
    }

    @Override
    public double getCost(String ticker) {
        return this.transactions.getOrDefault(ticker, new ArrayList<>())
                                .stream()
                                .mapToDouble(t -> t.getValue())
                                .sum();
    }

    @Override
    public double getCost() {
        return this.getTickers()
                   .stream()
                   .mapToDouble(t -> this.getCost(t))
                   .sum();
    }

    @Override
    public double getBenefit(String ticker) {
        return this.getValue(ticker) - this.getCost(ticker);
    }

    @Override
    public double getBenefit() {
        return this.getValue() - this.getCost();
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (!(obj instanceof Portfolio)) { return false; }
        Portfolio other = (Portfolio) obj;
        return this.getName().equals(other.getName());
    }
}
