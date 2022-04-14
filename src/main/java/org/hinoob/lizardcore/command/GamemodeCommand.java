package org.hinoob.lizardcore.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.util.CommandUtil;

@CommandAlias("gm|gamemode")
@CommandPermission("lizardcore.command.gamemode")
public class GamemodeCommand extends BaseCommand {

    @Default
    @HelpCommand
    @Subcommand("help")
    public void help(CommandSender sender){
        if(sender instanceof Player){
            CommandUtil.sendMessage(sender, ChatColor.RED + "Usage: /gm <creative|survival|adventure> [player]");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "Usage: /gm <creative|survival|adventure> <player>");
        }
    }

    @Subcommand("c|creative|1")
    @CommandPermission("lizardcore.command.gamemode.creative")
    public void creative(CommandSender sender, @Optional OnlinePlayer target){
        Player targetPlayer;
        if(target == null){
            if(sender instanceof Player){
                targetPlayer = (Player) sender;
            }else{
                CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
                return;
            }
        }else{
            targetPlayer = target.getPlayer();
        }

        targetPlayer.setGameMode(GameMode.CREATIVE);
        sendUpdateMessage(sender, targetPlayer, GameMode.CREATIVE);
    }

    @Subcommand("s|survival|0")
    @CommandPermission("lizardcore.command.gamemode.survival")
    public void survival(CommandSender sender, @Optional OnlinePlayer target){
        Player targetPlayer;
        if(target == null){
            if(sender instanceof Player){
                targetPlayer = (Player) sender;
            }else{
                CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
                return;
            }
        }else{
            targetPlayer = target.getPlayer();
        }

        targetPlayer.setGameMode(GameMode.SURVIVAL);
        sendUpdateMessage(sender, targetPlayer, GameMode.SURVIVAL);
    }

    @Subcommand("a|adventure|2")
    @CommandPermission("lizardcore.command.gamemode.adventure")
    public void adventure(CommandSender sender, @Optional OnlinePlayer target){
        Player targetPlayer;
        if(target == null){
            if(sender instanceof Player){
                targetPlayer = (Player) sender;
            }else{
                CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
                return;
            }
        }else{
            targetPlayer = target.getPlayer();
        }

        targetPlayer.setGameMode(GameMode.ADVENTURE);
        sendUpdateMessage(sender, targetPlayer, GameMode.ADVENTURE);
    }

    @Subcommand("sp|spectator|3")
    @CommandPermission("lizardcore.command.gamemode.spectator")
    public void spectator(CommandSender sender, @Optional OnlinePlayer target){
        Player targetPlayer;
        if(target == null){
            if(sender instanceof Player){
                targetPlayer = (Player) sender;
            }else{
                CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
                return;
            }
        }else{
            targetPlayer = target.getPlayer();
        }

        targetPlayer.setGameMode(GameMode.SPECTATOR);
        sendUpdateMessage(sender, targetPlayer, GameMode.SPECTATOR);
    }

    private void sendUpdateMessage(CommandSender sender, Player targetPlayer, GameMode newGameMode){
        if(sender.equals(targetPlayer)){
            CommandUtil.sendMessage(sender, ChatColor.YELLOW + "Your " + ChatColor.GREEN + "gamemode has been set to " + ChatColor.YELLOW + newGameMode.name());
        }else{
            CommandUtil.sendMessage(sender, ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GREEN + "'s gamemode has been set to " + ChatColor.YELLOW + newGameMode.name());
        }
    }
}
