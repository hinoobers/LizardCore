package org.hinoob.lizardcore.util;

import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;

@UtilityClass
public class CommandUtil {

    public void sendMessage(CommandSender sender, String message) {
        if(sender instanceof Player){
            sender.sendMessage(message);
        }else{
            LizardCore.getInstance().log(message);
        }
    }
}
