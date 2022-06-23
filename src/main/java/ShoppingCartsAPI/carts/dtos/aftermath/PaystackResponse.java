package ShoppingCartsAPI.carts.dtos.aftermath;

public class PaystackResponse {

    private String sessionId;

    public PaystackResponse() {
    }

    public PaystackResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
