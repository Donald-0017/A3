package com.comp2120.a3.system;

import com.comp2120.a3.config.IConfig;
import com.comp2120.a3.engine.GameEngine;
import com.comp2120.a3.misc.ConfigLoader;

/**
 * A System is a part of the game engine, could be a RenderSystem, AudioSystem, NpcSystem, PlayerSystem, etc.
 * <br>
 * All systems need to extend this abstract class.
 * <br>
 * To add a system to the engine, do the following (or refer to {@link TestSystem}):
 * <ol>
 *     <li>Create a new config class that implements {@link IConfig}</li>
 *     <li>Create a new config file in the <b>resources</b> folder</li>
 *     <li>Create a new class that extends this class and pass the Config's Class as the type parameter</li>
 *     <li>Declare a parameterless constructor that pass the Config's Class's class to the super constructor</li>
 *     <li>Implement the abstract methods</li>
 *     <li>Add the system name to the engine.json file's systemNames section</li>
 *     <li>Run the engine, this system will be automatically added</li>
 * </ol>
 * <br>
 * It is important to add generic parameter to the class, e.g. {@link TestSystem}.
 * <br>
 * It is also important to have an empty constructor that passes the type of the config to this super class.
 * For example, <code>super(TestSystemConfig.class)</code> in {@link TestSystem}.
 * <br>
 * <br>
 * The engine will automatically instantiate the system and call its {@link #start()}  and {@link #stop()} methods.
 *
 * @param <T> the type of the config file for the system
 * @author Jason Xu
 */
public abstract class SystemBase<T extends IConfig> {
    private GameEngine engine;
    private T config;
    private final Class<T> configClass;

    public SystemBase(Class<T> configClass) {
        this.configClass = configClass;
    }

    public void setConfig() {
        if (this.config != null) {
            throw new IllegalArgumentException("Config already set");
        }
        this.config = ConfigLoader.loadConfig(configPath(), configClass);
    }

    public T getConfig() {
        return config;
    }

    public void setEngine(GameEngine engine) {
        if (this.engine != null) {
            throw new IllegalArgumentException("Engine already set");
        }
        this.engine = engine;
    }

    protected GameEngine getEngine() {
        return engine;
    }

    public abstract String configPath();

    public abstract void start();

    public abstract void stop();
}
