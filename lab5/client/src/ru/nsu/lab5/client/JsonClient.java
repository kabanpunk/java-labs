package ru.nsu.lab5.client;
import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.nsu.lab5.network.Message;
import ru.nsu.lab5.network.MessageType;
import ru.nsu.lab5.network.TCPConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class JsonClient extends AbstractClient {
    private static final Logger logger = LogManager.getLogger(JsonClient.class);
    private final Gson gson = new Gson();

    private TCPConnection connection;
    private volatile boolean isConnect = false;
    private ModelClient model;
    private ViewGuiClient gui;

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        isConnect = true;
        gui.addMessage("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, Object msg) {
        try {
            Message message = gson.fromJson((String) msg, Message.class);

            if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                String nameUser = gui.getNameUser();
                connection.send(gson.toJson(new Message(MessageType.USER_NAME, nameUser)));
            }

            if (message.getTypeMessage() == MessageType.NAME_USED) {
                gui.errorDialogWindow("This name is already using. Try another");
                String nameUser = gui.getNameUser();
                connection.send(gson.toJson(new Message(MessageType.USER_NAME, nameUser)));
            }

            if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                gui.addMessage("Name accepted\n");
                model.setUsers(message.getListUsers());
            }

            if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                String stringMessage = message.getTextMessage();
                gui.addMessage(stringMessage);
            }

            if (message.getTypeMessage() == MessageType.USER_ADDED) {
                model.addUser(message.getTextMessage());
                gui.refreshListUsers(model.getUsers());
                gui.addMessage(String.format("We have new participant! %s joined to the chat.\n", message.getTextMessage()));
            }

            if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                model.removeUser(message.getTextMessage());
                gui.refreshListUsers(model.getUsers());
                gui.addMessage(String.format("We lost our participant! %s left the chat.\n", message.getTextMessage()));
            }
        } catch (Exception e) {
            gui.errorDialogWindow("Error in receiving message occurred");
            logger.error("Error in receiving message occurred: " + e);
            isConnect = false;
            gui.refreshListUsers(model.getUsers());
        }
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        gui.addMessage("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        gui.addMessage("Connection exception: " + e);
    }

    @Override
    public void connectToServer() {
        if (!isConnect) {
            String IP_ADDR = JOptionPane.showInputDialog("IP:");
            int PORT = parseInt(JOptionPane.showInputDialog("PORT:"));
            try {
                connection = new TCPConnection(this, IP_ADDR, PORT);
                isConnect = true;
                gui.addMessage("You connected to the server\n");
            } catch (IOException e) {
                gui.errorDialogWindow("You typed wrong port. Try another");
            }
        }
        else {
            gui.errorDialogWindow("You are already connected!");
        }
    }

    @Override
    public void disableClient() {
        try {
            if (isConnect) {
                connection.send(gson.toJson(new Message(MessageType.DISABLE_USER)));
                model.getUsers().clear();
                setConnect(false);
                connection.disconnect();
                gui.refreshListUsers(model.getUsers());
            }
            else {
                gui.errorDialogWindow("You are already disconnected");
            }
        } catch (Exception e) {
            gui.errorDialogWindow("Error with closing connection occurred");
        }
    }

    @Override
    public void run() {
        model = new ModelClient();
        gui = new ViewGuiClient(this);
        gui.initFrameClient();
    }

    @Override
    public void sendMessageOnServer(String text) {
        try {
            Message message = new Message(MessageType.TEXT_MESSAGE, text);
            String jsonObject = gson.toJson(message);
            connection.send(jsonObject);
        } catch (Exception e) {
            gui.errorDialogWindow("Error with sending message occurred");
            logger.info("client couldn't send a message");
        }
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean status) {
        isConnect = status;
    }

}
