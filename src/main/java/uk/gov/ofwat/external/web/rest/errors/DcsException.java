package uk.gov.ofwat.external.web.rest.errors;

public class DcsException  extends Exception {

    private static final long serialVersionUID = 1L;

    public DcsException(String message) {
        super(message);
    }

    public DcsException(Throwable cause) {
        super(cause);
    }

    public DcsException(String message, Throwable cause) {
        super(message, cause);
    }
}
