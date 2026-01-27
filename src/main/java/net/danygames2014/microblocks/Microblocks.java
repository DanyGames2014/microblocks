package net.danygames2014.microblocks;

import net.danygames2014.nyalib.event.MultipartComponentRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class Microblocks {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    @EventListener
    public void registerMultiparts(MultipartComponentRegistryEvent event){

    }
}
