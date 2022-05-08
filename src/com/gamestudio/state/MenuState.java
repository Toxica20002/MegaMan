/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.control.Button;
import com.gamestudio.control.RectangleButton;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;
import java.awt.Color;
import java.awt.Graphics;
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
	private boolean canContinueGame = false;
        
    public MenuState(GamePanel gamePanel) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
        buttons = new Button[NUMBER_OF_BUTTON];
        buttons[0] = new RectangleButton("ROUND ONE", 300, 100, 150, 40, 15, 25, Color.ORANGE);
		buttons[0].setHoverBgColor(Color.BLUE);
		buttons[0].setPressedBgColor(Color.GREEN);

		buttons[1] = new RectangleButton("ROUND TWO", 300, 160, 150, 40, 15, 25, Color.ORANGE);
		buttons[1].setHoverBgColor(Color.BLUE);
		buttons[1].setPressedBgColor(Color.GREEN);
		

		buttons[2] = new RectangleButton("EXIT", 300, 220, 150, 40, 15, 25, Color.ORANGE);
		buttons[2].setHoverBgColor(Color.BLUE);
		buttons[2].setPressedBgColor(Color.GREEN);
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
        graphicsPaint.setColor(Color.CYAN);
		graphicsPaint.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
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
        switch(code) {
            case KeyEvent.VK_DOWN:
                buttonSelected++;
                buttonSelected %= 3;
                break;
            case KeyEvent.VK_UP:
                buttonSelected--;
                if (buttonSelected < 0){
                    buttonSelected += 3;
                };
                break;
            case KeyEvent.VK_ENTER:
                actionMenu();
                break;
        }
    }

    @Override
    public void setReleasedButton(int code) {}
    
    private void actionMenu() {
        switch(buttonSelected) {
            case 0:
                gamePanel.setState(new GameRoundOneState(gamePanel));
                break;

            case 1:
                gamePanel.setState(new GameRoundTwoState(gamePanel));
                break;

            case 2:
                System.exit(0);
                break;
        }
    }
}
