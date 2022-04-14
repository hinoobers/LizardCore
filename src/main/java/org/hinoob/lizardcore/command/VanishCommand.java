package org.hinoob.lizardcore.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.user.User;
import org.hinoob.lizardcore.util.CommandUtil;
import org.hinoob.lizardcore.util.VanishUtil;

public class VanishCommand extends BaseCommand {

    @Default
    @CommandAlias("vanish|v")
    @CommandPermission("lizardcore.command.vanish")
    public void vanish(CommandSender sender, String[] args){
        if(sender instanceof Player){
            if(args.length != 1){
                User user = LizardCore.getInstance().getUserManager().getUser(((Player) sender).getUniqueId());

                user.setVanished(!user.isVanished());
                if(user.isVanished()){
                    sender.sendMessage(ChatColor.YELLOW + "You" + ChatColor.GREEN + " are now vanished!");
                    VanishUtil.toggleVanish(user.getPlayer(), true);

                    if(LizardCore.getInstance().getConfig().getBoolean("vanish.fake-leave-message.enabled")){
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', LizardCore.getInstance().getConfig().getString("vanish.fake-leave-message.message")
                                    .replace("%player%", sender.getName())));
                        });
                    }
                }else{
                    sender.sendMessage(ChatColor.YELLOW + "You" + ChatColor.GREEN + " are no longer vanished!");
                    VanishUtil.toggleVanish(user.getPlayer(), false);
                }
            }else{
                String playerName = args[0];

                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                Player player = Bukkit.getPlayer(playerName);

                if(player == null || !offlinePlayer.hasPlayedBefore()){
                    CommandUtil.sendMessage(sender, ChatColor.RED + "Player not found!");
                }else{
                    User user = LizardCore.getInstance().getUserManager().getUser(offlinePlayer.getUniqueId());
                    if(user == null) user = new User(offlinePlayer.getUniqueId()); // temporarily load the player just in case they aren't loaded yet

                    user.setVanished(!user.isVanished());
                    if(user.isVanished()){
                        CommandUtil.sendMessage(sender, ChatColor.YELLOW + offlinePlayer.getName() + ChatColor.GREEN + " is now vanished!");
                        if(user.getPlayer() != null){
                            if(LizardCore.getInstance().getConfig().getBoolean("vanish.fake-leave-message.enabled")){
                                Bukkit.getOnlinePlayers().forEach(otherPlayer -> otherPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LizardCore.getInstance().getConfig().getString("vanish.fake-leave-message.message")
                                        .replace("%player%", offlinePlayer.getName()))));
                            }

                            VanishUtil.toggleVanish(offlinePlayer.getPlayer(), true);
                        }
                    }else{
                        CommandUtil.sendMessage(sender, ChatColor.YELLOW + offlinePlayer.getName() + ChatColor.GREEN + " is no longer vanished!");
                        if(user.getPlayer() != null){
                            VanishUtil.toggleVanish(offlinePlayer.getPlayer(), false);
                        }
                    }
                }
            }
        }else{
            if(args.length != 1){
                CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
            }else{
                String playerName = args[0];

                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                Player player = Bukkit.getPlayer(playerName);

                if(player == null || !offlinePlayer.hasPlayedBefore()){
                    CommandUtil.sendMessage(sender, ChatColor.RED + "Player not found!");
                }else{
                    User user = LizardCore.getInstance().getUserManager().getUser(offlinePlayer.getUniqueId());
                    if(user == null) user = new User(offlinePlayer.getUniqueId()); // temporarily load the player just in case they aren't loaded yet

                    user.setVanished(!user.isVanished());
                    if(user.isVanished()){
                        CommandUtil.sendMessage(sender, ChatColor.YELLOW + offlinePlayer.getName() + ChatColor.GREEN + " is now vanished!");
                        if(user.getPlayer() != null){
                            if(LizardCore.getInstance().getConfig().getBoolean("vanish.fake-leave-message.enabled")){
                                Bukkit.getOnlinePlayers().forEach(otherPlayer -> otherPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LizardCore.getInstance().getConfig().getString("vanish.fake-leave-message.message")
                                        .replace("%player%", offlinePlayer.getName()))));
                            }

                            VanishUtil.toggleVanish(offlinePlayer.getPlayer(), true);
                        }
                    }else{
                        CommandUtil.sendMessage(sender, ChatColor.YELLOW + offlinePlayer.getName() + ChatColor.GREEN + " is no longer vanished!");
                        if(user.getPlayer() != null){
                            VanishUtil.toggleVanish(offlinePlayer.getPlayer(), false);
                        }
                    }
                }
            }
        }
    }


}
