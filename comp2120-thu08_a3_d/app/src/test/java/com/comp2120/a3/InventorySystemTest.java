package com.comp2120.a3;

import com.comp2120.a3.engine.GameEngine;
import com.comp2120.a3.inventory.Gold;
import com.comp2120.a3.system.InventorySystem;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySystemTest {
    private static GameEngine defaultEngine;

    @BeforeAll
    public static void setUp() {
        defaultEngine = new GameEngine();
        defaultEngine.start("engine_unit_test.json");
    }

    @AfterAll
    public static void tearDown() {
        defaultEngine.stop();
    }

    @Test
    public void testGetAndUse() {
        // Get gold from the inventory system
        InventorySystem system = defaultEngine.getSystem(InventorySystem.class);
        assertEquals(system.getInventoryQuantity(Gold.class), 0);
        // Add some
        system.obtainInventory(Gold.class, 100);
        assertEquals(system.getInventoryQuantity(Gold.class), 100);
        // Use some
        system.useInventory(Gold.class, 50);
        assertEquals(system.getInventoryQuantity(Gold.class), 50);
    }
}
