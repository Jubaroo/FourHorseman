package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.FailedException;
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
        FourHorseman.logInfo(String.format("Removing apocalypseStone %d", apocalypseStone.getWurmId()));
        apocalypseStones.remove(apocalypseStone);
        Set<Creature> horsemanToRemove = horseman.remove(apocalypseStone.getWurmId());
        if (horsemanToRemove != null) {
            horsemanToRemove.stream().filter(i -> !i.isDead()).forEach(Creature::destroy);
        }
    }

    public static void tick() {
        if (apocalypseStones.size() < 1)
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
            FourHorseman.logInfo(String.format("Loaded %d apocalypseStone", apocalypseStones.size()));
    }

    public static void spawnGravestoneAt(VolaTile vt) {
        try {
            Item gravestone = ItemFactory.createItem(CustomItems.apocalypseStoneId, 99f, (byte) 0, null);
            vt.addItem(gravestone, false, false);
            FourHorseman.logInfo(String.format("Spawned gravestone at %d,%d", vt.tilex, vt.tiley));
            addApocalypseStone(gravestone);
            for (int i = 0; i < 1; i++) {
                try {
                    int tpl;
                    tpl = CustomCreatures.HORSEMAN_CONQUEST_CID;
                    Creature spawned = Creature.doNew(
                            tpl,
                            CreatureTypes.C_MOD_NONE,
                            gravestone.getPosX() - 5f + Server.rand.nextFloat() * 10,
                            gravestone.getPosY() - 5f + Server.rand.nextFloat() * 10,
                            Server.rand.nextInt(360),
                            gravestone.isOnSurface() ? 0 : -1, "", (byte) 0);
                    GuardianCreatureAIData.get(spawned).guarded = gravestone;
                    addGuard(gravestone, spawned);
                } catch (Exception e) {
                    FourHorseman.logException("Error spawning defenders for gravestone", e);
                }
            }
        } catch (FailedException | NoSuchTemplateException e) {
            FourHorseman.logException("Error spawning gravestone", e);
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
