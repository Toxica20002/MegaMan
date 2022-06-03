package com.gamestudio.control;

import java.awt.*;


/*
Dinh đã thêm
 */

public class MenuButton extends Button {

    public MenuButton(int posX, int posY, String inactiveImage, String activeImage) {
        super(posX, posY, activeImage, inactiveImage);
    }

    @Override
    public boolean isInButton(int x, int y) {
        return (enabled && x >= posX && x <= posX + width && y >= posY && y <= posY + height);
    }

    @Override
    public void draw(Graphics g) {
        Image image;
        if(enabled) {
            switch (state) {
                case NONE -> {
                    image = Toolkit.getDefaultToolkit().getImage(inactiveImage);
                    g.drawImage(image, posX, posY, null);
                }
                case HOVER -> {
                    image = Toolkit.getDefaultToolkit().getImage(activeImage);
                    g.drawImage(image, posX, posY, null);
                }
            }
        }
    }
}
