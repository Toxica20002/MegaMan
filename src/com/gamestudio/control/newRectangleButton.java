package com.gamestudio.control;

import java.awt.*;

import static com.gamestudio.userinterface.GamePanel.*;

public class newRectangleButton extends newButton{

    public newRectangleButton(int posX, int posY, String activeImageLink, String inactiveImageLink) {
        super(posX, posY, activeImageLink, inactiveImageLink);
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
