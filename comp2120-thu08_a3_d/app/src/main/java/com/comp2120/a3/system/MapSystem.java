package com.comp2120.a3.system;

import com.comp2120.a3.map.MapData;
import com.comp2120.a3.config.MapSystemConfig;
import com.comp2120.a3.misc.JsonHelper;

public class MapSystem extends SystemBase<MapSystemConfig> {
    private char[][] currentMap;
    private String mapName;

    public String getMapName() {
        return mapName;
    }

    public int getWidth() {
        return currentMap[0].length;
    }

    public int getHeight() {
        return currentMap.length;
    }

    public char getMapTile(int x, int y) {
        return currentMap[y][x];
    }

    public void setMapTile(int x, int y, char tile) {
        currentMap[y][x] = tile;
    }

    public MapSystem() {
        super(MapSystemConfig.class);
    }


    @Override
    public String configPath() {
        return "map_system.json";
    }


    public void changeMap(String newMapFilePath) {
        MapData map = JsonHelper.deserialize(newMapFilePath, MapData.class);
        // store the mapdata into 2 dimensional structure
        currentMap = new char[map.getHeight()][map.getWidth()];

        for (int i = 0; i < map.getHeight(); i++) {
            currentMap[i] = map.getMapData()[i].toCharArray();  // transfer string into char line by line
        }

        mapName = map.getName();

        // initialize player's position
        getEngine().getSystem(MovementSystem.class).initPlayerPosition();
    }

    /**
     * Initialize the MapSystem, load the map from the path: mapFilePath
     *
     * @author Yeshen Gao
     */
    @Override
    public void start() {
        // load the default map
        changeMap(getConfig().defaultMapPath);
    }

    @Override
    public void stop() {
    }
}
