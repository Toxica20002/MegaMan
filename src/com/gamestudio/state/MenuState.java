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

public class MenuState extends State {

    protected final int NUMBER_OF_BUTTON = 3;
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;
    private final newButton[] buttons;
    protected long startQuitTime;
    protected boolean quitState = false;
    public MenuState(GamePanel gamePanel) {
        super(gamePanel);
        statePanel = outGame;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        buttons = new newButton[NUMBER_OF_BUTTON];
        buttons[0] = new newRectangleButton(200, 250, "data\\start_active.png", "data\\start_inactive.png");
        buttons[1] = new newRectangleButton(200, 350, "data\\rules_active.png", "data\\rules_inactive.png");
        buttons[2] = new newRectangleButton(200, 450, "data\\quit_active.png", "data\\quit_inactive.png");
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
        if(!quitState) {
            Image image = Toolkit.getDefaultToolkit().getImage("data\\menu_bg.gif");
            graphicsPaint.drawImage(image, 0, 0, null);


            for (newButton bt : buttons) {
                bt.draw(graphicsPaint);
            }
        }
        else {
            if(System.currentTimeMillis()-startQuitTime >= 4000)
                System.exit(0);
            Image image = Toolkit.getDefaultToolkit().getImage("data\\thanks.gif");
            graphicsPaint.drawImage(image, 0, 0, null);
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
        if(code == MouseEvent.BUTTON1) {
            actionMenu();
        }
    }

    private void actionMenu() {
        if(buttons[0].isInButton(mouseX, mouseY))
            gamePanel.setState(new RoundState(gamePanel));
        else if(buttons[1].isInButton(mouseX, mouseY))
            gamePanel.setState(new RuleState(gamePanel));
        else if(buttons[2].isInButton(mouseX, mouseY)) {
            startQuitTime = System.currentTimeMillis();
            quitState = true;
        }
        //Dinh đã đổi
    }
}
