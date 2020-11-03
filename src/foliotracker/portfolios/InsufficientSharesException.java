package foliotracker.portfolios;

public class InsufficientSharesException extends Exception {
    private static final long serialVersionUID = 1L;
    public InsufficientSharesException(String s) {
        super(s);
    }
}
