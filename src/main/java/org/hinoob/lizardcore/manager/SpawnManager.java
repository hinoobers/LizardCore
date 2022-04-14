package org.hinoob.lizardcore.manager;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.command.GamemodeCommand;

import java.util.ArrayList;
import java.util.List;

public class SpawnManager {

    private final LizardCore core;

    @Getter private Location spawnLocation;

    public SpawnManager(LizardCore core){
        this.core = core;
    }

    public void load(){
        if(core.getConfig().getBoolean("spawn.set")){
            String worldName = core.getConfig().getString("spawn.location.world");
            if(worldName == null) return;

            if(Bukkit.getWorld(worldName) == null){
                core.getLogger().info("[WARNING] Spawn world not found, creating...");
                Bukkit.createWorld(new WorldCreator(worldName));
            }

            double x = core.getConfig().getDouble("spawn.location.x");
            double y = core.getConfig().getDouble("spawn.location.y");
            double z = core.getConfig().getDouble("spawn.location.z");

            float yaw = (float) core.getConfig().getDouble("spawn.location.yaw");
            float pitch = (float) core.getConfig().getDouble("spawn.location.pitch");

            this.spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
    }

    public void setSpawn(Location location){
        spawnLocation = location;

        core.getConfig().set("spawn.set", true);
        core.getConfig().set("spawn.location.world", location.getWorld().getName());
        core.getConfig().set("spawn.location.x", location.getX());
        core.getConfig().set("spawn.location.y", location.getY());
        core.getConfig().set("spawn.location.z", location.getZ());
        core.getConfig().set("spawn.location.yaw", location.getYaw());
        core.getConfig().set("spawn.location.pitch", location.getPitch());
        core.saveConfig();
    }
    public List<String> getActiveTriggers(){
        List<String> keys = new ArrayList<>();

        for(String key : core.getConfig().getConfigurationSection("spawn.triggers").getKeys(false)){
            if(core.getConfig().getBoolean("spawn.triggers." + key)){
                keys.add(key);
            }
        }

        return keys;
    }
}
