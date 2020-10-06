package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.bodys.BodyTemplate;
import com.wurmonline.server.creatures.*;
import com.wurmonline.server.items.*;
import com.wurmonline.server.sounds.SoundPlayer;
import com.wurmonline.server.spells.SpellEffect;
import com.wurmonline.shared.constants.Enchants;
import com.wurmonline.shared.constants.ItemMaterials;
import com.wurmonline.shared.constants.SoundNames;

public class Spawn {

    private static void applyEnchant(Item item, byte enchant, float power) {
        ItemSpellEffects effs = item.getSpellEffects();
        if (effs == null) {
            effs = new ItemSpellEffects(item.getWurmId());
        }
        SpellEffect eff = new SpellEffect(item.getWurmId(), enchant, power, 20000000);
        effs.addSpellEffect(eff);
        if (item.getDescription().length() > 0) {
            item.setDescription(item.getDescription() + " ");
        }
        item.setDescription(item.getDescription() + eff.getName().substring(0, 1) + Math.round(power));
    }

    private static Item horsemanItemSpawn(int itemId) throws NoSuchTemplateException, FailedException {
        return ItemFactory.createItem(itemId, 50f + (Server.rand.nextFloat() * 30f), ItemMaterials.MATERIAL_STEEL, MiscConstants.COMMON, "Black Knight");
    }

    public static void equipHorseman(Creature creature) {
        try {
            Item twoHandSword = horsemanItemSpawn(ItemList.swordTwoHander);
            Item greatHelm = horsemanItemSpawn(ItemList.helmetGreat);
            Item steelChest = horsemanItemSpawn(ItemList.plateJacket);
            Item steelLegs = horsemanItemSpawn(ItemList.plateHose);
            Item steelLeftGlove = horsemanItemSpawn(ItemList.plateGauntlet);
            Item steelRightGlove = horsemanItemSpawn(ItemList.plateGauntlet);
            Item steelRightBoot = horsemanItemSpawn(ItemList.plateBoot);
            Item steelLeftBoot = horsemanItemSpawn(ItemList.plateBoot);
            Item steelRightSleeve = horsemanItemSpawn(ItemList.plateSleeve);
            Item steelLeftSleeve = horsemanItemSpawn(ItemList.plateSleeve);
            Item steelRightShoulder = horsemanItemSpawn(ItemList.shoulderPads11);
            Item steelLeftShoulder = horsemanItemSpawn(ItemList.shoulderPads11);

            //creature.setKingdomId(MiscConstants.KINGDOM_HOTS, true, false, creature.hasLink());
            //SoundPlayer.playSound(dramatic_sound, creature, 0f);// play dramatic sound
            // Sword
            int roll = Server.rand.nextInt(100);
            if (roll >= 0) {
                twoHandSword.setName("Ashbringer");
                twoHandSword.enchant((byte) 12);
                applyEnchant(twoHandSword, Enchants.BUFF_NIMBLENESS, 75);
                applyEnchant(twoHandSword, Enchants.BUFF_FLAMING_AURA, 90);
                twoHandSword.setColor(0);
                twoHandSword.setColor2(0);
            }
            if (roll >= 40) {
                twoHandSword.setName("Zulfiqar");
                twoHandSword.enchant((byte) 11);
                applyEnchant(twoHandSword, Enchants.BUFF_NIMBLENESS, 80);
                applyEnchant(twoHandSword, Enchants.BUFF_FROSTBRAND, 100);
                applyEnchant(twoHandSword, Enchants.BUFF_CIRCLE_CUNNING, 90);
                twoHandSword.setColor(0);
                twoHandSword.setColor2(0);
            }
            if (roll >= 75) {
                twoHandSword.setName("Apocalypse");
                twoHandSword.enchant((byte) 9);
                applyEnchant(twoHandSword, Enchants.BUFF_NIMBLENESS, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_ROTTING_TOUCH, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_MINDSTEALER, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_CIRCLE_CUNNING, 104);
                twoHandSword.setColor(0);
                twoHandSword.setColor2(0);
                twoHandSword.setMaterial(ItemMaterials.MATERIAL_ADAMANTINE);
            }
            if (roll >= 90) {
                twoHandSword.setName("World-Destroyer");
                twoHandSword.enchant((byte) 10);
                applyEnchant(twoHandSword, Enchants.BUFF_BLOODTHIRST, 10000);
                applyEnchant(twoHandSword, Enchants.BUFF_ROTTING_TOUCH, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_NIMBLENESS, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_CIRCLE_CUNNING, 104);
                applyEnchant(twoHandSword, Enchants.BUFF_BLOODTHIRST, 104);
                twoHandSword.setColor(0);
                twoHandSword.setColor2(0);
                twoHandSword.setMaterial(ItemMaterials.MATERIAL_GLIMMERSTEEL);
            }
            // Chest
            applyEnchant(steelChest, Enchants.BUFF_SHARED_PAIN, 104);
            steelChest.setColor(0);
            steelChest.setColor2(0);
            // Legs
            applyEnchant(steelLegs, Enchants.BUFF_SHARED_PAIN, 104);
            steelLegs.setColor(0);
            steelLegs.setColor2(0);
            // Sleeves
            applyEnchant(steelLeftSleeve, Enchants.BUFF_SHARED_PAIN, 104);
            applyEnchant(steelRightSleeve, Enchants.BUFF_SHARED_PAIN, 104);
            steelLeftSleeve.setColor(0);
            steelLeftSleeve.setColor2(0);
            steelRightSleeve.setColor(0);
            steelRightSleeve.setColor2(0);
            // Gloves
            applyEnchant(steelRightGlove, Enchants.BUFF_SHARED_PAIN, 104);
            applyEnchant(steelLeftGlove, Enchants.BUFF_SHARED_PAIN, 104);
            steelLeftGlove.setColor(0);
            steelLeftGlove.setColor2(0);
            steelRightGlove.setColor(0);
            steelRightGlove.setColor2(0);
            // Helm
            applyEnchant(greatHelm, Enchants.BUFF_SHARED_PAIN, 104);
            greatHelm.setColor(0);
            greatHelm.setColor2(0);
            // Boots
            applyEnchant(steelLeftBoot, Enchants.BUFF_SHARED_PAIN, 104);
            applyEnchant(steelRightBoot, Enchants.BUFF_SHARED_PAIN, 104);
            steelLeftBoot.setColor(0);
            steelLeftBoot.setColor2(0);
            steelRightBoot.setColor(0);
            steelRightBoot.setColor2(0);
            // Shoulders
            steelRightShoulder.setColor(0);
            steelRightShoulder.setColor2(0);
            steelLeftShoulder.setColor(0);
            steelLeftShoulder.setColor2(0);
            // Add armor and sword to black knight
            creature.getBody().getBodyPart(BodyTemplate.rightHand).insertItem(twoHandSword, true);
            creature.getBody().getBodyPart(BodyTemplate.rightHand).insertItem(steelRightGlove, true);
            creature.getBody().getBodyPart(BodyTemplate.leftHand).insertItem(steelLeftGlove, true);
            creature.getBody().getBodyPart(BodyTemplate.head).insertItem(greatHelm, true);
            creature.getBody().getBodyPart(BodyTemplate.leftArm).insertItem(steelLeftSleeve, true);
            creature.getBody().getBodyPart(BodyTemplate.rightArm).insertItem(steelRightSleeve, true);
            creature.getBody().getBodyPart(BodyTemplate.legs).insertItem(steelLegs, true);
            creature.getBody().getBodyPart(BodyTemplate.leftFoot).insertItem(steelLeftBoot, true);
            creature.getBody().getBodyPart(BodyTemplate.rightFoot).insertItem(steelRightBoot, true);
            creature.getBody().getBodyPart(BodyTemplate.torso).insertItem(steelChest, true);
            creature.getBody().getBodyPart(BodyTemplate.leftShoulder).insertItem(steelLeftShoulder, true);
            creature.getBody().getBodyPart(BodyTemplate.rightShoulder).insertItem(steelRightShoulder, true);
        } catch (NoSpaceException | FailedException | NoSuchTemplateException e) {
            e.printStackTrace();
        }
    }

    public static void spawnHorseman(Creature horseman, int cid) {
        try {
            float x = horseman.getPosX();
            float y = horseman.getPosY();
            CreatureTemplate template = CreatureTemplateFactory.getInstance().getTemplate(cid);
            Creature newHorseman = Creature.doNew(template.getTemplateId(), false, x, y, Server.rand.nextFloat() * 360f, horseman.getLayer(), template.getName(), MiscConstants.SEX_MALE, horseman.getKingdomId(), BodyTemplate.TYPE_HUMAN, false, /*age*/(byte) (20 + Server.rand.nextInt(50)));
            Creature horse;
            //TODO
            // FIX ME
            switch (cid) {
                case CustomCreatures.horsemanConquestId:
                    horse = Creature.doNew(CustomCreatures.horsemanConquestHorseId, newHorseman.getPosX(), newHorseman.getPosY(), 365f, newHorseman.getLayer(), String.format("%s's steed", newHorseman.getNameWithoutPrefixes()), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
                    break;
                case CustomCreatures.horsemanWarId:
                    horse = Creature.doNew(CustomCreatures.horsemanWarHorseId, newHorseman.getPosX(), newHorseman.getPosY(), 365f, newHorseman.getLayer(), String.format("%s's steed", newHorseman.getNameWithoutPrefixes()), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
                    break;
                case CustomCreatures.horsemanFamineId:
                    horse = Creature.doNew(CustomCreatures.horsemanFamineHorseId, newHorseman.getPosX(), newHorseman.getPosY(), 365f, newHorseman.getLayer(), String.format("%s's steed", newHorseman.getNameWithoutPrefixes()), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
                    break;
                case CustomCreatures.horsemanDeathId:
                    horse = Creature.doNew(CustomCreatures.horsemanDeathHorseId, newHorseman.getPosX(), newHorseman.getPosY(), 365f, newHorseman.getLayer(), String.format("%s's steed", newHorseman.getNameWithoutPrefixes()), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
                    break;
                default:
                    horse = Creature.doNew(CreatureTemplateIds.HORSE_CID, newHorseman.getPosX(), newHorseman.getPosY(), 365f, newHorseman.getLayer(), String.format("%s's steed", newHorseman.getNameWithoutPrefixes()), Server.rand.nextBoolean() ? MiscConstants.SEX_MALE : MiscConstants.SEX_FEMALE);
            }
            // hopefully make the new horseman mount the newly spawned horse
            Vehicle vehicle;
            vehicle = Vehicles.getVehicle(horse);
            final MountAction mountAction = new MountAction(newHorseman, null, vehicle, 0, true, 0);
            newHorseman.setMountAction(mountAction);
            newHorseman.setVehicle(horse.getWurmId(), true, (byte) 0);
            // make a horse sound when it is spawned
            SoundPlayer.playSound(SoundNames.HIT_HORSE_SND, newHorseman, 0f);
            // display a message when spawned
            FourHorseman.logInfo(String.format("Spawning %s, a horseman of the apocalypse!", newHorseman.getNameWithoutPrefixes()));
            String message = String.format("The horseman %s appears from the void ready to fight!", newHorseman.getNameWithoutPrefixes());
            Server.getInstance().broadCastAlert(message, true, (byte) 1);
            // equip the horseman with a weapon and armor
            equipHorseman(newHorseman);
        } catch (Exception e) {
            FourHorseman.logException(String.format("Error in Spawning %s", horseman.getTemplate().getName()), e);
            e.printStackTrace();
        }
    }

}