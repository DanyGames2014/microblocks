package net.danygames2014.microblocks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.*;
import net.danygames2014.microblocks.item.base.HandSawItem;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.nyalib.event.MultipartComponentRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.resource.language.TranslationInvalidationEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Microblocks {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    public static Item ironHandsaw;
    public static Item diamondHandsaw;

    public static ObjectArrayList<MicroblockItem> microblockItems = new ObjectArrayList<>();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        ironHandsaw = new HandSawItem(NAMESPACE.id("iron_handsaw"), 100).setTranslationKey(NAMESPACE, "iron_handsaw");
        diamondHandsaw = new HandSawItem(NAMESPACE.id("diamond_handsaw"), 1000).setTranslationKey(NAMESPACE, "diamond_handsaw");


        for (Block block : BlockRegistry.INSTANCE.stream().toList()) {
            Identifier identifier = BlockRegistry.INSTANCE.getId(block);
            
            if (identifier != null && block.getTexture(0) != 0) {
                String coverId = identifier.namespace + "_" + identifier.path + "_cover";
                microblockItems.add((MicroblockItem) new CoverMicroblockItem(NAMESPACE.id(coverId), block).setTranslationKey(NAMESPACE, coverId));

                String panelId = identifier.namespace + "_" + identifier.path + "_panel";
                microblockItems.add((MicroblockItem) new PanelMicroblockItem(NAMESPACE.id(panelId), block).setTranslationKey(NAMESPACE, panelId));

                String slabId = identifier.namespace + "_" + identifier.path + "_slab";
                microblockItems.add((MicroblockItem) new SlabMicroblockItem(NAMESPACE.id(slabId), block).setTranslationKey(NAMESPACE, slabId));

                String hollowCoverId = identifier.namespace + "_" + identifier.path + "_hollow_cover";
                microblockItems.add((MicroblockItem) new HollowCoverMicroblockItem(NAMESPACE.id(hollowCoverId), block).setTranslationKey(NAMESPACE, hollowCoverId));

                String hollowPanelId = identifier.namespace + "_" + identifier.path + "_hollow_panel";
                microblockItems.add((MicroblockItem) new HollowPanelMicroblockItem(NAMESPACE.id(hollowPanelId), block).setTranslationKey(NAMESPACE, hollowPanelId));

                String hollowSlabId = identifier.namespace + "_" + identifier.path + "_hollow_slab";
                microblockItems.add((MicroblockItem) new HollowSlabMicroblockItem(NAMESPACE.id(hollowSlabId), block).setTranslationKey(NAMESPACE, hollowSlabId));

                String stripCornerId = identifier.namespace + "_" + identifier.path + "_corner";
                microblockItems.add((MicroblockItem) new StripCornerMicroblockItem(NAMESPACE.id(stripCornerId), block).setTranslationKey(NAMESPACE, stripCornerId));

                String panelCornerId = identifier.namespace + "_" + identifier.path + "_panel_corner";
                microblockItems.add((MicroblockItem) new PanelCornerMicroblockItem(NAMESPACE.id(panelCornerId), block).setTranslationKey(NAMESPACE, panelCornerId));

                String slabCornerId = identifier.namespace + "_" + identifier.path + "_slab_corner";
                microblockItems.add((MicroblockItem) new SlabCornerMicroblockItem(NAMESPACE.id(slabCornerId), block).setTranslationKey(NAMESPACE, slabCornerId));

                String stripId = identifier.namespace + "_" + identifier.path + "_strip";
                microblockItems.add((MicroblockItem) new StripMicroblockItem(NAMESPACE.id(stripId), block).setTranslationKey(NAMESPACE, stripId));

                String panelStripId = identifier.namespace + "_" + identifier.path + "_panel_strip";
                microblockItems.add((MicroblockItem) new PanelStripMicroblockItem(NAMESPACE.id(panelStripId), block).setTranslationKey(NAMESPACE, panelStripId));

                String slabStripId = identifier.namespace + "_" + identifier.path + "_slab_strip";
                microblockItems.add((MicroblockItem) new SlabStripMicroblockItem(NAMESPACE.id(slabStripId), block).setTranslationKey(NAMESPACE, slabStripId));
            }
        }
    }

    @EventListener
    public void registerMultiparts(MultipartComponentRegistryEvent event) {
        event.register(NAMESPACE.id("face_microblock_component"), FaceMicroblockMultipartComponent.class, FaceMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("corner_microblock_component"), CornerMicroblockMultipartComponent.class, CornerMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("edge_microblock_component"), EdgeMicroblockMultipartComponent.class, EdgeMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("post_microblock_component"), PostMicroblockMultipartComponent.class, PostMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("hollow_microblock_component"), HollowMicroblockMultipartComponent.class, HollowMicroblockMultipartComponent::new);
    }

    @EventListener
    public void registerTranslations(TranslationInvalidationEvent event) {
        TranslationStorage storage = TranslationStorage.getInstance();
        Properties translations = storage.translations;

        for (MicroblockItem microblockItem : microblockItems) {
            String translation = I18n.getTranslation(microblockItem.getTypeTranslationKey(), microblockItem.block.getTranslatedName());
            translations.put(microblockItem.getTranslationKey() + ".name", translation);
        }
    }
}
