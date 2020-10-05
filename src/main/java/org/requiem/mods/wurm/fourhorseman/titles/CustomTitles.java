package org.requiem.mods.wurm.fourhorseman.titles;

import net.bdew.wurm.tools.server.ModTitles;
import org.requiem.mods.wurm.fourhorseman.ModConfig;

public class CustomTitles {
    public static void register() {
        ModTitles.addTitle(ModConfig.apocalypseEnderTitleId, "Apocalypse-ender", "Apocalypse-ender", -1, "NORMAL");
        ModTitles.addTitle(ModConfig.horsemanSlayerTitleId, "Horseman Slayer", "Horseman Slayer", -1, "NORMAL");
    }

}