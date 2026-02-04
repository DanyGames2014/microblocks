package net.danygames2014.microblocks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.event.MicroblockRegistryEvent;
import net.danygames2014.microblocks.item.*;
import net.danygames2014.microblocks.item.base.HandSawItem;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.microblock.MicroblockRegistry;
import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.nyalib.event.MultipartComponentRegistryEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.resource.language.TranslationInvalidationEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.util.Map;
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

        StationAPI.EVENT_BUS.post(new MicroblockRegistryEvent(MicroblockRegistry.getInstance()));

//        TagKey<Block> tag = TagKey.of(BlockRegistry.INSTANCE.getKey(), NAMESPACE.id("can_be_microblock"));
//        for (Block block : BlockRegistry.INSTANCE) {
//            RegistryEntry<Block> entry = BlockRegistry.INSTANCE.getEntry(block);
//            if (entry.isIn(tag)) {
//                MicroblockRegistry.register(block, 0);
//            }
//        }
        
        for (Map.Entry<Block, int[]> entry : MicroblockRegistry.getInstance().registry.entrySet()) {
            Block block = entry.getKey();
            Identifier identifier = BlockRegistry.INSTANCE.getId(block);

            if (identifier != null) {
                for (int meta : entry.getValue()) {
                    String coverId = identifier.namespace + "_" + identifier.path + "_cover_" + meta;
                    microblockItems.add((MicroblockItem) new CoverMicroblockItem(NAMESPACE.id(coverId), block, meta).setTranslationKey(NAMESPACE, coverId));

                    String panelId = identifier.namespace + "_" + identifier.path + "_panel_" + meta;
                    microblockItems.add((MicroblockItem) new PanelMicroblockItem(NAMESPACE.id(panelId), block, meta).setTranslationKey(NAMESPACE, panelId));

                    String slabId = identifier.namespace + "_" + identifier.path + "_slab_" + meta;
                    microblockItems.add((MicroblockItem) new SlabMicroblockItem(NAMESPACE.id(slabId), block, meta).setTranslationKey(NAMESPACE, slabId));

                    String hollowCoverId = identifier.namespace + "_" + identifier.path + "_hollow_cover_" + meta;
                    microblockItems.add((MicroblockItem) new HollowCoverMicroblockItem(NAMESPACE.id(hollowCoverId), block, meta).setTranslationKey(NAMESPACE, hollowCoverId));

                    String hollowPanelId = identifier.namespace + "_" + identifier.path + "_hollow_panel_" + meta;
                    microblockItems.add((MicroblockItem) new HollowPanelMicroblockItem(NAMESPACE.id(hollowPanelId), block, meta).setTranslationKey(NAMESPACE, hollowPanelId));

                    String hollowSlabId = identifier.namespace + "_" + identifier.path + "_hollow_slab_" + meta;
                    microblockItems.add((MicroblockItem) new HollowSlabMicroblockItem(NAMESPACE.id(hollowSlabId), block, meta).setTranslationKey(NAMESPACE, hollowSlabId));

                    String stripCornerId = identifier.namespace + "_" + identifier.path + "_corner_" + meta;
                    microblockItems.add((MicroblockItem) new StripCornerMicroblockItem(NAMESPACE.id(stripCornerId), block, meta).setTranslationKey(NAMESPACE, stripCornerId));

                    String panelCornerId = identifier.namespace + "_" + identifier.path + "_panel_corner_" + meta;
                    microblockItems.add((MicroblockItem) new PanelCornerMicroblockItem(NAMESPACE.id(panelCornerId), block, meta).setTranslationKey(NAMESPACE, panelCornerId));

                    String slabCornerId = identifier.namespace + "_" + identifier.path + "_slab_corner_" + meta;
                    microblockItems.add((MicroblockItem) new SlabCornerMicroblockItem(NAMESPACE.id(slabCornerId), block, meta).setTranslationKey(NAMESPACE, slabCornerId));

                    String stripId = identifier.namespace + "_" + identifier.path + "_strip_" + meta;
                    microblockItems.add((MicroblockItem) new StripMicroblockItem(NAMESPACE.id(stripId), block, meta).setTranslationKey(NAMESPACE, stripId));

                    String panelStripId = identifier.namespace + "_" + identifier.path + "_panel_strip_" + meta;
                    microblockItems.add((MicroblockItem) new PanelStripMicroblockItem(NAMESPACE.id(panelStripId), block, meta).setTranslationKey(NAMESPACE, panelStripId));

                    String slabStripId = identifier.namespace + "_" + identifier.path + "_slab_strip_" + meta;
                    microblockItems.add((MicroblockItem) new SlabStripMicroblockItem(NAMESPACE.id(slabStripId), block, meta).setTranslationKey(NAMESPACE, slabStripId));
                }
            }
        }
    }

    @EventListener
    public void registerVanillaMicroblocks(MicroblockRegistryEvent event) {
        event.register(Block.STONE);
        event.register(Block.GRASS_BLOCK);
        event.register(Block.DIRT);
        event.register(Block.COBBLESTONE);
        event.register(Block.PLANKS);
        //event.register(Block.BEDROCK);
        //event.register(Block.FLOWING_WATER);
        //event.register(Block.WATER);
        //event.register(Block.FLOWING_LAVA);
        //event.register(Block.LAVA);
        event.register(Block.SAND);
        event.register(Block.GRAVEL);
        event.register(Block.GOLD_ORE);
        event.register(Block.IRON_ORE);
        event.register(Block.COAL_ORE);
        event.register(Block.LOG, 0, 1, 2);
        event.register(Block.LEAVES, 0, 1, 2);
        event.register(Block.SPONGE);
        event.register(Block.GLASS);
        event.register(Block.LAPIS_ORE);
        event.register(Block.LAPIS_BLOCK);
        event.register(Block.DISPENSER);
        event.register(Block.SANDSTONE);
        event.register(Block.NOTE_BLOCK);
        event.register(Block.STICKY_PISTON);
        event.register(Block.PISTON);
        event.register(Block.WOOL, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        event.register(Block.GOLD_BLOCK);
        event.register(Block.IRON_BLOCK);
        event.register(Block.SLAB);
        event.register(Block.BRICKS);
        event.register(Block.TNT);
        event.register(Block.BOOKSHELF);
        event.register(Block.MOSSY_COBBLESTONE);
        event.register(Block.OBSIDIAN);
        //event.register(Block.SPAWNER);
        event.register(Block.DIAMOND_ORE);
        event.register(Block.DIAMOND_BLOCK);
        event.register(Block.CRAFTING_TABLE);
        event.register(Block.FURNACE);
        event.register(Block.REDSTONE_ORE);
        event.register(Block.ICE);
        event.register(Block.SNOW_BLOCK);
        event.register(Block.CLAY);
        event.register(Block.JUKEBOX);
        event.register(Block.PUMPKIN);
        event.register(Block.NETHERRACK);
        event.register(Block.SOUL_SAND);
        event.register(Block.GLOWSTONE);
        event.register(Block.JACK_O_LANTERN);
        event.register(Block.TRAPDOOR);
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

    @EventListener(phase = InitEvent.PRE_INIT_PHASE)
    public void preInit(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("microblocks:event_bus", Object.class).forEach(EntrypointManager::setup);
    }
}
