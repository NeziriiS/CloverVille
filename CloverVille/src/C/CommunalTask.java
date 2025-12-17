package C;

public class CommunalTask {

    private final String name;

    public CommunalTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}