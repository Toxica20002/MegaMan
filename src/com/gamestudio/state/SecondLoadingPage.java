/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static com.gamestudio.userinterface.GamePanel.*;
import static java.lang.Math.min;

public class SecondLoadingPage extends State {
    private BufferedImage bufferedImage;
    protected int round;
    protected long startLoadingPageTime;
    Graphics graphicsPaint;

    public SecondLoadingPage(GamePanel gamePanel, int round) {
        super(gamePanel);
        statePanel = loadingPage;
        startLoadingPageTime = System.currentTimeMillis();
        this.round = round;
        statePanel = loadingPage;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
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
            return;
        }
        Image image = Toolkit.getDefaultToolkit().getImage("data\\secondLoadingPage.png");
        graphicsPaint.drawImage(image, 0, 0, null);
        drawLoadingBar(graphicsPaint);
        if(System.currentTimeMillis()-startLoadingPageTime >= 5000)
            actionMenu();
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {}
    @Override
    public void setReleasedButton(int code) {}

    @Override
    public void setPressedMouse(int code) {}

    void drawLoadingBar(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0, 600, 1200, 25);
        g.setColor(Color.GREEN);

        long percentage = System.currentTimeMillis()-startLoadingPageTime;
        g.fillRect(0, 600, (int) (1200  * (percentage) / 4000), 25);
        g.setColor(Color.YELLOW);
        g.setFont(pixel);
        percentage = min((percentage*100)/4000, 100L);

        g.drawString("Loading...      " + percentage + "%", 0, 580);

    }
    private void actionMenu() {
        switch (round) {
            case 1 -> gamePanel.setState(new GameRoundOneState(gamePanel));
            case 2 -> gamePanel.setState(new GameRoundTwoState(gamePanel));
        }
    }
}
