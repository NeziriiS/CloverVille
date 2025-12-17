package C;

public class ResidentProfile {

    private String name;
    private int personalPoints;
    private boolean working;
    private Milestone milestones;

    public ResidentProfile(String name) {
        this.name = name;
        this.personalPoints = 0;
        this.working = true;
        this.milestones = new Milestone("Initial milestone");
    }

    public String getName() {
        return name;
    }

    public int getPersonalPoints() {
        return personalPoints;
    }

    public boolean isWorking() {
        return working;
    }

    public void addPoints(int points) {
        this.personalPoints += points;
    }

    public void removePoints(int points) {
        this.personalPoints -= points;
    }

    public Milestone getMilestones() {
        return milestones;
    }
}
