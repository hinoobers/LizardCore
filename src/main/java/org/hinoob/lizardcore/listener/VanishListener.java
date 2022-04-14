package org.hinoob.lizardcore.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.manager.UserManager;
import org.hinoob.lizardcore.user.User;
import org.hinoob.lizardcore.util.VanishUtil;

public class VanishListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        User user = LizardCore.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());
        if(user.isVanished()) {
            if(LizardCore.getInstance().getConfig().getBoolean("vanish.hide-join-and-leave-messages")){
                event.setJoinMessage("");
            }

            VanishUtil.toggleVanish(event.getPlayer(), true); // hide the player from everyone
        }else{
            if(event.getPlayer().hasPermission("lizardcore.command.vanish") && LizardCore.getInstance().getConfig().getBoolean("vanish.autovanish")){
                user.setVanished(true);

                if(LizardCore.getInstance().getConfig().getBoolean("vanish.hide-join-and-leave-messages")){
                    event.setJoinMessage("");
                }

                VanishUtil.toggleVanish(event.getPlayer(), true); // hide the player from everyone

                event.getPlayer().sendMessage(ChatColor.YELLOW + "You" + ChatColor.GREEN + " have been auto-vanished!");
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        User user = LizardCore.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());
        if(user.isVanished()) {
            if(LizardCore.getInstance().getConfig().getBoolean("vanish.hide-join-and-leave-messages")){
                event.setQuitMessage("");
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event){
        if(!LizardCore.getInstance().getConfig().getBoolean("vanish.anti-target")) return;

        if(event.getTarget() instanceof Player){
            User user = LizardCore.getInstance().getUserManager().getUser(event.getTarget().getUniqueId());
            if(user.isVanished()) event.setCancelled(true); // so mobs won't target the vanished player
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(!LizardCore.getInstance().getConfig().getBoolean("vanish.anti-chat")) return;

        User user = LizardCore.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());
        if(user.isVanished()){
            if(!event.getMessage().startsWith("@")){
                event.getPlayer().sendMessage(ChatColor.RED + "Please make sure your message starts with @, to talk!");
                event.setCancelled(true);
            }else{
                event.setMessage(event.getMessage().substring(1));
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(!LizardCore.getInstance().getConfig().getBoolean("vanish.hide-death-messages")) return;

        User user = LizardCore.getInstance().getUserManager().getUser(event.getEntity().getUniqueId());
        if(user.isVanished()){
            event.setDeathMessage("");
        }
    }

}
