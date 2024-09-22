package com.comp2120.a3.ui;

import com.comp2120.a3.system.InputSystem;
import com.comp2120.a3.system.MapSystem;
import com.comp2120.a3.system.MovementSystem;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;

public class MapPanel extends PanelBase {
    @Override
    public void onOpen() {
        InputSystem inputSystem = this.getEngine().getSystem(InputSystem.class);
        MovementSystem movementSystem = this.getEngine().getSystem(MovementSystem.class);
        inputSystem.registerKeymap(movementSystem.moveUp, () -> movementSystem.movePlayer(movementSystem.moveUp));
        inputSystem.registerKeymap(movementSystem.moveDown, () -> movementSystem.movePlayer(movementSystem.moveDown));
        inputSystem.registerKeymap(movementSystem.moveLeft, () -> movementSystem.movePlayer(movementSystem.moveLeft));
        inputSystem.registerKeymap(movementSystem.moveRight, () -> movementSystem.movePlayer(movementSystem.moveRight));
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onRender(TextGraphics graphics) {
        MapSystem mapSystem = getEngine().getSystem(MapSystem.class);
        // the position of the map while displaying on the screen
        int offsetX = mapSystem.getConfig().displayOffsetX;
        int offsetY = mapSystem.getConfig().displayOffsetY;

        // clear the screen
        graphics.fill(' ');

        // make map name as the title
        graphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(graphics.getSize().getColumns(), 3), '#');
        makeRow(graphics, 1, new String[]{mapSystem.getMapName()});

        // draw the Map on the screen
        for (int i = 0; i < mapSystem.getHeight(); i++) {
            for (int j = 0; j < mapSystem.getWidth(); j++) {
                // draw every char
                graphics.setCharacter(j + offsetX, i + offsetY, mapSystem.getMapTile(j, i));
            }
        }

        int width = graphics.getSize().getColumns();
        int height = graphics.getSize().getRows();
        //make the footer
        putTextCenter(graphics, 0, height - 1, width, 1, "Press arrow keys to move Player (P)");
    }
}
