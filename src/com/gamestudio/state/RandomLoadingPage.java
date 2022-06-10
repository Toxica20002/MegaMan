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
import java.awt.image.BufferedImage;
import java.util.Random;


public class RandomLoadingPage extends State {
    private BufferedImage bufferedImage;
    Graphics graphicsPaint;
    protected int round;

    public RandomLoadingPage(GamePanel gamePanel, int round) {
        super(gamePanel);
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.round = round;
    }

    @Override
    public void Update() {}

    @Override
    public void Render() {
        if(bufferedImage == null) {
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            return;
        }
        graphicsPaint = bufferedImage.getGraphics();
        if(graphicsPaint == null) {
            graphicsPaint = bufferedImage.getGraphics();
        }
        actionMenu();
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
    public void setPressedMouse(int code) {}

    private void actionMenu(){
        Random generator = new Random();
        int idLoadingPage = generator.nextInt(2);
        switch (idLoadingPage){
            case 0 -> gamePanel.setState(new FirstLoadingPage(gamePanel, round));
            case 1 -> gamePanel.setState(new SecondLoadingPage(gamePanel, round));
        }
    }
}
