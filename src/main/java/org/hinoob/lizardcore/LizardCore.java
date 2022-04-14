package org.hinoob.lizardcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hinoob.lizardcore.listener.SpawnListener;
import org.hinoob.lizardcore.listener.VanishListener;
import org.hinoob.lizardcore.manager.*;
import org.hinoob.lizardcore.user.User;
import org.hinoob.lizardcore.util.ReloadUtil;
import org.hinoob.lizardcore.util.VanishUtil;

import java.io.File;

public class LizardCore extends JavaPlugin {

    @Getter private static LizardCore instance;

    @Getter private DatabaseManager databaseManager;
    @Getter private CommandManager commandManager;
    @Getter private UserManager userManager;
    @Getter private SpawnManager spawnManager;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();

        if(!Bukkit.getOnlineMode()){
            if(!YamlConfiguration.loadConfiguration(new File(Bukkit.getWorldContainer(), "spigot.yml")).getBoolean("settings.bungeecord")){
                getLogger().info("[ERROR] LizardCore is not compatible with offline mode! (Disabled plugin)");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }

        // load the managers
        databaseManager = new DatabaseManager(this);
        if(!databaseManager.load()){
            getLogger().info("[ERROR] DatabaseManager failed to load! (Disabled plugin)");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        userManager = new UserManager(this);
        userManager.load(); // reload support smh

        commandManager = new CommandManager(this);
        commandManager.load();

        spawnManager = new SpawnManager(this);
        spawnManager.load();

        // register events
        registerListeners();

        // reload support smh
        if(!Bukkit.getOnlinePlayers().isEmpty()) ReloadUtil.onReloadFinished(this);

        getLogger().info("LizardCore has been enabled!");
    }

    @Override
    public void onDisable(){
        // to avoid any issues, we're going to make vanished players reappear, BUT NOT UNVANISH THEM
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = userManager.getUser(player.getUniqueId());

            if(user.isVanished()){
                VanishUtil.toggleVanish(player, false);
            }
        }

        getLogger().info("LizardCore has been disabled!");
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
        getServer().getPluginManager().registerEvents(new VanishListener(), this);
    }

    public void log(String message){
        getLogger().info(message);
    }
}
