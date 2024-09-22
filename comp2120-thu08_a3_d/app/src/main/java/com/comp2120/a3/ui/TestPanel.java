package com.comp2120.a3.ui;

import com.comp2120.a3.system.InputSystem;
import com.comp2120.a3.system.RenderSystem;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

public final class TestPanel extends PanelBase {
    public void onOpen() {

    }

    public void onClose() {

    }

    public void onRender(TextGraphics graphics) {
        graphics.putString(0, 0, "TestPanel");
        graphics.putString(0, 1, "This is a test panel");
        graphics.putString(0, 2, "Frame: " + getEngine().getSystem(RenderSystem.class).getRenderedFrameCount());
        KeyStroke input = getEngine().getSystem(InputSystem.class).getLastInput();
        if (input != null) {
            graphics.putString(0, 3, "Last Input: " + input);
        }
    }
}
