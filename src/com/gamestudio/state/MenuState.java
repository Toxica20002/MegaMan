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
public class MenuState extends State {

    public final int NUMBER_OF_BUTTON = 3;
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;

    private Button[] buttons;
    private int buttonSelected = 0;

    public MenuState(GamePanel gamePanel) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        buttons = new Button[NUMBER_OF_BUTTON];
        buttons[0] = new MenuButton(200, 250, "data\\start_active.png", "data\\start_inactive.png");
        buttons[1] = new MenuButton(200, 350, "data\\rules_active.png", "data\\rules_inactive.png");
        buttons[2] = new MenuButton(200, 450, "data\\quit_active.png", "data\\quit_inactive.png");
        //Dinh đã đổi
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
        Image image = Toolkit.getDefaultToolkit().getImage("data\\menu_bg.gif");
        graphicsPaint.drawImage(image, 0, 0, null);
        //Dinh đã đổi

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
            case 0 -> gamePanel.setState(new RoundState(gamePanel));
            case 1 -> gamePanel.setState(new RuleState(gamePanel));
            case 2 -> System.exit(0);
        }
        //Dinh đã đổi
    }
}
