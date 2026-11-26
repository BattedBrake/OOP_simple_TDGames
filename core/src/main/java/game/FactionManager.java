package game;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionManager {

    public static final int FACTION_THRESHOLD = 3;
    private final Map<FactName, Integer> factionThreshold = new HashMap<>();

    public FactionManager() {
        factionThreshold.put(FactName.KINGDOM, FACTION_THRESHOLD);
        factionThreshold.put(FactName.FANTASY, FACTION_THRESHOLD);
    }

    public void updateFactionBuffs(List<Troop> troops) {
        Map<FactName, Integer> countMap = countFactions(troops);
        for (Troop t : troops) {
            FactName f = t.getFaction();
            int required = factionThreshold.getOrDefault(f, Integer.MAX_VALUE);
            int actual = countMap.getOrDefault(f, 0);
            if (actual >= required) {
                getFactionEffect(f).applyBuff(t);
            }
        }
    }

    public int getFactionCount(List<Troop> troops, FactName faction) {
        return countFactions(troops).getOrDefault(faction, 0);
    }

    private Map<FactName, Integer> countFactions(List<Troop> troops) {
        Map<FactName, Integer> counts = new HashMap<>();
        for (Troop troop : troops) {
            counts.put(troop.getFaction(), counts.getOrDefault(troop.getFaction(), 0) + 1);
        }
        return counts;
    }

    private Faction getFactionEffect(FactName faction) {
        return faction == FactName.KINGDOM ? new KingdomFaction() : new FantasyFaction();
    }
}
