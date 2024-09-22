package com.comp2120.a3.config;


import java.util.List;

/**
 * Configuration for the game engine.
 *
 * @author Jason Xu
 */
public class GameEngineConfig implements IConfig {
    private int fps;
    private List<String> systemNames;

    /**
     * @return names of the systems to be added on the engine
     */
    public List<String> getSystemNames() {
        return systemNames;
    }

    public int getFps() {
        return fps;
    }
}
