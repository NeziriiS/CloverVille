package C;
import java.util.ArrayList;
import java.util.List;

public class greenActionType
{
  List<String> types = new ArrayList<>();

  public void add(String type)
  {
    types.add(type);
  }

  public void remove(String type)
  {
    types.remove(type);
  }

  public List<String> getList()
  {
    return types;
  }

}
