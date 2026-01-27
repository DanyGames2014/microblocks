package net.danygames2014.microblocks;

import net.danygames2014.microblocks.item.CoverMicroblockItem;
import net.danygames2014.microblocks.multipart.CoverMicroblockMultipartComponent;
import net.danygames2014.nyalib.event.MultipartComponentRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class Microblocks {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    public static Item coverMicroblock;

    @EventListener
    public void registerItems(ItemRegistryEvent event){
        coverMicroblock = new CoverMicroblockItem(Identifier.of("cover_microblock")).setTranslationKey(NAMESPACE, "cover_microblock");
    }

    @EventListener
    public void registerMultiparts(MultipartComponentRegistryEvent event){
        event.register(NAMESPACE.id("cover_microblock_component"), CoverMicroblockMultipartComponent.class, CoverMicroblockMultipartComponent::new);
    }
}
