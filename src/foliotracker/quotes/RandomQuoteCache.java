package foliotracker.quotes;

import java.io.IOException;
import java.util.Random;


public class RandomQuoteCache extends AbstractQuoteCache {
    private Random r;

    public RandomQuoteCache() {
        this.r = new Random();
    }

    @Override
    protected void doFetch(String ticker) throws IOException, NoSuchTickerException {
        this.names.put(ticker, ticker);
        this.values.put(ticker, this.r.nextDouble() * 100);
    }
}
