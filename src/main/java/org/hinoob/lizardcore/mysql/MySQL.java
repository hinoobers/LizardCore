package org.hinoob.lizardcore.mysql;

import lombok.Setter;
import org.hinoob.lizardcore.LizardCore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class MySQL {

    @Setter private ConnectionCredentials credentials;
    @Setter private File sqLiteFile;

    private final DriverType type;

    private Connection connection; // Connection object

    public MySQL(DriverType type){
        this.type = type;
    }

    @SuppressWarnings("all")
    public boolean connect(){
        if(this.type == DriverType.MYSQL){
            if(credentials == null){
                LizardCore.getInstance().getLogger().info("[ERROR] MySQL Credentials are null!");
                return false;
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
                final Properties prop=new Properties();
                prop.setProperty("user", credentials.getUsername());
                prop.setProperty("password", credentials.getPassword());
                prop.setProperty("useSSL", String.valueOf(credentials.isUseSSL()));
                prop.setProperty("autoReconnect","true");
                this.connection = DriverManager.getConnection("jdbc:mysql://"+credentials.getHost()+"/"+credentials.getDatabase(),prop);

                return true;
            }catch(Exception ex){
                if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                    ex.printStackTrace();
                }
                return false;
            }
        }else if(type == DriverType.SQLITE){
            if(sqLiteFile == null){
                try {
                    sqLiteFile.createNewFile();
                }catch(Exception ex){
                    if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                        ex.printStackTrace();
                    }
                    return false;
                }
            }

            try {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:"+sqLiteFile.getAbsolutePath());

                return true;
            }catch(Exception ex){
                if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                    ex.printStackTrace();
                }
                return false;
            }
        }

        return false;
    }

    public ResultSet executeQuery(String query){
        if(!query.endsWith(";")){
            // general mistake, let's correct it
            query += ";";
        }

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            return statement.executeQuery();
        }catch(Exception ex){
            if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                ex.printStackTrace();
            }
            return null;
        }
    }

    public boolean executeUpdate(String query){
        if(!query.endsWith(";")){
            // general mistake, let's correct it
            query += ";";
        }

        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.executeUpdate();

            return true;
        }catch(Exception ex){
            if(LizardCore.getInstance().getConfig().getBoolean("debug")){
                ex.printStackTrace();
            }
            return false;
        }
    }
}
