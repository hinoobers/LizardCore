package org.hinoob.lizardcore.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.guieffect.qual.UIType;
import org.hinoob.lizardcore.LizardCore;

import java.util.List;

@UtilityClass
public class VanishUtil {

    @SneakyThrows
    @SuppressWarnings("deprecation")
    public void toggleVanish(Player player, boolean status) {
        if(status){
            for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if(otherPlayer == player) continue; // hide player from the player itself?
                if(otherPlayer.isOp() || otherPlayer.hasPermission("lizardcore.vanish.see")) continue; // so staff can see each other

                otherPlayer.hidePlayer(player);
            }
        }else{
            for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if(otherPlayer == player) continue;

                otherPlayer.showPlayer(player);
            }
        }
    }
}
