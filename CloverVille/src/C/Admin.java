package C;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {

    private final ObservableList<Resident> residents =
            FXCollections.observableArrayList();

    private final ObservableList<GreenAction> greenActions =
            FXCollections.observableArrayList();

    private final ObservableList<TradeOffer> tradeOffers =
            FXCollections.observableArrayList();

    private final ObservableList<Milestone> milestones =
            FXCollections.observableArrayList();

    private final ObservableList<Boost> boosts =
            FXCollections.observableArrayList();

    // === GETTERI ===
    public ObservableList<Resident> getResidents() {
        return residents;
    }

    public ObservableList<GreenAction> getGreenActions() {
        return greenActions;
    }

    public ObservableList<TradeOffer> getTradeOffers() {
        return tradeOffers;
    }

    public ObservableList<Milestone> getMilestones() {
        return milestones;
    }

    public ObservableList<Boost> getBoosts() {
        return boosts;
    }
}
