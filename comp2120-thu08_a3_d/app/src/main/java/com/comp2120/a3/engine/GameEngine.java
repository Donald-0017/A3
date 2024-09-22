package com.comp2120.a3.engine;

import com.comp2120.a3.config.GameEngineConfig;
import com.comp2120.a3.config.IConfig;
import com.comp2120.a3.misc.ConfigLoader;
import com.comp2120.a3.system.SystemBase;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameEngine {
    private GameEngineConfig config;
    private boolean isRunning;
    private Map<Class<?>, SystemBase<? extends IConfig>> systems;

    /**
     * Get whether the engine is running.
     *
     * @return whether the engine is running
     * @author Jason Xu
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Get the engine configuration.
     *
     * @return the engine configuration
     * @author Jason Xu
     */
    public GameEngineConfig getConfig() {
        return config;
    }

    /**
     * Get the system of the given class, configured in the engine.json
     *
     * @param systemClass the class of the system
     * @param <T>         the type of the system
     * @return the system of the given class
     * @author Jason Xu
     */
    public <T extends SystemBase<? extends IConfig>> T getSystem(Class<T> systemClass) {
        if (!systems.containsKey(systemClass)) {
            throw new RuntimeException("System not found: " + systemClass.getName());
        }
        SystemBase<? extends IConfig> system = systems.get(systemClass);
        if (systemClass.isInstance(system)) {
            return systemClass.cast(system);  // Safe cast
        } else {
            throw new IllegalArgumentException("Type mismatch: " + systemClass.getName());
        }
    }

    /**
     * Check whether the engine has the system of the given class
     *
     * @param systemClass the class of the system
     * @return whether the engine has the system of the given class
     * @author Jason Xu
     */
    public boolean hasSystem(Class<? extends SystemBase<? extends IConfig>> systemClass) {
        return systems.containsKey(systemClass);
    }

    /**
     * Run the game engine, using engine.json as the config file
     *
     * @throws RuntimeException if there is a duplicate system or system instantiation fails or config file not found
     * @author Jason Xu
     */
    public void start() {
        start("engine.json");
    }

    /**
     * Run the game engine
     *
     * @throws RuntimeException if there is a duplicate system or system instantiation fails or config file not found
     * @author Jason Xu
     */
    public void start(String configPath) {
        // Load the engine config
        config = ConfigLoader.loadConfig(configPath, GameEngineConfig.class);
        // New map for systems
        systems = new LinkedHashMap<>();
        // Bind Systems
        for (String systemName : config.getSystemNames()) {
            try {
                if (!systemName.startsWith("com.comp2120.a3.system")) {
                    systemName = "com.comp2120.a3.system." + systemName;
                }
                // Use reflection to instantiate the system
                Class<?> systemClass = Class.forName(systemName);
                // Cannot have duplicate systems
                if (systems.containsKey(systemClass)) {
                    throw new RuntimeException("Duplicate system: " + systemName);
                }
                // Instantiate the system
                SystemBase<? extends IConfig> system = (SystemBase<? extends IConfig>) systemClass.getDeclaredConstructor().newInstance();
                // Set engine and config
                system.setEngine(this);
                system.setConfig();
                // Add to systems map
                systems.put(systemClass, system);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        isRunning = true;
        // Start Systems
        for (SystemBase<? extends IConfig> system : systems.values()) {
            system.start();
        }
        System.out.println("GameEngine is running");


    }

    /**
     * Stop the game engine, usually called when the game is closed
     *
     * @author Jason Xu
     */
    public void stop() {
        for (SystemBase<? extends IConfig> system : systems.values()) {
            system.stop();
        }
        isRunning = false;
        System.out.println("GameEngine is stopped");
    }
}
