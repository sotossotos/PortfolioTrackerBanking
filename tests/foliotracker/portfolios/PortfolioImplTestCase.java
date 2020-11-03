package foliotracker.portfolios;

import java.io.IOException;
import java.util.List;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import foliotracker.quotes.NoSuchTickerException;
import foliotracker.quotes.QuoteCache;
import foliotracker.quotes.RandomQuoteCache;

public class PortfolioImplTestCase {
    private static final double DELTA = 1e-10;
    private Portfolio p;
    private RandomQuoteCache cache;
    private String ta;
    private String tb;
    private String tc;
    private String td;

    @Before
    public void setUp() {
        ta = "TA";
        tb = "TB";
        tc = "TC";
        td = "TD";
        cache = new RandomQuoteCache();
        p = new PortfolioImpl("PR", cache);
    }

    @Test(expected=InsufficientSharesException.class)
    public void try_sell_more_than_have() throws IOException, NoSuchTickerException, InsufficientSharesException {
        p.buy(ta, 10);
        p.sell(ta, 11);
        assertTrue("Failed to buy 10 shares instead bought:"+p.getShares(ta),p.getShares(ta)==10);
    }

    @Test(expected=NoSuchTickerException.class)
    public void test_nonexisting_stock() throws NoSuchTickerException, IOException {
        Portfolio portfolioNoTickers = new PortfolioImpl(tb,new NoSuchTickerQuoteCache());
        portfolioNoTickers.buy(tb, 10);
    }

    @Test
    public void test_buy_sell() throws IOException, NoSuchTickerException, InsufficientSharesException {
        p.buy(tb, 10);
        p.sell(tb, 5);
        p.sell(tb, 3);
        assertEquals("The buy and sell transactions are wrong Shares:" + p.getShares(tb),
                     p.getShares(tb), 2);
    }

    @Test
    public void test_sellAll() throws IOException, NoSuchTickerException {
        p.buy(tc, 10);
        p.sellAll(tc);
        assertEquals("The sell all transaction fails, Shares:" + p.getShares(tc),
                     p.getShares(tc), 0);
    }

    @Test
    public void sellAll_without_having_shares() throws IOException, NoSuchTickerException  {
        p.sellAll(td);
        List<StockTransaction>transactionListForSD =p.getTransactions(td);
        assertTrue("There shouldn't be any recorder transactions Number of Transactions:" + transactionListForSD.size(),
                   transactionListForSD.isEmpty());
    }

    @Test
    public void equals_reflexive() {
        Portfolio p2 = p;
        assertEquals("First Folio:" + p.toString() + "Second Folio" + p2.toString(),
                     p, p2);
    }

    @Test
    public void equals_symmetric() {
        Portfolio p2 = new PortfolioImpl("PR", new RandomQuoteCache());
        assertTrue("First Folio:" + p.toString() + "Second Folio" + p2.toString(),
                   p.equals(p2) && p2.equals(p));
    }

    @Test
    public void equals_null() {
        assertFalse("No object must be equal to null",
                    p.equals(null));
    }

    @Test
    public void equals_transitive() {
        Portfolio p2 = new PortfolioImpl("PR", new RandomQuoteCache());
        Portfolio p3 = new PortfolioImpl("PR", new RandomQuoteCache());
        assertTrue("Transitive equality fails",
                    p.equals(p2) && p2.equals(p3) && p.equals(p3));
    }

    @Test
    public void equals_constistent() {
        Portfolio p2 = new PortfolioImpl("PR", new RandomQuoteCache());
        assertTrue("Consistency equality fails",
                   p.equals(p2) && p2.equals(p) && p.equals(p2));
    }

    @Test
    public void equals_otherObj() {
        Object p2 = new Object();
        assertFalse(p.equals(p2));
    }

    @Test
    public void basic_profit_calculation_check() throws IOException, NoSuchTickerException, InsufficientSharesException {
        p.buy(ta, 5);
        p.buy(tb, 8);
        p.buy(ta, 3);
        p.buy(ta, 3);
        p.buy(ta, 3);
        p.buy(ta, 3);
        p.buy(ta, 3);
        p.buy(tb, 4);
        p.buy(tb, 4);
        p.sellAll(tb);
        p.sell(ta,10);
        if (p.getValue() - p.getCost() >= 0) {
            assertTrue("Cannot have a negative profit if Value is:" + p.getValue() + " and Cost is:" + p.getCost(),
                       p.getBenefit() >= 0);
        } else {
            assertTrue("Cannot have a positive profit if Value is:" + p.getValue() + " and Cost is:" + p.getCost(),
                       p.getBenefit() < 0);
        }
    }

    @Test
    public void specific_profit_calculation_check() throws IOException, NoSuchTickerException, InsufficientSharesException {
        p.buy(ta, 5);
        p.sell(ta,1);
        p.buy(ta, 3);
        p.sell(ta,1);
        Date firstDate = p.getTransactions(ta).get(0).getTimestamp();
        Double firstBuyPrice = p.getTransactions(ta).get(0).getPrice();
        double expectedBenefit = p.getValue(ta) - p.getCost(ta);
        assertTrue("The expected profit is:" + expectedBenefit +
                   "The actual profit calculated is:" + p.getBenefit(ta) +
                   " First Transaction Date/Time:" + firstDate.toString() +
                   " First Share buying Price: " + firstBuyPrice.toString(),
                   expectedBenefit == p.getBenefit(ta));
    }

    @Test
    public void accumulative_profit_calculation_check() throws IOException, NoSuchTickerException, InsufficientSharesException {
        p.buy(ta, 5);
        p.sell(ta,1);
        p.buy(ta, 3);
        p.sell(ta,1);
        p.buy(tb, 8);
        p.sell(tb,1);
        double expectedBenefit = (p.getValue(ta) - p.getCost(ta)) + (p.getValue(tb) - p.getCost(tb));
        assertEquals("The expected accumulative profit is:" + expectedBenefit +
                     " The actual accumulative profit calculated is:" + p.getBenefit(),
                     expectedBenefit, p.getBenefit(), DELTA);
    }

    @Test
    public void test_get_quote() {
        QuoteCache cacheRes = p.getQuoteCache();
        assertEquals(cache,cacheRes);
    }

    @Test
    public void test_set_quote() throws IOException {
        QuoteCache cacheRes = p.getQuoteCache();
        p.setQuoteCache(cacheRes);
        assertEquals(cache,cacheRes);
    }
}
