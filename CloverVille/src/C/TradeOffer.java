package C;

public class TradeOffer {

    private final String text;

    public TradeOffer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}