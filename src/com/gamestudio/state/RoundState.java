/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.control.Button;
import com.gamestudio.control.MenuButton;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author phamn
 */

/**
 *  Dinh đã thêm
 *
 */
public class RoundState extends State {

    public final int NUMBER_OF_BUTTON = 3;
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;

    private Button[] buttons;
    private int buttonSelected = 0;

    public RoundState(GamePanel gamePanel) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        buttons = new Button[NUMBER_OF_BUTTON];
        buttons[0] = new MenuButton(100, 250, "data\\round1_active.png", "data\\round1_inactive.png");
        buttons[1] = new MenuButton(100, 350, "data\\round2_active.png", "data\\round2_inactive.png");
        buttons[2] = new MenuButton(100, 450, "data\\return_active.png", "data\\return_inactive.png");

    }

    @Override
    public void Update() {
        for(int i = 0;i<NUMBER_OF_BUTTON;i++) {
            if(i == buttonSelected) {
                buttons[i].setState(Button.HOVER);
            } else {
                buttons[i].setState(Button.NONE);
            }
        }
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
        Image image = Toolkit.getDefaultToolkit().getImage("data\\menu_background.gif");
        graphicsPaint.drawImage(image, 0, 0, null);
        for (Button bt : buttons) {
            bt.draw(graphicsPaint);
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_DOWN -> {
                buttonSelected++;
                buttonSelected %= 3;
            }
            case KeyEvent.VK_UP -> {
                buttonSelected--;
                if (buttonSelected < 0) {
                    buttonSelected += 3;
                }
            }
            case KeyEvent.VK_ENTER -> actionMenu();
        }
    }

    @Override
    public void setReleasedButton(int code) {}

    private void actionMenu() {
        switch (buttonSelected) {
            case 0 -> gamePanel.setState(new GameRoundOneState(gamePanel));
            case 1 -> gamePanel.setState(new GameRoundTwoState(gamePanel));
            case 2 -> gamePanel.setState(new MenuState(gamePanel));
        }
    }
}
