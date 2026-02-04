package net.danygames2014.microblocks.event;

import net.danygames2014.microblocks.microblock.MicroblockRegistry;
import net.mine_diver.unsafeevents.Event;
import net.minecraft.block.Block;

public class MicroblockRegistryEvent extends Event {
    public MicroblockRegistry registry;
    
    public MicroblockRegistryEvent(MicroblockRegistry registry) {
        this.registry = registry;
    }

    public boolean register(Block block, int... meta) {
        return MicroblockRegistry.register(block, meta);
    }
    
    public boolean register(Block block) {
        return MicroblockRegistry.register(block);
    }
}
