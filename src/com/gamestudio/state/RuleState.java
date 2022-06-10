/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.control.newButton;
import com.gamestudio.control.newRectangleButton;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.gamestudio.userinterface.GamePanel.*;

public class RuleState extends State {

    protected final int NUMBER_OF_BUTTON = 1;
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;

    private newButton[] buttons;

    public RuleState(GamePanel gamePanel) {
        super(gamePanel);
        statePanel = outGame;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        buttons = new newButton[NUMBER_OF_BUTTON];
        buttons[0] = new newRectangleButton(50, 520, "data\\return_active.png", "data\\return_inactive.png");
    }

    @Override
    public void Update() {

    }

    @Override
    public void Render() {
        if(bufferedImage == null) {
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if(graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
            return;
        }

        Image image = Toolkit.getDefaultToolkit().getImage("data\\ruleScreen_background.png");
        graphicsPaint.drawImage(image, 0, 0, null);
        for (newButton bt : buttons) {
            bt.draw(graphicsPaint);
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {

    }

    @Override
    public void setReleasedButton(int code) {}

    @Override
    public void setPressedMouse(int code) {
        if(code == MouseEvent.BUTTON1)
            actionMenu();
    }

    private void actionMenu() {
        if(buttons[0].isInButton(mouseX, mouseY))
            gamePanel.setState(new MenuState(gamePanel));
    }
}
