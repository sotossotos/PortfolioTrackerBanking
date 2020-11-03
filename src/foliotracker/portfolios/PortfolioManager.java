package foliotracker.portfolios;

import java.nio.file.Path;
import java.io.IOException;
import java.util.Set;

import foliotracker.quotes.QuoteCache;

/**
 * Manages portfolios of stock.
 * You can add, get, remove, save and load portfolios.
 * Maintains a cache of fetched quotes.
 */
public interface PortfolioManager {
    /**
     * requires: name != null
     * effects: returns the portfolio with the given name; or
     *          null if such a portfolio has not been created.
     */
    public Portfolio get(String name);

    /**
     * requires: name != null
     * modifies: this
     * effects: returns the portfolio with the given name if it exists; or
     *          creates and returns a new portfolio with that name.
     */
    public Portfolio getOrCreate(String name);

    /**
     * effects: returns all the portfolios in the quotes.
     */
    public Set<Portfolio> getAll();

    /**
     * requires: name != null
     * modifies: this
     * effects: removes the portfolio with the given name if it exists; and
     *          returns whether a portfolio with such a name existed.
     */
    public boolean remove(String name);

    /**
     * effects: returns the reference to the cache of quotes.
     */
    public QuoteCache getQuoteCache();

    /**
     * requires: portfolio != null && path != null
     * effects: throws IOException if there is any problem writting the file; or
     *          writes the representation of the portfolio to the given path.
     */
    public void save(Portfolio portfolio, Path path) throws IOException;

    /**
     * requires: path != null
     * effects: throws IOException if there is any problem reading the file; or
     *          throws PortfolioAlreadyExistsException if a portfolio with the
     *          name of the loaded portfolio already exists; or
     *          loads the portfolio into the manager and returns it.
     */
    public Portfolio load(Path path) throws IOException, PortfolioAlreadyExistsException;

}
