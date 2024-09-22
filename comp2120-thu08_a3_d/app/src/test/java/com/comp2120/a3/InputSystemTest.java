package com.comp2120.a3;

import com.comp2120.a3.engine.GameEngine;
import com.comp2120.a3.system.InputSystem;
import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class InputSystemTest {
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
    public void testInputQuit() {
        // Get the input system
        InputSystem system = defaultEngine.getSystem(InputSystem.class);
        // Check that the engine is running
        assertTrue(defaultEngine.isRunning());
        // Mock the quit key
        system.mockInput(KeyStroke.fromString("q"));
        // Check that the engine has stopped
        assertFalse(defaultEngine.isRunning());
    }

    @Test
    public void mockCustomKey() {
        // Get the input system
        InputSystem system = defaultEngine.getSystem(InputSystem.class);
        KeyStroke customKey = KeyStroke.fromString("c");
        AtomicBoolean flag = new AtomicBoolean(false);
        // Register
        system.registerKeymap(customKey, () -> {
            flag.set(true);
        });
        // Mock the custom key
        system.mockInput(customKey);
        // Check that the flag has been set
        assertTrue(flag.get());
    }

    @Test
    public void checkLastInput() {
        // Get the input system
        InputSystem system = defaultEngine.getSystem(InputSystem.class);
        KeyStroke customKey = KeyStroke.fromString("v");
        // Mock the custom key
        system.mockInput(customKey);
        // Check that the last input is the custom key
        assertEquals(system.getLastInput(), customKey);
    }

    @Test void checkRemoveKeymap() {
        // Get the input system
        InputSystem system = defaultEngine.getSystem(InputSystem.class);
        KeyStroke customKey = KeyStroke.fromString("r");
        AtomicBoolean flag = new AtomicBoolean(false);
        // Register
        system.registerKeymap(customKey, () -> {
            flag.set(true);
        });
        // Mock the custom key
        system.mockInput(customKey);
        // Check that the flag has been set
        assertTrue(flag.get());
        // Remove the keymap
        system.removeKeymap(customKey);
        // Reset the flag
        flag.set(false);
        // Mock the custom key
        system.mockInput(customKey);
        // Check that the flag has not been set
        assertFalse(flag.get());
    }
}