package net.danygames2014.microblocks;

import net.danygames2014.microblocks.item.CornerMicroblockItem;
import net.danygames2014.microblocks.item.EdgeMicroblockItem;
import net.danygames2014.microblocks.item.FaceMicroblockItem;
import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.EdgeMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.FaceMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PostMicroblockMultipartComponent;
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

    public static Item faceMicroblock;
    public static Item cornerMicroblock;
    public static Item edgeMicroblock;

    @EventListener
    public void registerItems(ItemRegistryEvent event){
        faceMicroblock = new FaceMicroblockItem(Identifier.of("face_microblock")).setTranslationKey(NAMESPACE, "face_microblock");
        cornerMicroblock = new CornerMicroblockItem(Identifier.of("corner_microblock")).setTranslationKey(NAMESPACE, "corner_microblock");
        edgeMicroblock = new EdgeMicroblockItem(Identifier.of("edge_microblock")).setTranslationKey(NAMESPACE, "edge_microblock");
    }

    @EventListener
    public void registerMultiparts(MultipartComponentRegistryEvent event){
        event.register(NAMESPACE.id("face_microblock_component"), FaceMicroblockMultipartComponent.class, FaceMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("corner_microblock_component"), CornerMicroblockMultipartComponent.class, CornerMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("edge_microblock_component"), EdgeMicroblockMultipartComponent.class, EdgeMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("post_microblock_component"), PostMicroblockMultipartComponent.class, PostMicroblockMultipartComponent::new);
    }
}
