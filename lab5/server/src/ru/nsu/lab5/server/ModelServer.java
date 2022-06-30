package ru.nsu.lab5.server;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import ru.nsu.lab5.network.TCPConnection;

import java.util.HashMap;
import java.util.Map;

public class ModelServer {
    private final BidiMap<String, TCPConnection> allUsers = new DualHashBidiMap<>();

    public Map<String, TCPConnection> getAllUsers() {
        return allUsers;
    }

    public void addUser(String nameUser, TCPConnection connection) {
        allUsers.put(nameUser, connection);
    }

    public void removeUserByName(String nameUser) {
        allUsers.remove(nameUser);
    }

    public void removeUserByTCPConnection(TCPConnection connection) {
        allUsers.removeValue(connection);
    }

    public String getUserNameByTCPConnection(TCPConnection connection) {
        return allUsers.getKey(connection);
    }

}