package net.danygames2014.microblocks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.CornerMicroblockItem;
import net.danygames2014.microblocks.item.EdgeMicroblockItem;
import net.danygames2014.microblocks.item.FaceMicroblockItem;
import net.danygames2014.microblocks.item.MicroblockItem;
import net.danygames2014.microblocks.multipart.CornerMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.EdgeMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.FaceMicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PostMicroblockMultipartComponent;
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
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Microblocks {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    public static Item faceMicroblock;
    public static Item cornerMicroblock;
    public static Item edgeMicroblock;

    public static ObjectArrayList<MicroblockItem> microblockItems = new ObjectArrayList<>();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        for (Block block : BlockRegistry.INSTANCE.stream().toList()) {
            Identifier identifier = BlockRegistry.INSTANCE.getId(block);
            
            if (identifier != null && block.getTexture(0) != 0) {
                String coverId = identifier.namespace + "_" + identifier.path + "_cover";
                FaceMicroblockItem coverMicroblockItem = (FaceMicroblockItem) new FaceMicroblockItem(NAMESPACE.id(coverId), block).setTranslationKey(NAMESPACE, coverId);

                String cornerId = identifier.namespace + "_" + identifier.path + "_cover";
                FaceMicroblockItem cornerMicroblockItem = (FaceMicroblockItem) new FaceMicroblockItem(NAMESPACE.id(cornerId), block).setTranslationKey(NAMESPACE, cornerId);
            }
        }
        
        faceMicroblock = new FaceMicroblockItem(Identifier.of("face_microblock"), Block.STONE).setTranslationKey(NAMESPACE, "face_microblock");
        cornerMicroblock = new CornerMicroblockItem(Identifier.of("corner_microblock"), Block.STONE).setTranslationKey(NAMESPACE, "corner_microblock");
        edgeMicroblock = new EdgeMicroblockItem(Identifier.of("edge_microblock"), Block.STONE).setTranslationKey(NAMESPACE, "edge_microblock");

        microblockItems.add((MicroblockItem) faceMicroblock);
        microblockItems.add((MicroblockItem) cornerMicroblock);
        microblockItems.add((MicroblockItem) edgeMicroblock);
    }

    @EventListener
    public void registerMultiparts(MultipartComponentRegistryEvent event) {
        event.register(NAMESPACE.id("face_microblock_component"), FaceMicroblockMultipartComponent.class, FaceMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("corner_microblock_component"), CornerMicroblockMultipartComponent.class, CornerMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("edge_microblock_component"), EdgeMicroblockMultipartComponent.class, EdgeMicroblockMultipartComponent::new);
        event.register(NAMESPACE.id("post_microblock_component"), PostMicroblockMultipartComponent.class, PostMicroblockMultipartComponent::new);
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
