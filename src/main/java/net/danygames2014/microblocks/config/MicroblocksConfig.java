package net.danygames2014.microblocks.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class MicroblocksConfig {
    @ConfigEntry(name = "Hide Microblocks in AMI", description = "Hides every microblock except for stone ones")
    public Boolean hideMicroblocksInAmi = false;
}
