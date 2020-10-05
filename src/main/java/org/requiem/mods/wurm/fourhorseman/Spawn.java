package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.Server;
import com.wurmonline.server.bodys.BodyTemplate;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.sounds.SoundPlayer;
import com.wurmonline.shared.constants.SoundNames;

public class Spawn {

    private static void spawnHorseman(Creature creature, int cid) {
        try {
            float x = creature.getPosX();
            float y = creature.getPosY();
            if (creature.getAttackers() != 0) {
                FourHorseman.logInfo(String.format("Spawning %s, a horseman of the apocalypse!", creature.getNameWithoutPrefixes()));
                CreatureTemplate template = CreatureTemplateFactory.getInstance().getTemplate(cid);
                Creature horseman = Creature.doNew(template.getTemplateId(), false, x, y, Server.rand.nextFloat() * 360f, creature.getLayer(), template.getName(), MiscConstants.SEX_MALE, creature.getKingdomId(), BodyTemplate.TYPE_HUMAN, false, /*age*/(byte) (20 + Server.rand.nextInt(50)));
                horseman.putInWorld();
                Creature horse = Creature.doNew(CreatureTemplateIds.HELL_HORSE_CID, horseman.getPosX(), horseman.getPosY(), 365f, horseman.getLayer(), horseman.getName(), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
                horseman.setVehicle(horse.getWurmId(), true, (byte) 3);
                SoundPlayer.playSound(SoundNames.HIT_HORSE_SND, creature, 0f);
                if (cid == CustomCreatures.HORSEMAN_CONQUEST_CID) {
                    Server.getInstance().broadCastAction("The four seals have been broken! Mankind shall be judged and punished accordingly!", creature, 30);
                }
                Server.getInstance().broadCastAction(String.format("The horseman %s appears from the void ready to fight!", creature.getTemplate().getName()), creature, 30);
            }
        } catch (Exception e) {
            FourHorseman.logException(String.format("Error in Spawning %s", creature.getTemplate().getName()), e);
            e.printStackTrace();
        }
    }

    public static void checkLootTable(Creature mob, Item corpse) {
        if (mob.getTemplate().getTemplateId() == CustomCreatures.HORSEMAN_CONQUEST_CID) {
            // Spawn the horseman war when conquest dies
            spawnHorseman(mob, CustomCreatures.HORSEMAN_WAR_CID);
        } else if (mob.getTemplate().getTemplateId() == CustomCreatures.HORSEMAN_WAR_CID) {
            // Spawn the horseman famine when war dies
            spawnHorseman(mob, CustomCreatures.HORSEMAN_FAMINE_CID);
        } else if (mob.getTemplate().getTemplateId() == CustomCreatures.HORSEMAN_FAMINE_CID) {
            // Spawn the horseman death when famine dies
            spawnHorseman(mob, CustomCreatures.HORSEMAN_DEATH_CID);
        }
    }

}