package ru.nsu.lab5.network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    private final SerializationMode serializationMode;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port, SerializationMode serializationMode) throws IOException {
        this(eventListener, new Socket(ipAddr, port));
    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket, SerializationMode serializationMode) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        eventListener.onReceiveString(TCPConnection.this, in.readLine());
                    }
                }
                catch (IOException e) {
                    eventListener.onException(TCPConnection.this, e);
                }
                finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }

            }
        });
        rxThread.start();
    }

    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush();
        }
        catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void sendMessage(Message msg) {
        try {
            switch (msg.getSerializationMode()) {
                case JSON_SERIALIZATION -> out.write(msg.getJson());
                case DEFAULT_SERIALIZTION -> out.write(msg.getString());
                default -> throw new IllegalStateException("Unexpected value: " + msg.getSerializationMode());
            }
            //out.write( "\r\n");
            out.flush();
        }
        catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        }
        catch (IOException e) {
            eventListener.onException(TCPConnection.this, e);
        }
    }

    public String serialize() throws IOException {
        switch (getSerializationMode()) {
            case JSON_SERIALIZATION:
                return gson.toJson(this);
            case DEFAULT_SERIALIZTION:
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream( baos );
                oos.writeObject( this );
                oos.close();
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            default:
                return "";
        }
    }

    public String deserialize(String data) throws IOException {
        switch (getSerializationMode()) {
            case JSON_SERIALIZATION:
                return gson.toJson(this);
            case DEFAULT_SERIALIZTION:
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream( baos );
                oos.writeObject( this );
                oos.close();
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ":" + socket.getPort();
    }
}
