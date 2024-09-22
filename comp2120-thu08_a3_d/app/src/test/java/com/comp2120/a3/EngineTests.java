package com.comp2120.a3;

import com.comp2120.a3.engine.GameEngine;
import com.comp2120.a3.system.TestSystem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTests {
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
    public void ensureEngineIsRunning() {
        // Ensure the engine is running, prevent other tests stops the engine
        defaultEngine.start("engine_unit_test.json");
        assertTrue(defaultEngine.isRunning());
    }

    @Test
    public void ensureEngineHasTestSystem() {
        assertTrue(defaultEngine.hasSystem(TestSystem.class));
        assertNotNull(defaultEngine.getSystem(TestSystem.class));
    }

    @Test
    public void ensureEngineHasConfig() {
        assertNotNull(defaultEngine.getConfig());
    }

    @Test
    public void checkEngineStop() {
        defaultEngine.stop();
        assertFalse(defaultEngine.isRunning());
    }
}
