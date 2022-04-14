package org.hinoob.lizardcore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.hinoob.lizardcore.LizardCore;

public class SpawnListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(LizardCore.getInstance().getSpawnManager().getSpawnLocation() != null &&
                LizardCore.getInstance().getSpawnManager().getActiveTriggers().stream().anyMatch(string -> string.equalsIgnoreCase("on-join"))){
            event.getPlayer().teleport(LizardCore.getInstance().getSpawnManager().getSpawnLocation());
        }
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent event){
        if(LizardCore.getInstance().getSpawnManager().getSpawnLocation() != null &&
                LizardCore.getInstance().getSpawnManager().getActiveTriggers().stream().anyMatch(string -> string.equalsIgnoreCase("on-respawn"))){
            event.setRespawnLocation(LizardCore.getInstance().getSpawnManager().getSpawnLocation());
        }
    }
}
