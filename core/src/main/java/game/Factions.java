package game;

public class Factions {

}

class KingdomFaction implements Faction {
    @Override
    public void applyBuff(Troop unit) {
        unit.applyRangeBonusOnce(15f);
    }
}

class FantasyFaction implements Faction {
    @Override
    public void applyBuff(Troop unit) {
        unit.applyAttackBonusOnce(1);
    }
}
