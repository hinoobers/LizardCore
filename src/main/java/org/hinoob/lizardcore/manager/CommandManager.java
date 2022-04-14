package org.hinoob.lizardcore.manager;

import co.aikar.commands.PaperCommandManager;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.command.*;
import org.hinoob.lizardcore.mysql.ConnectionCredentials;
import org.hinoob.lizardcore.mysql.DriverType;
import org.hinoob.lizardcore.mysql.MySQL;

import java.io.File;

public class CommandManager {

    private final LizardCore core;

    public CommandManager(LizardCore core){
        this.core = core;
    }

    public void load(){
        PaperCommandManager commandManager = new PaperCommandManager(core);

        commandManager.registerCommand(new GamemodeCommand());
        commandManager.registerCommand(new WorldCommand());
        commandManager.registerCommand(new VanishCommand());
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new DemoTrollCommand());
    }
}
