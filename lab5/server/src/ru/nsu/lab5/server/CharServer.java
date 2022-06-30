package ru.nsu.lab5.server;

import ru.nsu.lab5.network.SerializationMode;
import ru.nsu.lab5.network.TCPConnection;
import ru.nsu.lab5.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CharServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new CharServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private CharServer() {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            while(true) {
                try {
                    new TCPConnection(this, serverSocket.accept(), SerializationMode.JSON_SERIALIZATION);
                }
                catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).sendString(value);
        }
    }
}
