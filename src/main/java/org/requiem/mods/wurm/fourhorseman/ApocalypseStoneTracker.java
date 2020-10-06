package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import com.wurmonline.shared.constants.CreatureTypes;

import java.util.*;

public class ApocalypseStoneTracker {
    public static final Set<Item> apocalypseStones = new HashSet<>();
    public static final Map<Long, Set<Creature>> horseman = new HashMap<>();

    private static long lastCheck = System.currentTimeMillis();

    public static void addApocalypseStone(Item apocalypseStone) {
        apocalypseStones.add(apocalypseStone);
        horseman.put(apocalypseStone.getWurmId(), new HashSet<>());
    }

    public static void addGuard(Item apocalypseStone, Creature guard) {
        horseman.get(apocalypseStone.getWurmId()).add(guard);
    }

    public static void apocalypseStoneRemoved(Item apocalypseStone) {
        FourHorseman.logInfo(String.format("Removing Apocalypse Stone %d at %d %d", apocalypseStone.getWurmId(), apocalypseStone.getTileX(), apocalypseStone.getTileY()));
        apocalypseStones.remove(apocalypseStone);
        Set<Creature> horsemanToRemove = horseman.remove(apocalypseStone.getWurmId());
        if (horsemanToRemove != null) {
            horsemanToRemove.stream().filter(i -> !i.isDead()).forEach(Creature::destroy);
        }
    }

    public static void tick() {
        for (Item item : Items.getAllItems()) {
            if (item.getTemplateId() == CustomItems.apocalypseStoneId) {
                ApocalypseStoneTracker.addApocalypseStone(item);
            }
        }
        if (apocalypseStones.size() == 0)
            spawnRandomApocalypseStone();
        if (System.currentTimeMillis() - lastCheck > 10000) {
            lastCheck = System.currentTimeMillis();
            for (Item apocalypseStone : new LinkedList<>(apocalypseStones)) {
                if (apocalypseStone.deleted) {
                    apocalypseStoneRemoved(apocalypseStone);
                } else {
                    Set<Creature> horsemanToCheck = horseman.get(apocalypseStone.getWurmId());
                    if (horsemanToCheck != null && !horsemanToCheck.isEmpty()) {
                        horsemanToCheck.removeIf(Creature::isDead);
                    }
                }
            }
        }
    }

    public static void started() {
        if (apocalypseStones.size() > 0)
            FourHorseman.logInfo(String.format("Loaded %d Apocalypse Stones", apocalypseStones.size()));
        for (Item item : Items.getAllItems()) {
            if (apocalypseStones.size() > 0) {
                if (item.getTemplateId() == CustomItems.apocalypseStoneId) {
                    FourHorseman.logInfo(String.format("Apocalypse Stone located and remembered, with name: %s, templateId: %d ,and wurmId: %d", item.getName(), CustomItems.apocalypseStoneId, item.getWurmId()));
                    addApocalypseStone(item);
                }
            }
            break;
        }
    }

    public static void removeStones() {
        for (int i = 0; i < apocalypseStones.size(); i++) {
            for (Item item : Items.getAllItems()) {
                if (item.getTemplateId() == CustomItems.apocalypseStoneId) {
                    apocalypseStoneRemoved(item);
                }
            }
        }
    }

    public static void spawnGravestoneAt(VolaTile vt) {
        try {
            Item apocalypseStone = ItemFactory.createItem(CustomItems.apocalypseStoneId, 99f, MiscConstants.COMMON, null);
            vt.addItem(apocalypseStone, false, false);
            FourHorseman.logInfo(String.format("Spawned Apocalypse %d Stone at %d,%d",apocalypseStone.getWurmId(), vt.tilex, vt.tiley));
            addApocalypseStone(apocalypseStone);
            try {
                Creature spawned = Creature.doNew(CustomCreatures.horsemanConquestId, CreatureTypes.C_MOD_NONE, apocalypseStone.getPosX() - 5f + Server.rand.nextFloat() * 10, apocalypseStone.getPosY() - 5f + Server.rand.nextFloat() * 10, Server.rand.nextInt(360), apocalypseStone.isOnSurface() ? 0 : -1, "", MiscConstants.SEX_MALE);
                GuardianCreatureAIData.get(spawned).guarded = apocalypseStone;
                addGuard(apocalypseStone, spawned);
                // equip the horseman with a weapon and armor
                Spawn.equipHorseman(spawned);
                // display a message when spawned
                String m1 = "The four seals have been broken! Mankind shall be judged and punished accordingly!";
                String m2 = "The 4 Horseman of the apocalypse have begun to enter our mortal realm to end the lives of all living things. Stop them before they complete their task.";
                Server.getInstance().broadCastAlert(m1, true, (byte) 1);
                Server.getInstance().broadCastAlert(m2, true, (byte) 1);
            } catch (Exception e) {
                FourHorseman.logException("Error spawning defenders for Apocalypse Stone", e);
            }

        } catch (FailedException | NoSuchTemplateException e) {
            FourHorseman.logException("Error spawning Apocalypse Stone", e);
        }

    }

    private static void spawnRandomApocalypseStone() {
        int tileX = Server.rand.nextInt(Zones.worldTileSizeX);
        int tileY = Server.rand.nextInt(Zones.worldTileSizeY);
        int tile = Server.surfaceMesh.getTile(tileX, tileY);

        byte type = Tiles.decodeType(tile);

        if (Tiles.decodeHeight(tile) < 0 || Tiles.getTile(type).isRoad()) return;

        VolaTile vt = Zones.getOrCreateTile(tileX, tileY, true);

        if (vt.getVillage() != null || vt.getStructure() != null || vt.getItems().length > 0)
            return;

        int woodTiles = 0;

        for (int x = tileX - 2; x <= tileX + 2; x++) {
            for (int y = tileY - 2; y <= tileY + 2; y++) {
                if (x < 0 || y < 0 || x >= Zones.worldTileSizeX || y >= Zones.worldTileSizeY) return;
                Tiles.Tile check = Tiles.getTile(Tiles.decodeType(Server.surfaceMesh.getTile(x, y)));
                if (check.isTree() || check.isBush()) woodTiles++;
            }
        }

        if (woodTiles < 5) return;

        spawnGravestoneAt(vt);
    }
}
