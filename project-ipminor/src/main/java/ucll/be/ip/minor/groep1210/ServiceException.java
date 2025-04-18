package ucll.be.ip.minor.groep1210;


public class ServiceException extends RuntimeException {

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ServiceException(String action, String message) {
        super(message);
        this.action = action;
    }
}