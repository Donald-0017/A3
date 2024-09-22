package com.comp2120.a3.misc;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {
    /**
     * Gson object for JSON deserialization
     */
    private static final Gson gson = new Gson();

    /**
     * Deserialize a JSON file to an object
     *
     * @param path the JSON config path under resources
     * @param type the class of the object
     * @param <T>  the type of the object
     * @return the deserialized config java object
     * @author Jason Xu
     */
    public static <T> T deserialize(String path, Class<T> type) {
        // Load the JSON file from the resources folder
        try (InputStream stream = JsonHelper.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new RuntimeException("File not found: " + path);
            }
            // Read the JSON file as a string
            String json = new String(stream.readAllBytes());
            // Deserialize the JSON string to a java object
            return gson.fromJson(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
