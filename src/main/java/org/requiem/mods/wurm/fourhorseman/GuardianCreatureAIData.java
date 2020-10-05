package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.ai.CreatureAIData;
import com.wurmonline.server.items.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GuardianCreatureAIData extends CreatureAIData {
    public static final Set<Item> apocalypseStone = new HashSet<>();
    public static final Map<Long, Set<Creature>> guards = new HashMap<>();
    public Item guarded;
    public int pathAttempts;

    public static GuardianCreatureAIData get(Creature creature) {
        return (GuardianCreatureAIData) creature.getCreatureAIData();
    }

    public static void addGuard(Item item, Creature guard) {
        guards.get(item.getWurmId()).add(guard);
    }

    public boolean tryFindGuarded() {
        for (Item apocalypseStone : GuardianCreatureAIData.apocalypseStone) {
            if (!apocalypseStone.deleted && apocalypseStone.getPos2f().distance(getCreature().getPos2f()) < 16) {
                guarded = apocalypseStone;
                GuardianCreatureAIData.addGuard(guarded, getCreature());
                FourHorseman.logInfo(String.format("Associated %s (%d) with %s (%d)", getCreature().getName(), getCreature().getWurmId(), guarded.getName(), guarded.getWurmId()));
                return false;
            }
        }
        FourHorseman.logInfo(String.format("Failed to find guarded object for %s (%d) - despawning", getCreature().getName(), getCreature().getWurmId()));
        getCreature().destroy();
        return true;
    }
}