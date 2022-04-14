package org.hinoob.lizardcore.user;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.hinoob.lizardcore.LizardCore;

import java.sql.ResultSet;
import java.util.UUID;

public class User {

    private Player bukkitPlayer; // cache this object
    private final UUID uuidObject;

    @Getter private boolean vanished;

    public User(UUID uuid){
        this.uuidObject = uuid;

        load();
    }

    private void load(){
        ResultSet resultSet = LizardCore.getInstance().getDatabaseManager().getMysql().executeQuery("SELECT * FROM `lizardcore_users` WHERE `uuid` = '" + uuidObject.toString() + "'");

        if(resultSet == null){
            createData();
            return;
        }

        try {
            if (!resultSet.next()){
                // no data

                createData();
                return;
            }

            while(resultSet.next()){
                this.vanished = resultSet.getBoolean("vanished");
            }
        }catch(Exception ex){
            if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                ex.printStackTrace();
            }

            createData();
        }
    }

    private void createData(){
        LizardCore.getInstance().getLogger().info(uuidObject + " has no data, creating...");

        String query = "INSERT INTO `lizardcore_users` (`uuid`, `vanished`) VALUES ('" + uuidObject.toString() + "', '"+this.vanished+"')";
        LizardCore.getInstance().getDatabaseManager().getMysql().executeUpdate(query);
    }

    public void setVanished(boolean vanished){
        this.vanished = vanished;

        String query = "UPDATE `lizardcore_users` SET `vanished` = '" + this.vanished + "' WHERE `uuid` = '" + uuidObject.toString() + "'";
        LizardCore.getInstance().getDatabaseManager().getMysql().executeUpdate(query);
    }

    public Player getPlayer(){
        if(this.bukkitPlayer == null){
            this.bukkitPlayer = Bukkit.getPlayer(uuidObject);
        }

        return this.bukkitPlayer;
    }
}
