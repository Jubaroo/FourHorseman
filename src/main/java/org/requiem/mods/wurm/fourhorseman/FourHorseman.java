package org.requiem.mods.wurm.fourhorseman;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.requiem.mods.wurm.fourhorseman.titles.CustomAchievements;
import org.requiem.mods.wurm.fourhorseman.titles.CustomTitles;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FourHorseman implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener {
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
        try {
            ClassPool classPool = HookManager.getInstance().getClassPool();
            CtClass ctCreature = classPool.get("com.wurmonline.server.creatures.Creature");
            // Modify creatures on death
            ctCreature.getDeclaredMethod("die").instrument(new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("setRotation")) {
                        m.replace("$_ = $proceed($$);"
                                + Spawn.class.getName() + ".checkLootTable(this, corpse);");
                        logger.info("Instrumented setRotation to call insertCorpseItems as well.");
                    }
                }
            });
        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemTemplatesCreated() {
        try {
            CustomCreatures.createHorsemanConquestTemplate();
            CustomCreatures.createHorsemanWarTemplate();
            CustomCreatures.createHorsemanFamineTemplate();
            CustomCreatures.createHorsemanDeathTemplate();
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

}