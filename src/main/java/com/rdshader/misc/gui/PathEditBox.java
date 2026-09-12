package com.rdshader.misc.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PathEditBox extends EditBox {
    private final Consumer<String> listener;

    public PathEditBox(Font font, int width, int height, Component message, Consumer<String> listener) {
        super(font, width, height, message);
        this.listener = listener;
    }

    public void appendPath(Component path, Predicate<String> predicate) {
        String text = getValue();
        if (predicate.test(text)) {
            if (text.endsWith("/")) {
                setValue(text + path.getString());
            } else {
                setValue(text + "/" + path.getString());
            }
            refresh();
        }
    }

    public void refresh() {
        listener.accept(getValue());
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_ENTER) {
            refresh();
            return true;
        }
        return super.keyPressed(event);
    }
}
