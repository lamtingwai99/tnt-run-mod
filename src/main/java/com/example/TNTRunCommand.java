package com.example;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class TNTRunCommand {
    public static boolean isGameActive = false;

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("tntrun")
                .then(CommandManager.literal("start")
                    .executes(context -> {
                        isGameActive = true;
                        context.getSource().sendMessage(Text.literal("TNT Run 遊戲已開始！"));
                        return 1;
                    }))
                .then(CommandManager.literal("stop")
                    .executes(context -> {
                        isGameActive = false;
                        context.getSource().sendMessage(Text.literal("TNT Run 遊戲已結束！"));
                        return 1;
                    }))
            );
        });
    }
}
