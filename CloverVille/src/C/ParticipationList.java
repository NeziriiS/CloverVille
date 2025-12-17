package C;
import java.util.ArrayList;

public class ParticipationList
{
  private ArrayList<CommunalTask> participations = new ArrayList<>();

  public void addParticipation(CommunalTask participation)
  {
    participations.add(participation);
  }

  public ArrayList<CommunalTask> getList()
  {
    return participations;
  }
}
