package foliotracker.portfolios;

import java.io.IOException;

import foliotracker.quotes.AbstractQuoteCache;
import foliotracker.quotes.NoSuchTickerException;

public class NoSuchTickerQuoteCache extends AbstractQuoteCache{

    @Override
    protected void doFetch(String ticker) throws IOException, NoSuchTickerException {
        throw new NoSuchTickerException(ticker);
    }

}
