package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.bodys.BodyTemplate;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.combat.ArmourTemplate;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.CreatureTypes;
import com.wurmonline.shared.constants.ItemMaterials;
import com.wurmonline.shared.constants.SoundNames;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.CreatureTemplateBuilder;

public class CustomCreatures {
    public static int HORSEMAN_CONQUEST_CID, HORSEMAN_WAR_CID, HORSEMAN_FAMINE_CID, HORSEMAN_DEATH_CID;

    public static boolean isHorseman(Creature creature) {
        return CustomCreatures.isHorseman(creature.getTemplate().getTemplateId());
    }

    private static boolean isHorseman(int templateId) {
        if (templateId == CustomCreatures.HORSEMAN_CONQUEST_CID) {
            return true;
        } else if (templateId == CustomCreatures.HORSEMAN_WAR_CID) {
            return true;
        } else if (templateId == CustomCreatures.HORSEMAN_FAMINE_CID) {
            return true;
        } else return templateId == CustomCreatures.HORSEMAN_DEATH_CID;
    }

    static void createHorsemanConquestTemplate() throws NoSuchFieldException, IllegalAccessException {
        final int[] types = {CreatureTypes.C_TYPE_MOVE_GLOBAL, CreatureTypes.C_TYPE_UNDEAD, CreatureTypes.C_TYPE_MONSTER, CreatureTypes.C_TYPE_HUMAN, CreatureTypes.C_TYPE_AGG_HUMAN, CreatureTypes.C_TYPE_HUNTING, CreatureTypes.C_TYPE_NON_NEWBIE, CreatureTypes.C_TYPE_NO_REBIRTH};
        final int[] itemsButchered = new int[]{ItemList.heart};
        final CreatureTemplate temp = new CreatureTemplateBuilder("horseman.creature.conquest")
                .name("Conquest")
                .description("Conquest, the rider of the White Horse (sometimes referred to as the White Rider) He carries a bow, and wears a victor's crown.")
                .modelName("model.creature.human.player")
                .types(types)
                .bodyType(BodyTemplate.TYPE_HUMAN)
                .defaultSkills()
                .skill(SkillList.BODY_STRENGTH, 40f)
                .skill(SkillList.BODY_CONTROL, 45f)
                .skill(SkillList.BODY_STAMINA, 50f)
                .skill(SkillList.MIND_LOGICAL, 40f)
                .skill(SkillList.MIND_SPEED, 40f)
                .skill(SkillList.SOUL_STRENGTH, 40f)
                .skill(SkillList.SOUL_DEPTH, 40f)
                .skill(SkillList.WEAPONLESS_FIGHTING, 40f)
                .vision((short) 5)
                .dimension((short) 180, (short) 50, (short) 250)
                .deathSounds(SoundNames.DEATH_MALE_SND, SoundNames.DEATH_FEMALE_SND)
                .hitSounds(SoundNames.HIT_MALE_SND, SoundNames.HIT_FEMALE_SND)
                .naturalArmour(0.05f)
                .damages(10f, 15f, 10f, 12f, 0f)
                .speed(2f)
                .moveRate(1500)
                .itemsButchered(itemsButchered)
                .meatMaterial(ItemMaterials.MATERIAL_MEAT_HUMAN)
                .maxHuntDist(10)
                .aggressive(99)
                .handDamString("punch")
                .kickDamString("kick")
                .headbuttDamString("headbutt")
                .maxAge(300)
                .armourType(ArmourTemplate.ARMOUR_TYPE_PLATE)
                .baseCombatRating(20f)
                .combatDamageType(Wound.TYPE_CRUSH)
                .alignment(100f)
                .maxPopulationOfCreatures(1)
                .build();

        temp.setCreatureAI(new GuardianCreatureAI());

        ReflectionUtil.setPrivateField(temp, ReflectionUtil.getField(CreatureTemplate.class, "corpsename"), "horse.butchered.");

        HORSEMAN_CONQUEST_CID = temp.getTemplateId();
    }

    static void createHorsemanWarTemplate() throws NoSuchFieldException, IllegalAccessException {
        final int[] types = {CreatureTypes.C_TYPE_MOVE_GLOBAL, CreatureTypes.C_TYPE_UNDEAD, CreatureTypes.C_TYPE_MONSTER, CreatureTypes.C_TYPE_HUMAN, CreatureTypes.C_TYPE_AGG_HUMAN, CreatureTypes.C_TYPE_HUNTING, CreatureTypes.C_TYPE_NON_NEWBIE, CreatureTypes.C_TYPE_NO_REBIRTH};
        final int[] itemsButchered = new int[]{ItemList.heart};
        final CreatureTemplate temp = new CreatureTemplateBuilder("horseman.creature.war")
                .name("War")
                .description("The rider of the second horse is often taken to represent War (he is often pictured holding a sword upwards as though ready for battle) or mass slaughter. His horse's color is red (πυρρός, from πῦρ, fire); and in some translations, the color is specifically a \"fiery\" red. The color red, as well as the rider's possession of a great sword, suggests blood that is to be spilled.")
                .modelName("model.creature.human.player")
                .types(types)
                .bodyType(BodyTemplate.TYPE_HUMAN)
                .defaultSkills()
                .skill(SkillList.BODY_STRENGTH, 40f)
                .skill(SkillList.BODY_CONTROL, 45f)
                .skill(SkillList.BODY_STAMINA, 50f)
                .skill(SkillList.MIND_LOGICAL, 40f)
                .skill(SkillList.MIND_SPEED, 40f)
                .skill(SkillList.SOUL_STRENGTH, 40f)
                .skill(SkillList.SOUL_DEPTH, 40f)
                .skill(SkillList.WEAPONLESS_FIGHTING, 40f)
                .vision((short) 5)
                .dimension((short) 180, (short) 50, (short) 250)
                .deathSounds(SoundNames.DEATH_MALE_SND, SoundNames.DEATH_FEMALE_SND)
                .hitSounds(SoundNames.HIT_MALE_SND, SoundNames.HIT_FEMALE_SND)
                .naturalArmour(0.05f)
                .damages(10f, 15f, 10f, 12f, 0f)
                .speed(2f)
                .moveRate(1500)
                .itemsButchered(itemsButchered)
                .meatMaterial(ItemMaterials.MATERIAL_MEAT_HUMAN)
                .maxHuntDist(10)
                .aggressive(99)
                .handDamString("punch")
                .kickDamString("kick")
                .headbuttDamString("headbutt")
                .maxAge(300)
                .armourType(ArmourTemplate.ARMOUR_TYPE_PLATE)
                .baseCombatRating(20f)
                .combatDamageType(Wound.TYPE_CRUSH)
                .alignment(100f)
                .maxPopulationOfCreatures(1)
                .build();

        temp.setCreatureAI(new GuardianCreatureAI());

        ReflectionUtil.setPrivateField(temp, ReflectionUtil.getField(CreatureTemplate.class, "corpsename"), "horse.butchered.");

        HORSEMAN_WAR_CID = temp.getTemplateId();
    }

    static void createHorsemanFamineTemplate() throws NoSuchFieldException, IllegalAccessException {
        final int[] types = {CreatureTypes.C_TYPE_MOVE_GLOBAL, CreatureTypes.C_TYPE_UNDEAD, CreatureTypes.C_TYPE_MONSTER, CreatureTypes.C_TYPE_HUMAN, CreatureTypes.C_TYPE_AGG_HUMAN, CreatureTypes.C_TYPE_HUNTING, CreatureTypes.C_TYPE_NON_NEWBIE, CreatureTypes.C_TYPE_NO_REBIRTH};
        final int[] itemsButchered = new int[]{ItemList.heart};
        final CreatureTemplate temp = new CreatureTemplateBuilder("horseman.creature.famine")
                .name("Famine")
                .description("The third Horseman rides a black horse and is popularly understood to be Famine as the Horseman carries a pair of balances or weighing scales, indicating the way that bread would have been weighed during a famine.")
                .modelName("model.creature.human.player")
                .types(types)
                .bodyType(BodyTemplate.TYPE_HUMAN)
                .defaultSkills()
                .skill(SkillList.BODY_STRENGTH, 40f)
                .skill(SkillList.BODY_CONTROL, 45f)
                .skill(SkillList.BODY_STAMINA, 50f)
                .skill(SkillList.MIND_LOGICAL, 40f)
                .skill(SkillList.MIND_SPEED, 40f)
                .skill(SkillList.SOUL_STRENGTH, 40f)
                .skill(SkillList.SOUL_DEPTH, 40f)
                .skill(SkillList.WEAPONLESS_FIGHTING, 40f)
                .vision((short) 5)
                .dimension((short) 180, (short) 50, (short) 250)
                .deathSounds(SoundNames.DEATH_MALE_SND, SoundNames.DEATH_FEMALE_SND)
                .hitSounds(SoundNames.HIT_MALE_SND, SoundNames.HIT_FEMALE_SND)
                .naturalArmour(0.05f)
                .damages(10f, 15f, 10f, 12f, 0f)
                .speed(2f)
                .moveRate(1500)
                .itemsButchered(itemsButchered)
                .meatMaterial(ItemMaterials.MATERIAL_MEAT_HUMAN)
                .maxHuntDist(10)
                .aggressive(99)
                .handDamString("punch")
                .kickDamString("kick")
                .headbuttDamString("headbutt")
                .maxAge(300)
                .armourType(ArmourTemplate.ARMOUR_TYPE_PLATE)
                .baseCombatRating(20f)
                .combatDamageType(Wound.TYPE_CRUSH)
                .alignment(100f)
                .maxPopulationOfCreatures(1)
                .build();

        temp.setCreatureAI(new GuardianCreatureAI());

        ReflectionUtil.setPrivateField(temp, ReflectionUtil.getField(CreatureTemplate.class, "corpsename"), "horse.butchered.");

        HORSEMAN_FAMINE_CID = temp.getTemplateId();
    }

    static void createHorsemanDeathTemplate() throws NoSuchFieldException, IllegalAccessException {
        final int[] types = {CreatureTypes.C_TYPE_MOVE_GLOBAL, CreatureTypes.C_TYPE_UNDEAD, CreatureTypes.C_TYPE_MONSTER, CreatureTypes.C_TYPE_HUMAN, CreatureTypes.C_TYPE_AGG_HUMAN, CreatureTypes.C_TYPE_HUNTING, CreatureTypes.C_TYPE_NON_NEWBIE, CreatureTypes.C_TYPE_NO_REBIRTH};
        final int[] itemsButchered = new int[]{ItemList.heart};
        final CreatureTemplate temp = new CreatureTemplateBuilder("horseman.creature.death")
                .name("Death")
                .description("The fourth and final Horseman is named Death. Known as \"Thanatos\", of all the riders, he is the only one to whom the text itself explicitly gives a name. Unlike the other three, he is not described carrying a weapon or other object, instead he is followed by Hades (the resting place of the dead). However, illustrations commonly depict him carrying a scythe (like the Grim Reaper), sword, or other implement.")
                .modelName("model.creature.human.player")
                .types(types)
                .bodyType(BodyTemplate.TYPE_HUMAN)
                .defaultSkills()
                .skill(SkillList.BODY_STRENGTH, 40f)
                .skill(SkillList.BODY_CONTROL, 45f)
                .skill(SkillList.BODY_STAMINA, 50f)
                .skill(SkillList.MIND_LOGICAL, 40f)
                .skill(SkillList.MIND_SPEED, 40f)
                .skill(SkillList.SOUL_STRENGTH, 40f)
                .skill(SkillList.SOUL_DEPTH, 40f)
                .skill(SkillList.WEAPONLESS_FIGHTING, 40f)
                .vision((short) 5)
                .dimension((short) 180, (short) 50, (short) 250)
                .deathSounds(SoundNames.DEATH_MALE_SND, SoundNames.DEATH_FEMALE_SND)
                .hitSounds(SoundNames.HIT_MALE_SND, SoundNames.HIT_FEMALE_SND)
                .naturalArmour(0.05f)
                .damages(10f, 15f, 10f, 12f, 0f)
                .speed(2f)
                .moveRate(1500)
                .itemsButchered(itemsButchered)
                .meatMaterial(ItemMaterials.MATERIAL_MEAT_HUMAN)
                .maxHuntDist(10)
                .aggressive(99)
                .handDamString("punch")
                .kickDamString("kick")
                .headbuttDamString("headbutt")
                .maxAge(300)
                .armourType(ArmourTemplate.ARMOUR_TYPE_PLATE)
                .baseCombatRating(20f)
                .combatDamageType(Wound.TYPE_CRUSH)
                .alignment(100f)
                .maxPopulationOfCreatures(1)
                .build();

        temp.setCreatureAI(new GuardianCreatureAI());

        ReflectionUtil.setPrivateField(temp, ReflectionUtil.getField(CreatureTemplate.class, "corpsename"), "horse.butchered.");

        HORSEMAN_DEATH_CID = temp.getTemplateId();
    }

}
