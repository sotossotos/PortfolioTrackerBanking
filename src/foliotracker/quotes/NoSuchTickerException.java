package foliotracker.quotes;

public class NoSuchTickerException extends Exception {
    private static final long serialVersionUID = 1L;
    public NoSuchTickerException(String s) {
        super(s);
    }
}
