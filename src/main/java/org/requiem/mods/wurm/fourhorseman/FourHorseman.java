package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.requiem.mods.wurm.fourhorseman.titles.CustomAchievements;
import org.requiem.mods.wurm.fourhorseman.titles.CustomTitles;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FourHorseman implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, ServerShutdownListener {
    private static final Logger logger = Logger.getLogger("FourHorseman");

    public static void logException(String msg, Throwable e) {
        if (logger != null)
            logger.log(Level.SEVERE, msg, e);
    }

    public static void logWarning(String msg) {
        if (logger != null)
            logger.log(Level.WARNING, msg);
    }

    public static void logInfo(String msg) {
        if (logger != null)
            logger.log(Level.INFO, msg);
    }

    @Override
    public void configure(Properties properties) {

    }

    @Override
    public void preInit() {
        CustomTitles.register();
    }

    @Override
    public void init() {
    }

    @Override
    public void onItemTemplatesCreated() {
        try {
            CustomCreatures.registerCustomCreatures();
            CustomItems.registerApocalypseStone();
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        CustomAchievements.register();
    }

    @Override
    public void onServerStarted() {
        ApocalypseStoneTracker.started();
    }

    @Override
    public void onServerPoll() {
        ApocalypseStoneTracker.tick();
    }

    @Override
    public void onServerShutdown() {
        ApocalypseStoneTracker.removeStones();
    }

    public String getVersion() {
        return "1.0";
    }

}