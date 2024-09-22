package com.comp2120.a3.system;

import com.comp2120.a3.config.InputSystemConfig;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InputSystem extends SystemBase<InputSystemConfig> {

    // initialised on first use
    private Map<KeyStroke, Runnable> keyMap;

    private KeyStroke lastInput;

    public InputSystem() {
        super(InputSystemConfig.class);
    }

    /**
     * Register a new key mapping. Note that if the key combination is already registered, the old action will be replaced
     *
     * @param key    the key combination to listen for
     * @param action the function to call when the keystroke is detected
     * @author Mitchell Browne
     */
    public void registerKeymap(KeyStroke key, Runnable action) {
        if (keyMap == null) {
            keyMap = new HashMap<>();
        }

        keyMap.put(key, action);
    }

    /**
     * Remove a key mapping.
     *
     * @param key the key combination to remove
     * @author Jason Xu
     */
    public void removeKeymap(KeyStroke key) {
        keyMap.remove(key);
    }

    /**
     * Mock a key input for testing purposes.
     *
     * @param input the keystroke to mock
     */
    public void mockInput(KeyStroke input) {
        triggerInput(input);
    }

    /**
     * Trigger an input event.
     *
     * @param input the keystroke to trigger
     */
    private void triggerInput(KeyStroke input) {
        lastInput = input;
        Runnable action = keyMap.get(input);
        if (action != null) {
            action.run();
        }
    }

    /**
     * Get the last input that was received by the system.
     *
     * @return the last input that was received by the system
     * @author Jason Xu
     */
    public KeyStroke getLastInput() {
        return lastInput;
    }

    @Override
    public String configPath() {
        return "input_system.json";
    }

    @Override
    public void start() {
        System.out.println("the input system is starting!");

        // register keymaps that are known from the config file.
        this.registerKeymap(KeyStroke.fromString(this.getConfig().keyQuit), () -> this.getEngine().stop());

        // if there is no render system, we assume it is not running on an interactable terminal so no need for listening for input.
        if (!this.getEngine().hasSystem(RenderSystem.class)) {
            return;
        }

        // if there is a render system, we can assume it is running on an interactable terminal so we can listen for input.
        // we get the screen from the render system and poll for input on a separate thread.
        Screen screen = this.getEngine().getSystem(RenderSystem.class).getScreen();
        Duration sleepDuration = Duration.ofSeconds(1).dividedBy(this.getConfig().updateFrequency);

        Thread keyMonitorThread = new Thread(() -> {
            while (this.getEngine().isRunning()) {
                try {
                    // we poll one input per update cycle
                    KeyStroke input = screen.pollInput();
                    // Input might be null since it polls rather than block and wait til a valid input
                    if (input != null) {
                        triggerInput(input);
                    }

                } catch (IOException e) {
                    throw new RuntimeException("error polling for input: " + e);
                }

                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException("the key monitoring thread was unexpectedly interrupted: " + e);
                }
            }
        });
        keyMonitorThread.start();
    }

    @Override
    public void stop() {
    }
}

