package com.example;

import net.fabricmc.api.ModInitializer;

public class TNTRunMod implements ModInitializer {
    @Override
    public void onInitialize() {
        TNTRunCommand.register();
        TNTRunGameLoop.register();
    }
}
