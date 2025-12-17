package C;

public class GreenAction {

    private String description;

    public GreenAction(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
