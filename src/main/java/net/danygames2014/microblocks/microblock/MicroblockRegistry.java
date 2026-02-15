package net.danygames2014.microblocks.microblock;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.danygames2014.microblocks.Microblocks;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.minecraft.block.Block;

public class MicroblockRegistry {
    public final Object2ObjectOpenHashMap<Block, int[]> registry = new Object2ObjectOpenHashMap<>(); // VALID_META_VALUES_FOR_BLOCK
    public final Object2ObjectOpenHashMap<MicroblockItemType, Block> MICROBLOCKS_OF_TYPE = new Object2ObjectOpenHashMap<>(); 
    
    private static MicroblockRegistry INSTANCE;
    
    public static MicroblockRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MicroblockRegistry();
        }
        
        return INSTANCE;
    }
    
    public static boolean register(Block block) {
        return register(block, 0);
    }
    
    public static boolean register(Block block, int... meta) {
        var r = getInstance().registry;
        
        if (r.containsKey(block)) {
            Microblocks.LOGGER.warn("Microblock for block {} already registered!", block.id);
        }
        
        getInstance().registry.put(block, meta);
        return true;
    }
    
    public static int[] getMeta(Block block) {
        var r = getInstance().registry;
        
        if (r.containsKey(block)) {
            return r.get(block);
        }
        
        return null;
    }

    //    public static class Entry {
//        public Block block;
//        public int[] meta;
//
//        public Entry(Block block, int... meta) {
//            this.block = block;
//            this.meta = meta;
//        }
//
//        public Entry(Block block) {
//            this(block, 0);
//        }
//    }
}
