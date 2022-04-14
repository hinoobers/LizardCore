package org.hinoob.lizardcore.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.util.CommandUtil;

public class DemoTrollCommand extends BaseCommand {

    @CommandPermission("lizardcore.command.demotroll")
    @CommandAlias("demotroll")
    @SneakyThrows
    public void demotroll(CommandSender sender, @Optional OnlinePlayer onlinePlayer){
        if(onlinePlayer != null){
            PacketContainer demoTroll = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);

            demoTroll.getIntegers().write(0, 5); // reason
            demoTroll.getFloat().write(0, 0f); // value

            ProtocolLibrary.getProtocolManager().sendServerPacket(onlinePlayer.getPlayer(), demoTroll);
            CommandUtil.sendMessage(onlinePlayer.getPlayer(), ChatColor.YELLOW + onlinePlayer.getPlayer().getName() + " " + ChatColor.GREEN + "trolled");
        }else{
            CommandUtil.sendMessage(sender, ChatColor.RED + "You must specify a player!");
        }
    }
}
