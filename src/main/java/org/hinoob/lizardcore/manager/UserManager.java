package org.hinoob.lizardcore.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.mysql.MySQL;
import org.hinoob.lizardcore.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {


    private final LizardCore core;

    private final Map<UUID, User> userMap = new HashMap<>(); // cache users for 10 minutes

    public UserManager(LizardCore core){
        this.core = core;
    }

    public User getUser(UUID uuid){
        User user = userMap.getOrDefault(uuid, null);
        if(user == null){
            user = new User(uuid);
            userMap.put(uuid, user);
        }
        return user;
    }

    public void load(){
        // reload support

        for(Player player : Bukkit.getOnlinePlayers()){
            // guarantees that they have a user object
            getUser(player.getUniqueId());
        }
    }


}
