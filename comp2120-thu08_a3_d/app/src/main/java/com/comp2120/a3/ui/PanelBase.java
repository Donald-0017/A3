package com.comp2120.a3.ui;

import com.comp2120.a3.engine.GameEngine;
import com.comp2120.a3.system.RenderSystem;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

/**
 * Base class for all UI panels. To make a UI panel, simply extend this class and implement the abstract methods.
 * <br>
 * The {@link PanelBase#onRender(TextGraphics)} method is called each frame to render the panel, u should put all stuffs to render in this method.
 * Note that the last frame's content will be erased when rendering the new frame (before the new call to onRender).
 * <br>
 * You can set the current rendering panel by calling {@link com.comp2120.a3.system.RenderSystem#setPanel(Class)},
 * however u need to be aware of RenderSystem might not exist (i.e. in UnitTest).
 *
 * @author Jason Xu
 */
public abstract class PanelBase {
    private GameEngine engine;

    /**
     * Get the game engine.
     *
     * @return The game engine.
     */
    protected GameEngine getEngine() {
        return engine;
    }

    /**
     * Get the screen.
     *
     * @return The screen.
     */
    protected Screen getScreen() {
        return engine.getSystem(RenderSystem.class).getScreen();
    }

    /**
     * Render a string of text to the grid, centered.
     *
     * @param graphics   The graphics object to render things to.
     * @param x          The x position to render the text.
     * @param y          The y position to render the text.
     * @param gridWidth  The width of the grid to render the text in.
     * @param gridHeight The height of the grid to render the text in.
     * @param text       The text to render.
     * @author Jason Xu
     */
    protected void putTextCenter(TextGraphics graphics, int x, int y, int gridWidth, int gridHeight, String text) {
        x += gridWidth / 2 - text.length() / 2;
        y += gridHeight / 2;
        graphics.putString(x, y, text);
    }

    /**
     * Render a row of text to the screen (split into equal parts).
     *
     * @param graphics The graphics object to render things to.
     * @param y        Which row to render the text to.
     * @param text     The text to render.
     * @author Jason Xu
     */
    protected void makeRow(TextGraphics graphics, int y, String[] text) {
        int width = graphics.getSize().getColumns() / text.length;
        int ceilWidth = (int) Math.ceil((double) graphics.getSize().getColumns() / text.length);
        for (int i = 0; i < text.length; i++) {
            putTextCenter(graphics, i * ceilWidth, y, width, 1, text[i]);
        }
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Called when the panel is opened. (i.e. {@link com.comp2120.a3.system.RenderSystem#setPanel(Class)})
     */
    public abstract void onOpen();

    /**
     * Called when the panel is closed. (i.e. call {@link com.comp2120.a3.system.RenderSystem#setPanel(Class)} and it was rendering this panel)
     */
    public abstract void onClose();

    /**
     * Called when the panel is rendered (each frame)
     *
     * @param graphics The graphics object to render things to.
     */
    public abstract void onRender(TextGraphics graphics);
}
