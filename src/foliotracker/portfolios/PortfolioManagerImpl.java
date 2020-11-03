package foliotracker.portfolios;

import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Observer;

import foliotracker.quotes.QuoteCache;
import foliotracker.quotes.NoSuchTickerException;

public class PortfolioManagerImpl implements PortfolioManager {
    private QuoteCache quotes;
    private Set<Portfolio> portfolios;

    public PortfolioManagerImpl(QuoteCache quotes) {
        this.portfolios = new HashSet<>();
        this.quotes = quotes;
    }

    public Portfolio get(String name) {
        return this.portfolios.stream().filter(p -> p.getName().equals(name))
                                       .findFirst().orElse(null);
    }

    public Portfolio getOrCreate(String name) {
        this.portfolios.add(new PortfolioImpl(name, this.quotes));
        return this.get(name);
    }


    public Set<Portfolio> getAll() {
        return new HashSet<>(this.portfolios);
    }

    public boolean remove(String name) {
        Portfolio p = this.get(name);
        return this.portfolios.remove(p);
    }

    public QuoteCache getQuoteCache() {
        return this.quotes;
    }

    public void save(Portfolio portfolio, Path path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            out.writeObject(portfolio);
        }
    }

    public Portfolio load(Path path) throws IOException, PortfolioAlreadyExistsException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            Portfolio p = (Portfolio) in.readObject();

            // Check if a portfolio with such a name isn't already loaded
            if (this.portfolios.contains(p)) {
                throw new PortfolioAlreadyExistsException(
                        String.format("A portfolio with name %s already exists", p.getName()));
            }

            // Give the loaded portfolio access to our global cache
            // This automatically fetches the portfolio tickers into the cache
            p.setQuoteCache(this.quotes);
            this.portfolios.add(p);
            return p;

        } catch (ClassNotFoundException c) {
            throw new IOException("The provided file does not contain a portfolio.");
        }
    }
}
