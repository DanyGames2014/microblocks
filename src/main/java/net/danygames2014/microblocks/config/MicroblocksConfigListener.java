package net.danygames2014.microblocks.config;

import net.glasslauncher.mods.alwaysmoreitems.util.AlwaysMoreItems;
import net.glasslauncher.mods.gcapi3.api.PreConfigSavedListener;
import net.glasslauncher.mods.gcapi3.impl.EventStorage;
import net.glasslauncher.mods.gcapi3.impl.GlassYamlFile;

public class MicroblocksConfigListener implements PreConfigSavedListener {
    @Override
    public void onPreConfigSaved(int source, GlassYamlFile oldValues, GlassYamlFile newValues) {
        if (EventStorage.EventSource.containsOne(source, EventStorage.EventSource.USER_SAVE, EventStorage.EventSource.MOD_SAVE)) {
            if (oldValues.contains("hideMicroblocksInAmi")) {
                if (oldValues.getBoolean("hideMicroblocksInAmi") != newValues.getBoolean("hideMicroblocksInAmi")) {
                    AlwaysMoreItems.reloadBlacklist();       
                }
            }
        }
    }
}
