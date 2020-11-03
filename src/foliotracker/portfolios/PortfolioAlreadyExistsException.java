package foliotracker.portfolios;

public class PortfolioAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;
    public PortfolioAlreadyExistsException(String s) {
        super(s);
    }
}
