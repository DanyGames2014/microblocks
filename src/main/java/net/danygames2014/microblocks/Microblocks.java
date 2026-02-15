package net.danygames2014.microblocks;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.danygames2014.microblocks.config.MicroblocksConfig;
import net.danygames2014.microblocks.event.MicroblockRegistryEvent;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.item.base.HandSawItem;
import net.danygames2014.microblocks.item.base.MicroblockItem;
import net.danygames2014.microblocks.microblock.MicroblockRegistry;
import net.danygames2014.microblocks.multipart.*;
import net.danygames2014.microblocks.recipe.MicroblockRecipeIngredient;
import net.danygames2014.microblocks.recipe.MicroblockRecipeManager;
import net.danygames2014.nyalib.event.MultipartComponentRegistryEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
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

    @ConfigRoot(value = "microblocks", visibleName = "Microblocks")
    public static final MicroblocksConfig CONFIG = new MicroblocksConfig();
    
    public static Item ironHandsaw;
    public static Item diamondHandsaw;

    public static Object2ObjectOpenHashMap<MicroblockItemType, Object2ObjectOpenHashMap<Block, Int2ObjectOpenHashMap<MicroblockItem>>> microblockItems = new Object2ObjectOpenHashMap<>();
    
    public static MicroblockRecipeManager recipeManager;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        ironHandsaw = new HandSawItem(NAMESPACE.id("iron_handsaw"), 320).setTranslationKey(NAMESPACE, "iron_handsaw");
        diamondHandsaw = new HandSawItem(NAMESPACE.id("diamond_handsaw"), 1920).setTranslationKey(NAMESPACE, "diamond_handsaw");

        // Let mods register their own blocks to get the micro treatment
        StationAPI.EVENT_BUS.post(new MicroblockRegistryEvent(MicroblockRegistry.getInstance()));

//        TagKey<Block> tag = TagKey.of(BlockRegistry.INSTANCE.getKey(), NAMESPACE.id("can_be_microblock"));
//        for (Block block : BlockRegistry.INSTANCE) {
//            RegistryEntry<Block> entry = BlockRegistry.INSTANCE.getEntry(block);
//            if (entry.isIn(tag)) {
//                MicroblockRegistry.register(block, 0);
//            }
//        }

        // Init the HashMaps for all microblock item types
        for (MicroblockItemType type : MicroblockItemType.values()) {
            microblockItems.put(type, new Object2ObjectOpenHashMap<>());
        }

        // Could use Block.isFullBlock()
        // Register all of the microblock items
        for (Map.Entry<Block, int[]> entry : MicroblockRegistry.getInstance().registry.entrySet()) {
            Block block = entry.getKey();
            Identifier identifier = BlockRegistry.INSTANCE.getId(block);

            if (identifier != null) {
                for (int meta : entry.getValue()) {
                    for (MicroblockItemType type : MicroblockItemType.values()) {
                        String id = type.constructIdentifier(identifier, meta);

                        microblockItems.get(type).computeIfAbsent(block, k -> new Int2ObjectOpenHashMap<>());
                        microblockItems.get(type).get(block).put(meta, (MicroblockItem) type.factory.create(NAMESPACE.id(id), block, meta).setTranslationKey(NAMESPACE, id));
                    }
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
    public void registerRecipes(RecipeRegisterEvent event) {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {
            recipeManager = new MicroblockRecipeManager();
            
            // Cutting
            // Slab -> Panel -> Cover
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 2,"S", "B");
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 2,"S", "L", 'L', MicroblockItemType.SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.COVER, 2,"S", "P", 'P', MicroblockItemType.PANEL);
            
            // Hollow Slab -> Hollow Panel -> Hollow Cover
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_PANEL, 2,"S", "L", 'L', MicroblockItemType.HOLLOW_SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_COVER, 2,"S", "P", 'P', MicroblockItemType.HOLLOW_PANEL);
            
            // Slab -> Slab Strip -> Slab Corner
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB_STRIP, 2,"SL", 'L', MicroblockItemType.SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB_CORNER, 2,"SL", 'L', MicroblockItemType.SLAB_STRIP);
            
            // Panel -> Panel Strip -> Panel Corner
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_STRIP, 2,"SP", 'P', MicroblockItemType.PANEL);
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_CORNER, 2,"SP", 'P', MicroblockItemType.PANEL_STRIP);

            recipeManager.addRecipe(MicroblockRecipeIngredient.STRIP, 2,"SC", 'C', MicroblockItemType.COVER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.CORNER, 2,"SC", 'C', MicroblockItemType.STRIP);
            
            // Slab Strip -> 2x Panel Strip
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_STRIP, 2,"S", "L", 'L', MicroblockItemType.SLAB_STRIP);
            
            // Panel Strip -> 2x Cover Strip
            recipeManager.addRecipe(MicroblockRecipeIngredient.STRIP, 2,"S", "P", 'P', MicroblockItemType.PANEL_STRIP);
            
            // Slab Corner -> 2x Panel Corner
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_CORNER, 2,"S", "L", 'L', MicroblockItemType.SLAB_CORNER);
            
            // Panel Corner -> 2x Cover Corner
            recipeManager.addRecipe(MicroblockRecipeIngredient.CORNER, 2,"S", "P", 'P', MicroblockItemType.PANEL_CORNER);
            
            // Combining
            // 2x Slab -> Full Block
            recipeManager.addRecipe(MicroblockRecipeIngredient.BLOCK, 1,"SS", 'S', MicroblockItemType.SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.BLOCK, 1,"S", "S", 'S', MicroblockItemType.SLAB);
            
            // 2x Slab Corner -> Slab Strip
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB_STRIP, 1,"CC", 'C', MicroblockItemType.SLAB_CORNER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB_STRIP, 1,"C", "C", 'C', MicroblockItemType.SLAB_CORNER);

            // 2x Slab Strip -> Slab
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 1,"LL", 'L', MicroblockItemType.SLAB_STRIP);
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 1,"L", "L", 'L', MicroblockItemType.SLAB_STRIP);

            // 2x Panel Corner -> Panel Strip
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_STRIP, 1,"CC", 'C', MicroblockItemType.PANEL_CORNER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL_STRIP, 1,"C", "C", 'C', MicroblockItemType.PANEL_CORNER);
            
            // 2x Panel Strip -> Panel
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 1,"SS", 'S', MicroblockItemType.PANEL_STRIP);
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 1,"S", "S", 'S', MicroblockItemType.PANEL_STRIP);
            
            // 2x Corner -> Strip
            recipeManager.addRecipe(MicroblockRecipeIngredient.STRIP, 1,"CC", 'C', MicroblockItemType.CORNER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.STRIP, 1,"C", "C", 'C', MicroblockItemType.CORNER);
            
            // 2x Strip -> Cover
            recipeManager.addRecipe(MicroblockRecipeIngredient.COVER, 1,"SS", 'S', MicroblockItemType.STRIP);
            recipeManager.addRecipe(MicroblockRecipeIngredient.COVER, 1,"S", "S", 'S', MicroblockItemType.STRIP);
            
            // 2x Panel -> Slab
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 1,"PP", 'P', MicroblockItemType.PANEL);
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 1,"P", "P", 'P', MicroblockItemType.PANEL);
            
            // 2x Cover -> Panel
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 1,"CC", 'C', MicroblockItemType.COVER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 1,"C", "C", 'C', MicroblockItemType.COVER);
            
            // Hollowing
            // 8x Slab -> 8x Hollow Slab
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_SLAB, 8,"LLL", "L L", "LLL", 'L', MicroblockItemType.SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_SLAB, 1, "L", 'L', MicroblockItemType.SLAB);
            recipeManager.addRecipe(MicroblockRecipeIngredient.SLAB, 1, "L", 'L', MicroblockItemType.HOLLOW_SLAB);
            
            // 8x Panel -> 8x Hollow Panel
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_PANEL, 8,"PPP", "P P", "PPP", 'P', MicroblockItemType.PANEL);
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_PANEL, 1, "P", 'P', MicroblockItemType.PANEL);
            recipeManager.addRecipe(MicroblockRecipeIngredient.PANEL, 1, "P", 'P', MicroblockItemType.HOLLOW_PANEL);
            
            // 8x Cover -> 8x Hollow Cover
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_COVER, 8,"CCC", "C C", "CCC", 'C', MicroblockItemType.COVER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.HOLLOW_COVER, 1, "C", 'C', MicroblockItemType.COVER);
            recipeManager.addRecipe(MicroblockRecipeIngredient.COVER, 1, "C", 'C', MicroblockItemType.HOLLOW_COVER);
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

        for (var microblockItemsOfType : microblockItems.values()) {
            for (var microblockItems : microblockItemsOfType.values()) {
                for (Int2ObjectMap.Entry<MicroblockItem> microblockItem : microblockItems.int2ObjectEntrySet()) {
                    ItemStack stack = new ItemStack(microblockItem.getValue().block, 1, microblockItem.getIntKey());
                    String translation = I18n.getTranslation(microblockItem.getValue().getTypeTranslationKey(), I18n.getTranslation(stack.getItem().getTranslationKey(stack) + ".name"));
                    translations.put(microblockItem.getValue().getTranslationKey() + ".name", translation);
                }
            }
        }
    }

    @EventListener(phase = InitEvent.PRE_INIT_PHASE)
    public void preInit(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("microblocks:event_bus", Object.class).forEach(EntrypointManager::setup);
    }
}
