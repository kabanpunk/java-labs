package ru.nsu.lab5.server;

import ru.nsu.lab5.network.Message;
import ru.nsu.lab5.network.TCPConnection;
import ru.nsu.lab5.network.TCPConnectionListener;

public abstract class AbstractServer  implements TCPConnectionListener {
    public abstract void run();

    public abstract void startServer(int port);

    public abstract void stopServer();

    public abstract void sendMessageAllUsers(Message message);
    public abstract void onConnectionReady(TCPConnection tcpConnection);
    public abstract void onReceiveString(TCPConnection tcpConnection, Object message);
    public abstract void onDisconnect(TCPConnection tcpConnection);
    public abstract void onException(TCPConnection tcpConnection, Exception e);
}
