package org.hinoob.lizardcore.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.user.User;

public class ReloadUtil {

    public static void onReloadFinished(LizardCore core){
        core.getLogger().info("Detected a reload!");

        // re-vanish the players
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = core.getUserManager().getUser(player.getUniqueId());

            if(user != null && user.isVanished()){
                VanishUtil.toggleVanish(player, true);
            }
        }
    }
}
