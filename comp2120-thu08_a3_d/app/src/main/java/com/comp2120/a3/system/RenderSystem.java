package com.comp2120.a3.system;

import com.comp2120.a3.config.RenderSystemConfig;
import com.comp2120.a3.ui.PanelBase;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * The Rendering System. This is a thin wrapper on a renderer. The system creates
 * the renderer, then starts it in its own thread.
 */
public class RenderSystem extends SystemBase<RenderSystemConfig> {
    private Screen screen;
    private int totalFrameCnt;
    private PanelBase curPanel;
    private final Map<Class<? extends PanelBase>, PanelBase> panels = new HashMap<>();

    public RenderSystem() {
        super(RenderSystemConfig.class);
    }

    /**
     * Get the single instance of the Screen the game renders and reads input to/from.
     *
     * @return The screen that the renderer is using.
     */
    public Screen getScreen() {
        return this.screen;
    }

    /**
     * Get the total number of frames rendered by the renderer.
     *
     * @return The total number of frames rendered.
     */
    public int getRenderedFrameCount() {
        return this.totalFrameCnt;
    }

    /**
     * Set the current panel to be rendered.
     *
     * @author Jason Xu
     */
    public <T extends PanelBase> void setPanel(Class<T> panelClass) {
        if (curPanel != null) {
            if (curPanel.getClass() == panelClass) {
                return;
            }
            curPanel.onClose();
        }
        if (panels.containsKey(panelClass)) {
            curPanel = panels.get(panelClass);
        } else {
            try {
                curPanel = panelClass.getDeclaredConstructor().newInstance();
                curPanel.setEngine(getEngine());
                panels.put(panelClass, curPanel);
            } catch (Exception e) {
                throw new RuntimeException("Could not create panel: " + e);
            }
        }
        //clear last frame contents
        screen.clear();
        curPanel.onOpen();
    }

    @Override
    public String configPath() {
        return "render_system.json";
    }

    @Override
    public void start() {
        // create the screen
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        try {
            screen = new TerminalScreen(defaultTerminalFactory.createTerminal());
        } catch (IOException e) {
            throw new RuntimeException("Renderer could not create a terminal: " + e);
        }

        // start the screen
        try {
            screen.startScreen();
            screen.setCursorPosition(null);
            screen.clear();
        } catch (IOException e) {
            throw new RuntimeException("Renderer could not start the screen: " + e);
        }

        Duration sleepDuration = Duration.ofSeconds(1).dividedBy(getEngine().getConfig().getFps());
        TextGraphics graphics = screen.newTextGraphics();
        Thread renderThread = new Thread(() -> {
            while (getEngine().isRunning()) {
                //update frame count
                totalFrameCnt++;
                //render the current panel
                if (curPanel != null) {
                    curPanel.onRender(graphics);
                }
                //refresh the screen - render the frame
                try {
                    screen.refresh();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //sleep render thread for the duration of the frame
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    // Do nothing, this is expected to happen, and is okay.
                    // We will interrupt the thread when closing the renderer to make the process quicker
                }
            }

            // the game engine has stopped. Try to close the screen and return
            try {
                screen.stopScreen();
            } catch (IOException e) {
                throw new RuntimeException("Renderer could not stop the screen: " + e);
            }

        });
        renderThread.start();
    }

    /**
     * This does nothing, the rendering thread will automatically shut down when
     * the engine stops
     */
    @Override
    public void stop() {
    }
}
