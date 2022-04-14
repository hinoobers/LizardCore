package org.hinoob.lizardcore.manager;

import org.hinoob.lizardcore.LizardCore;
import org.hinoob.lizardcore.mysql.ConnectionCredentials;
import org.hinoob.lizardcore.mysql.DriverType;
import org.hinoob.lizardcore.mysql.MySQL;

import java.io.File;

public class DatabaseManager {

    private final LizardCore core;

    private MySQL mySQL;

    public DatabaseManager(LizardCore core){
        this.core = core;
    }

    public boolean load(){
        String driverType = core.getConfig().getString("database.driver");
        if(driverType == null){
            core.getLogger().info("[ERROR] Invalid database driver type: null");
            return false;
        }

        if(driverType.equalsIgnoreCase("mysql")){
            this.mySQL = new MySQL(DriverType.MYSQL);

            String host = core.getConfig().getString("database.mysql.host");
            String username = core.getConfig().getString("database.mysql.username");
            String password = core.getConfig().getString("database.mysql.password");
            String database = core.getConfig().getString("database.mysql.database");
            int port = core.getConfig().getInt("database.mysql.port");
            boolean useSSL = core.getConfig().getBoolean("database.mysql.useSSL");
            this.mySQL.setCredentials(new ConnectionCredentials(host, username, password, database, port, useSSL));
        }else if(driverType.equalsIgnoreCase("sqlite")){
            this.mySQL = new MySQL(DriverType.SQLITE);

            String sqlPath = core.getConfig().getString("database.sqlite.path").replaceAll("%plugindatafolder%", core.getDataFolder().getPath());
            this.mySQL.setSqLiteFile(new File(sqlPath));
        }else{
            core.getLogger().info("[ERROR] Invalid database driver type: " + driverType);
            return false;
        }

        if(this.mySQL != null){
            boolean result = this.mySQL.connect();

            if(result){
                core.getLogger().info("Connected to the database! (Driver=" + driverType + ")");

                core.getLogger().info("Creating/Updating tables...");
                // --
                mySQL.executeUpdate("CREATE TABLE IF NOT EXISTS `lizardcore_users` (uuid VARCHAR(255), vanished BOOLEAN);");
                // --
                core.getLogger().info("Tables created/updated");

                return true;
            }else{
                core.getLogger().info("[ERROR] Could not connect to database");
                return false;
            }
        }else{
            return false;
        }
    }

    public MySQL getMysql(){
        return mySQL;
    }
}
