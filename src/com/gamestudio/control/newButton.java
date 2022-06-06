package com.gamestudio.control;

import com.gamestudio.state.State;

import javax.swing.*;
import java.awt.*;

public abstract class newButton {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int imageWidth;
    protected int imageHeight;
    protected String activeImageLink;
    protected String inactiveImageLink;
    protected int buttonId;

    public newButton(int posX, int posY, String activeImageLink, String inactiveImageLink, int buttonId) {
        this.inactiveImageLink = inactiveImageLink;
        this.activeImageLink = activeImageLink;
        this.posX = posX;
        this.posY = posY;
        this.buttonId = buttonId;
        ImageIcon icon = new ImageIcon(activeImageLink);
        imageWidth = icon.getIconWidth();
        imageHeight = icon.getIconHeight();
    }

    public abstract boolean isInButton(int x, int y);

    public abstract void draw(Graphics g);

}
