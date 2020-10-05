package org.requiem.mods.wurm.fourhorseman.titles;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.players.Achievement;
import com.wurmonline.server.players.AchievementTemplate;
import com.wurmonline.server.players.Achievements;
import com.wurmonline.server.players.Titles;
import net.bdew.wurm.tools.server.ModAchievements;
import org.requiem.mods.wurm.fourhorseman.ModConfig;

public class CustomAchievements {
    public static AchievementTemplate horsemanKiller, deathKiller;

    public static void register() {
        horsemanKiller = ModAchievements.build(ModConfig.horsemanKillerAchId)
                .name("Horseman Slayer")
                .description("You killed a horseman of the apocalypse")
                .achievementType(MiscConstants.A_TYPE_SILVER)
                .buildAndRegister();

        deathKiller = ModAchievements.build(ModConfig.deathKillerAchId)
                .name("Death and Taxes")
                .description("You killed the essence of death and saved the world, for now...")
                .achievementType(MiscConstants.A_TYPE_GOLD)
                .buildAndRegister();
    }

    private static Titles.Title getAwardedTitle(Achievement ach) {
        int count = ach.getCounter();
        if (ach.getTemplate() == deathKiller) {
            if (count >= 1)
                return Titles.Title.getTitle(ModConfig.apocalypseEnderTitleId);
        } else if (ach.getTemplate() == horsemanKiller) {
            if (count >= 1)
                return Titles.Title.getTitle(ModConfig.horsemanSlayerTitleId);
        }
        return null;
    }

    public static void triggerAchievement(Creature player, AchievementTemplate tpl) {
        Achievements.triggerAchievement(player.getWurmId(), tpl.getNumber());
        Achievements achievements = Achievements.getAchievementObject(player.getWurmId());
        Achievement achievement = achievements.getAchievement(tpl.getNumber());
        if (achievement != null) {
            Titles.Title title = getAwardedTitle(achievement);
            if (title != null) {
                player.addTitle(title);
            }
        }
    }

}