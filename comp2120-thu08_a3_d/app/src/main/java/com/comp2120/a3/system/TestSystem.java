package com.comp2120.a3.system;

import com.comp2120.a3.config.TestSystemConfig;
import com.comp2120.a3.inventory.*;
import com.comp2120.a3.ui.InventoryPanel;
import com.comp2120.a3.ui.MapPanel;
import com.comp2120.a3.ui.TestPanel;
import com.googlecode.lanterna.input.KeyStroke;

public class TestSystem extends SystemBase<TestSystemConfig>/*crucial*/ {

    /**
     * Empty constructor, passes type of the config class to the super class
     * THIS IS CRUCIAL
     *
     * @author Jason Xu
     * @see SystemBase
     */
    public TestSystem() {
        super(TestSystemConfig.class);
    }

    @Override
    public String configPath() {
        return "test_system.json";
    }

    public void start() {
        System.out.println("TestSystem started");
        System.out.println("Config's welcome message: " + getConfig().welcomeMessage);
        System.out.println("Hello from TestSystem, engine is running = " + getEngine().isRunning());
        // If the engine has a render system, set the panel to be rendered to TestPanel
        if (getEngine().hasSystem(RenderSystem.class)) {
            InputSystem inputSystem = getEngine().getSystem(InputSystem.class);
            RenderSystem renderSystem = getEngine().getSystem(RenderSystem.class);
            MapSystem mapSystem = getEngine().getSystem(MapSystem.class);
            InventorySystem inventorySystem = getEngine().getSystem(InventorySystem.class);
            inventorySystem.obtainInventory(Gold.class, 100);
            inventorySystem.obtainInventory(Key.class, 3);
            inventorySystem.obtainInventory(Sword.class, 1);
            renderSystem.setPanel(MapPanel.class);

//            renderSystem.setPanel(InventoryPanel.class);
//            inputSystem.registerKeymap(KeyStroke.fromString("t"), () ->
//                    renderSystem.setPanel(TestPanel.class)
//            );
//            inputSystem.registerKeymap(KeyStroke.fromString("i"), () ->
//                    renderSystem.setPanel(InventoryPanel.class)
//            );
        }
    }

    public void stop() {
        System.out.println("TestSystem stopped");
    }
}
