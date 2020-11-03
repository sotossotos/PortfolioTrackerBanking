package foliotracker.portfolios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import foliotracker.quotes.RandomQuoteCache;
import foliotracker.quotes.QuoteCache;

public class PortfolioManagerImplTestCase {
    private PortfolioManager manager;
    private String ticker;

    @Before
    public void setUp() {
        manager = new PortfolioManagerImpl(new RandomQuoteCache());
        ticker = "FAKE";
    }

    @Test
    public void test_get_nonexistent_is_null() {
        assertNull("We should get no portfolio with an nonexistent name.",
                manager.get("nonexistent"));
    }

    @Test
    public void test_get_or_create_nonexistent_creates() {
        manager.getOrCreate("new");
        assertNotNull("A new portfolio should be created.",
                manager.get("new"));
    }

    @Test
    public void test_get_or_create_nonexistent_returns() {
        assertNotNull("A new portfolio should be returned.",
                manager.getOrCreate("new"));
    }

    @Test
    public void test_get_or_create_existent_does_not_override() {
        Portfolio p = manager.getOrCreate("existent");
        try {
            p.buy(ticker, 10);
        } catch (Exception e) {
            fail(e.toString());
        }
        Portfolio q = manager.getOrCreate("existent");
        assertEquals("Existent portfolios should not be overriden.",
                10, q.getShares(ticker));
    }

    @Test
    public void test_get_all_starts_empty() {
        assertEquals("There should be no portfolio yet.",
                new HashSet<>(), manager.getAll());
    }

    @Test
    public void test_get_all_fills() {
        Portfolio p = manager.getOrCreate("p");
        Portfolio q = manager.getOrCreate("q");
        assertEquals("Already created portfolios should be returned.",
                new HashSet<>(Arrays.asList(p, q)), manager.getAll());
    }

    @Test
    public void test_remove_nonexistent() {
        assertFalse("No portfolio with this name exists.",
                manager.remove("nonexistent"));
    }

    @Test
    public void test_remove_existent() {
        manager.getOrCreate("existent");
        assertTrue("A portfolio with such a name existed.",
                manager.remove("existent"));
    }

    @Test
    public void test_remove_existent_cannot_get() {
        manager.getOrCreate("p");
        manager.remove("p");
        assertNull("The portfolio should have been removed.",
                manager.get("p"));
    }

    @Test
    public void test_remove_existent_not_in_get_all() {
        manager.getOrCreate("p");
        manager.remove("p");
        assertEquals("The only portfolio should have been removed.",
                new HashSet<>(), manager.getAll());
    }

    @Test
    public void test_get_quote_cache_returns_construction_time_cache() {
        QuoteCache q = new RandomQuoteCache();
        PortfolioManager m = new PortfolioManagerImpl(q);
        assertEquals("We should get out the same quote cache that we put in.",
                     q, m.getQuoteCache());
    }
}
