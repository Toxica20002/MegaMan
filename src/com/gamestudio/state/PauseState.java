package com.gamestudio.state;

import com.gamestudio.control.Button;
import com.gamestudio.control.RectangleButton;
import com.gamestudio.control.newButton;
import com.gamestudio.control.newRectangleButton;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.gamestudio.userinterface.GamePanel.*;

public class PauseState extends State{
    protected final int NUMBER_OF_BUTTON = 5;
    protected final GameWorldState previous;
    protected BufferedImage bufferedImage;
    GamePanel gamePanel;
    Graphics graphicsPaint;
    private final newButton[] buttons;

    public PauseState(GamePanel gamePanel, GameWorldState previous)
    {
        super(gamePanel);
        statePanel = outGame;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.previous= previous;
        this.gamePanel= gamePanel;

        buttons = new newButton[NUMBER_OF_BUTTON];
        buttons[0] = new newRectangleButton(758, 250, "data\\resume_active.png", "data\\resume_inactive.png");
        buttons[1] = new newRectangleButton(758, 350, "data\\minus_active.png", "data\\minus_inactive.png");
        buttons[2] = new newRectangleButton(958, 350, "data\\plus_active.png", "data\\plus_inactive.png");
        buttons[3] = new newRectangleButton(758, 450, "data\\quit_active.png", "data\\quit_inactive.png");
        buttons[4] = new newRectangleButton(1039, 350, "data\\mute_active.png", "data\\mute_inactive.png");

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
        Image image = Toolkit.getDefaultToolkit().getImage("data\\Pause.png");
        graphicsPaint.drawImage(image, 0, 0, null);

        graphicsPaint.setColor(Color.CYAN);
        graphicsPaint.fillRect(838, 350, 120, 80);
        graphicsPaint.fillRect(540, 350, 200, 80);

        for (newButton bt : buttons) {
            bt.draw(graphicsPaint);
        }
        graphicsPaint.setColor(Color.black);
        graphicsPaint.setFont(pixel);
        graphicsPaint.drawString(String.valueOf((int)Math.ceil(previous.bgMusic.getVolume() * 5)), 888, 410);
        graphicsPaint.drawString("VOLUME: ", 560, 410);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
    }

    @Override
    public void setReleasedButton(int code) {

    }

    @Override
    public void setPressedMouse(int code) {
        if(code == MouseEvent.BUTTON1) {
            actionMenu();
        }
    }

    private void actionMenu() {
        if(buttons[0].isInButton(mouseX, mouseY)){
            previous.setState(previous.getLastState());
            previous.setLastState(previous.getState());
            gamePanel.setState(previous);
        }
        else if(buttons[3].isInButton(mouseX, mouseY)){
            previous.setState(GameWorldState.GAMEOVER);
            previous.setLastState(previous.getState());
            gamePanel.setState(previous);
        }
        else if(buttons[1].isInButton(mouseX, mouseY)){
            if(previous.bgMusic.getVolume()-0.2f >= 0.0f)
            {
                previous.bgMusic.setVolume(previous.bgMusic.getVolume()-0.2f);
            }
            else
            {
                previous.bgMusic.setVolume(0.0f);
            }
            previous.bgMusic.stop();
            previous.bgMusic.play();
        }
        else if(buttons[2].isInButton(mouseX, mouseY)){
            if(previous.bgMusic.getVolume()+0.2f <= 1.0f)
            {
                previous.bgMusic.setVolume(previous.bgMusic.getVolume()+0.2f);
            }
            else
            {
                previous.bgMusic.setVolume(1.0f);
            }
            previous.bgMusic.stop();
            previous.bgMusic.play();
        }
        else if(buttons[4].isInButton(mouseX, mouseY)){
            previous.bgMusic.stop();
            previous.bgMusic.setVolume(0.0f);
            previous.bgMusic.play();
        }
    }
}
