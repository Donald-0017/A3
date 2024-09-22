package com.comp2120.a3.system;

import com.comp2120.a3.config.MovementSystemConfig;
import com.googlecode.lanterna.input.KeyStroke;

public class MovementSystem extends SystemBase<MovementSystemConfig> {
    public KeyStroke moveUp;
    public KeyStroke moveDown;
    public KeyStroke moveLeft;
    public KeyStroke moveRight;

    private int playerX;
    private int playerY;

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public MovementSystem() {
        super(MovementSystemConfig.class);
    }

    @Override
    public String configPath() {
        return "movement_system.json";
    }

    @Override
    public void start() {
        System.out.println("MovementSystem started.");
        moveUp = KeyStroke.fromString(this.getConfig().moveUp);
        moveDown = KeyStroke.fromString(this.getConfig().moveDown);
        moveLeft = KeyStroke.fromString(this.getConfig().moveLeft);
        moveRight = KeyStroke.fromString(this.getConfig().moveRight);
    }

    @Override
    public void stop() {
        System.out.println("MovementSystem stopped.");
    }

    public void initPlayerPosition() {
        MapSystem mapSystem = getEngine().getSystem(MapSystem.class);
        // initialize player's position
        for (int i = 0; i < mapSystem.getHeight(); i++) {
            for (int j = 0; j < mapSystem.getWidth(); j++) {
                //  'P' represent the player
                if (mapSystem.getMapTile(j, i) == 'P') {
                    playerX = j;
                    playerY = i;
                    break;
                }
            }
        }
    }

    /**
     * Parse the key string from the JSON and convert it into the corresponding KeyStroke.
     *
     * @param direction The character of the grid that the player is expected to enter next,
     *                  and this method will respond accordingly based on that character.
     * @author Yeshen Gao
     */
    public void movePlayer(KeyStroke direction) {
        MapSystem mapSystem = getEngine().getSystem(MapSystem.class);

        int newX = playerX;
        int newY = playerY;

        if (direction.equals(moveUp)) { // up
            newY--;
        } else if (direction.equals(moveDown)) { // down
            newY++;
        } else if (direction.equals(moveLeft)) { // left
            newX--;
        } else if (direction.equals(moveRight)) { // right
            newX++;
        }

        // check if the grid can be set foot on
        if (newX >= 0 && newX < mapSystem.getWidth() && newY >= 0 && newY < mapSystem.getHeight()) {
            char encountered = mapSystem.getMapTile(newX, newY);

            if (encountered == ' ') {  // can only move to an empty space
                mapSystem.setMapTile(playerX, playerY, ' ');  // erased the old grid
                playerX = newX;
                playerY = newY;
                mapSystem.setMapTile(playerX, playerY, 'P'); //  update the new grid
            } else {
                //TODO simplify this part
                //detect if the player triggered any event?
                switch (encountered) {
                    case 'E': // enter the entrance room of Dungeons
                        // load and launch TownSystem
                        //TODO maybe LevelSystem.loadLevel(0)? // where 0 is the entrance?
                        mapSystem.changeMap("dungeon_entrance.json"); //player enter the Dungeon's entrance
                        break;
                    case '[': // enter the Dungeon
                        System.out.println("You are starting an adventure");
                        //TODO maybe LevelSystem.loadLevel(Integer.parseInt(mapSystem.getMapTile(newX + 1, newY)))?
                        if (mapSystem.getMapTile(newX + 1, newY) == '1') {
                            // load and launch TownSystem
                            mapSystem.changeMap("dungeon_1.json"); //player is going to enter the Dungeon
                        }
                        if (mapSystem.getMapTile(newX + 1, newY) == '2') {
                            // load and launch TownSystem
                            mapSystem.changeMap("dungeon_2.json"); //player is going to enter the Dungeon
                        }
                        // other unknown encounter, do nothing
                    default:
                        break;
                }
            }
        }
    }
}
