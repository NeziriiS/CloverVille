package C;
import java.util.ArrayList;

public class tradeOfferList
{
  private ArrayList<TradeOffer> tradeOffers = new ArrayList<>();

  public void addTradeOffer(TradeOffer tradeOffer)
  {
    tradeOffers.add(tradeOffer);
  }

  public void removeTradeOffer(TradeOffer tradeOffer)
  {
    tradeOffers.remove(tradeOffer);
  }

  public ArrayList<TradeOffer> getList()
  {
    return tradeOffers;
  }
}
