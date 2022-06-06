package com.gamestudio.control;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.gamestudio.userinterface.GamePanel.*;

public class newRectangleButton extends newButton{

    public newRectangleButton(int posX, int posY, String activeImageLink, String inactiveImageLink, int buttonId) {
        super(posX, posY, activeImageLink, inactiveImageLink, buttonId);
    }

    @Override
    public boolean isInButton(int x, int y) {

        return (x >= posX && x <= posX + imageWidth && y >= posY && y <= posY + imageHeight);
    }

    @Override
    public void draw(Graphics g) {

        Image image;

        if(isInButton(mouseX, mouseY)){
            image = Toolkit.getDefaultToolkit().getImage(activeImageLink);
            g.drawImage(image, posX, posY, null);
        }
        else {
            image = Toolkit.getDefaultToolkit().getImage(inactiveImageLink);
            g.drawImage(image, posX, posY, null);
        }
    }
}
