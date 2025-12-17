package C;
import java.util.ArrayList;

public class residentList
{
  private ArrayList<ResidentProfile> residentProfiles = new ArrayList<>();

  public void addResident(ResidentProfile residentProfile)
  {
    residentProfiles.add(residentProfile);
  }

  public void removeResident(ResidentProfile residentProfile)
  {
    residentProfiles.remove(residentProfile);
  }

  public ArrayList<ResidentProfile> getList()
  {
    return residentProfiles;
  }
}
