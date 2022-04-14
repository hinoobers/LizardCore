package org.hinoob.lizardcore.mysql;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ConnectionCredentials {

    private String host, username, password, database;
    private int port;
    private boolean useSSL;
}
