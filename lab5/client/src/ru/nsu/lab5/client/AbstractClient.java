package ru.nsu.lab5.client;

import ru.nsu.lab5.network.TCPConnection;
import ru.nsu.lab5.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbstractClient extends JFrame implements TCPConnectionListener {
    public abstract void run();

    public abstract void connectToServer();
    public abstract void onConnectionReady(TCPConnection tcpConnection);
    public abstract boolean isConnect();

    public abstract void disableClient();
    public abstract void onDisconnect(TCPConnection tcpConnection);


    public abstract void sendMessageOnServer(String text);


    public abstract void onException(TCPConnection tcpConnection, Exception e);
}
