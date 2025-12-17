package C;
import java.util.ArrayList;

public class communalTaskList
{
  private ArrayList<CommunalTask> tasks = new ArrayList<>();

  public void addTask(CommunalTask task)
  {
    tasks.add(task);
  }

  public void removeTask(CommunalTask task)
  {
    tasks.remove(task);
  }

  public ArrayList<CommunalTask> getList()
  {
    return tasks;
  }
}