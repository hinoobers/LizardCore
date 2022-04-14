package org.hinoob.lizardcore.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;

@CommandAlias("spawn")
@CommandPermission("lizardcore.command.spawn")
public class SpawnCommand extends BaseCommand {

    @HelpCommand
    @Default
    public void tp(Player player, @Optional OnlinePlayer targetPlayer){
        if (LizardCore.getInstance().getSpawnManager().getSpawnLocation() == null) {
            player.sendMessage(ChatColor.RED + "Spawn has not been set yet!");
            return;
        }

        if(targetPlayer == null){
            player.teleport(LizardCore.getInstance().getSpawnManager().getSpawnLocation());
        }else{
            if(player.hasPermission("lizardcore.command.spawn.others")){
                targetPlayer.getPlayer().teleport(LizardCore.getInstance().getSpawnManager().getSpawnLocation());
            }else{
                player.teleport(LizardCore.getInstance().getSpawnManager().getSpawnLocation());
            }
        }
    }

    @Subcommand("set")
    @CommandPermission("lizardcore.command.spawn.set")
    public void set(CommandSender sender){
        if(sender instanceof Player){
            Player player = (Player) sender;
            LizardCore.getInstance().getSpawnManager().setSpawn(player.getLocation());

            player.sendMessage(ChatColor.GREEN + "Spawn set to your location!");
        }
    }
}
