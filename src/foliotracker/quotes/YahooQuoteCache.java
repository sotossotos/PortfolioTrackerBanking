package foliotracker.quotes;

import java.net.URL;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class YahooQuoteCache extends AbstractQuoteCache {
    private final static String baseUrl = "http://finance.yahoo.com/d/quotes.csv?s=%s&f=nl1&e=.csv";
    // http://stackoverflow.com/a/13259681
    // This allows us to parse names of quotes with a comma in them, like
    // "Amazon.com, Inc."
    private final static Pattern pattern = Pattern.compile(
            "(?:\\s*(?:\\\"([^\\\"]*)\\\"|([^,]+))\\s*,?)+?");

    @Override
    protected void doFetch(String ticker) throws IOException, NoSuchTickerException {
        if (ticker == null || ticker.trim().equals("")) {
            throw new NoSuchTickerException(ticker);
        }

        // Fetch the URL, read the contents and get the first line as a string
        URL url = new URL(String.format(baseUrl, ticker));
        BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = buffer.readLine().toString();
        buffer.close();

        // Get the matching groups out to a list
        Matcher ma = this.pattern.matcher(line);
        List<String> data = new ArrayList<>();
        while (ma.find()) {
            data.add(ma.group(1) != null ? ma.group(1) : ma.group(2));
        }

        // Sanity check: we should only get the name and the current value
        if (data.size() != 2) {
            throw new NoSuchTickerException(ticker);
        }

        try {
            this.names.put(ticker, data.get(0));
            this.values.put(ticker, Double.parseDouble(data.get(1)));
        }
        catch (NumberFormatException e) {
            throw new NoSuchTickerException(ticker);
        }
    }
}
