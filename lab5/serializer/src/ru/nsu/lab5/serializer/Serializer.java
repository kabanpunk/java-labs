package ru.nsu.lab5.serializer;

import java.io.*;
import java.util.Base64;
import com.google.gson.Gson;

public class Serializer {
    private final SerializationMode serializationMode;
    private final Gson gson;

    public Serializer (SerializationMode serializationMode) {
        this.serializationMode = serializationMode;
        gson = new Gson();
    }

    public String serialize(Message msg) throws IOException {
        switch (serializationMode) {
            case JSON_SERIALIZATION:
                return gson.toJson(msg);
            case DEFAULT_SERIALIZTION:
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream( baos );
                oos.writeObject( this );
                oos.close();
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            default:
                throw new IllegalStateException("Unexpected value: " + serializationMode);
        }
    }

    public Message deserialize(String rec) throws IOException, ClassNotFoundException {
        switch (serializationMode) {
            case JSON_SERIALIZATION:
                return gson.fromJson(rec, Message.class);
            case DEFAULT_SERIALIZTION:
                byte [] data = Base64.getDecoder().decode( rec );
                ObjectInputStream ois = new ObjectInputStream(
                        new ByteArrayInputStream(  data ) );
                Object object = ois.readObject();
                ois.close();
                return (Message) object;
            default:
                throw new IllegalStateException("Unexpected value: " + serializationMode);
        }
    }
}
