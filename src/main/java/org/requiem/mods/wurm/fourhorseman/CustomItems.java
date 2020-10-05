package org.requiem.mods.wurm.fourhorseman;

import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.shared.constants.IconConstants;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import java.io.IOException;

public class CustomItems {
    public static int apocalypseStoneId;

    static void registerApocalypseStone() throws IOException {
        ItemTemplate temp = new ItemTemplateBuilder("horseman.item.apocalypseStone")
                .name("apocalypse stone", "apocalypse stone", "A stone sent from the gods to judge mankind.")
                .modelName("model.decoration.gravestone.buried.")
                .imageNumber((short) IconConstants.ICON_NONE)
                .weightGrams(200000)
                .dimensions(100, 100, 400)
                .decayTime(Long.MAX_VALUE)
                .material(ItemMaterials.MATERIAL_STONE)
                .behaviourType(BehaviourList.itemBehaviour)
                .itemTypes(new short[]{
                        ItemTypes.ITEM_TYPE_NOMOVE,
                        ItemTypes.ITEM_TYPE_NOTAKE,
                        ItemTypes.ITEM_TYPE_NOT_MISSION,
                        ItemTypes.ITEM_TYPE_INDESTRUCTIBLE,
                        ItemTypes.ITEM_TYPE_DECORATION,
                })
                .build();

        apocalypseStoneId = temp.getTemplateId();
    }

}
