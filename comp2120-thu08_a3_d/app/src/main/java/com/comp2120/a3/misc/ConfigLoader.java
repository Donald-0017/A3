package com.comp2120.a3.misc;

import com.comp2120.a3.config.IConfig;

/**
 * Load configuration from a JSON file
 *
 * @author Jason Xu
 */
public class ConfigLoader {
    /**
     * Deserialize a JSON config file to an object
     *
     * @param path the JSON config path under resources
     * @param type the class of the object
     * @param <T>  the type of the object
     * @return the deserialized config java object
     * @author Jason Xu
     */
    public static <T extends IConfig> T loadConfig(String path, Class<T> type) {
        return JsonHelper.deserialize(path, type);
    }
}
