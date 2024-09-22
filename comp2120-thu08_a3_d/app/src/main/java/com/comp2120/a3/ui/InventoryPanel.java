package com.comp2120.a3.ui;

import com.comp2120.a3.inventory.InventoryBase;
import com.comp2120.a3.system.InputSystem;
import com.comp2120.a3.system.InventorySystem;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

public class InventoryPanel extends PanelBase {
    private int selectedPage;

    @Override
    public void onOpen() {
        Screen screen = getScreen();
        InputSystem inputSystem = getEngine().getSystem(InputSystem.class);
        inputSystem.registerKeymap(new KeyStroke(KeyType.ArrowLeft), () -> {
            if (selectedPage > 1) {
                selectedPage--;
            }
        });
        inputSystem.registerKeymap(new KeyStroke(KeyType.ArrowDown), () -> {
            InventorySystem inventorySystem = getEngine().getSystem(InventorySystem.class);
            if (selectedPage < inventorySystem.nonEmptyInventoryCount() / 5) {
                selectedPage++;
            }
        });
    }

    @Override
    public void onClose() {
        InputSystem inputSystem = getEngine().getSystem(InputSystem.class);
        inputSystem.removeKeymap(new KeyStroke(KeyType.ArrowLeft));
        inputSystem.removeKeymap(new KeyStroke(KeyType.ArrowDown));
    }

    @Override
    public void onRender(TextGraphics graphics) {
        //check page is correct
        InventorySystem inventorySystem = getEngine().getSystem(InventorySystem.class);
        if (selectedPage > inventorySystem.nonEmptyInventoryCount() / 5) {
            selectedPage = inventorySystem.nonEmptyInventoryCount() / 5;
        }
        //get items
        List<Class<? extends InventoryBase>> items = inventorySystem.getNonEmptyInventories();
        //draw ui
        int width = graphics.getSize().getColumns();
        int height = graphics.getSize().getRows();
        //make the title
        graphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(graphics.getSize().getColumns(), 3), '#');
        makeRow(graphics, 1, new String[]{"Inventories"});
        //make the table headers
        makeRow(graphics, 3, new String[]{"Entry", "Item", "Quantity"});
        //make the table border
        graphics.drawLine(0, 4, width, 4, '-');
        //make the table rows
        for (int i = 0; i < 5; i++) {
            int index = selectedPage * 5 + i;
            if (index >= items.size()) {
                break;
            }
            Class<? extends InventoryBase> item = items.get(index);
            makeRow(graphics, 5 + i, new String[]{
                    String.valueOf(index + 1),
                    inventorySystem.getInventoryName(item),
                    String.valueOf(inventorySystem.getInventoryQuantity(item))
            });
        }
        //make the table border
        graphics.drawLine(0, 10, width, 10, '-');
        //make the footer
        putTextCenter(graphics, 0, height - 3, width, 1, "Press arrow keys to change page");
        //page x/y
        putTextCenter(graphics, 0, height - 2, width, 1, "Page 1/1");
        //draw the border of the whole UI
        graphics.drawRectangle(new TerminalPosition(0, 2),
                new TerminalSize(width, height - 2), '#');
    }
}
