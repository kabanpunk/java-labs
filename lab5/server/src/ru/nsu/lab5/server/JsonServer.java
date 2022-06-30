package ru.nsu.lab5.server;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.nsu.lab5.network.Message;
import ru.nsu.lab5.network.MessageType;
import ru.nsu.lab5.network.TCPConnection;
import ru.nsu.lab5.network.TCPConnectionListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonServer extends AbstractServer {
    private final Gson gson = new Gson();
    private static final Logger logger = LogManager.getLogger(JsonServer.class);
    private ServerSocket serverSocket;
    private ViewGuiServer gui;
    private ModelServer model;
    private volatile boolean isServerStart = false;

    JsonServer() {

    }

    @Override
    public void run() {
        gui = new ViewGuiServer(this);
        model = new ModelServer();
        gui.initFrameServer();

        while (true) {
            if (isServerStart) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                    logger.info("new conection... ");
                }
                catch (IOException e) {
                    logger.info("TCPConnection exception: " + e);
                }
            }
        }
    }

    @Override
    public void startServer(int port) {
        logger.info("Server running...");
        try {
            serverSocket = new ServerSocket(port);
            isServerStart = true;
            gui.refreshDialogWindowServer("Server started.\n");
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Server couldn't start for some reason.\n");
        }
    }

    @Override
    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                for (Map.Entry<String, TCPConnection> user : model.getAllUsers().entrySet()) {
                    user.getValue().disconnect();
                }
                serverSocket.close();
                model.getAllUsers().clear();
            }
            else {
                gui.refreshDialogWindowServer("Server is not already working!\n");
            }
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Error with stopping server occurred.\n");
            logger.error(e);
        }
    }

    @Override
    public void sendMessageAllUsers(Message message) {
        for (Map.Entry<String, TCPConnection> user : model.getAllUsers().entrySet()) {
            try {
                user.getValue().send(gson.toJson(message));
            } catch (Exception e) {
                logger.error(e);
                gui.refreshDialogWindowServer("Error with sending the message occurred\n");
            }
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection connection) {
        connection.send(gson.toJson(new Message(MessageType.REQUEST_NAME_USER)));
        logger.info("Connected: " + connection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection connection, Object msg) {
        Message message = gson.fromJson((String) msg, Message.class);
        String userName = model.getUserNameByTCPConnection(connection);
        String content = message.getTextMessage();
        if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
            String textMessage = String.format("%s: %s\n", userName, message.getTextMessage());
            sendMessageAllUsers(new Message(MessageType.TEXT_MESSAGE, textMessage));
        }
        if (message.getTypeMessage() == MessageType.DISABLE_USER) {
            sendMessageAllUsers(new Message(MessageType.REMOVED_USER, userName));
            model.removeUserByName(userName);
            connection.disconnect();
            gui.refreshDialogWindowServer("User left the chat.\n");
        }
        if (message.getTypeMessage() == MessageType.USER_NAME) {
            if (content != null && !content.isEmpty() && !model.getAllUsers().containsKey(content)) {
                model.addUser(content, connection);
                Set<String> listUsers = new HashSet<>();
                for (Map.Entry<String, TCPConnection> users : model.getAllUsers().entrySet()) {
                    listUsers.add(users.getKey());
                }
                connection.send(gson.toJson(new Message(MessageType.NAME_ACCEPTED, listUsers)));
                sendMessageAllUsers(new Message(MessageType.USER_ADDED, content));
            }
            else {
                connection.send(gson.toJson(new Message(MessageType.NAME_USED)));
            }
        }
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        model.removeUserByTCPConnection(tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        logger.error("TCPConnection exception: " + e);
        gui.refreshDialogWindowServer("TCPConnection exception: " + e);
    }
}
