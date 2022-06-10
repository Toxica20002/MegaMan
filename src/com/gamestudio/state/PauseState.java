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
    protected final int NUMBER_OF_BUTTON = 8;
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
        buttons[0] = new newRectangleButton(350, 520, "data\\resume_active.png", "data\\resume_inactive.png");
        buttons[1] = new newRectangleButton(758, 220, "data\\minus_active.png", "data\\minus_inactive.png");
        buttons[2] = new newRectangleButton(958, 220, "data\\plus_active.png", "data\\plus_inactive.png");
        buttons[3] = new newRectangleButton(1039, 220, "data\\mute_active.png", "data\\mute_inactive.png");
        buttons[4] = new newRectangleButton(758, 420, "data\\minus_active.png", "data\\minus_inactive.png");
        buttons[5] = new newRectangleButton(958, 420, "data\\plus_active.png", "data\\plus_inactive.png");
        buttons[6] = new newRectangleButton(1039, 420, "data\\mute_active.png", "data\\mute_inactive.png");
        buttons[7] = new newRectangleButton(760, 520, "data\\quit_active.png", "data\\quit_inactive.png");
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
        graphicsPaint.fillRect(838, 220, 120, 80);
        graphicsPaint.fillRect(758, 120, 400, 80);

        graphicsPaint.fillRect(838, 420, 120, 80);
        graphicsPaint.fillRect(758, 320, 400, 80);

        for (newButton bt : buttons) {
            bt.draw(graphicsPaint);
        }
        graphicsPaint.setColor(Color.black);
        graphicsPaint.setFont(pixel);
        graphicsPaint.drawString(String.valueOf((int)Math.round(previous.bgMusic.getVolume() * 5)), 888, 280);
        graphicsPaint.drawString("MUSIC VOLUME: ", 800, 180);

        graphicsPaint.drawString(String.valueOf((int)Math.round(previous.getParticularObjectManager().getParticularObjects().get(0).getVolume() * 5)), 888, 480);
        graphicsPaint.drawString("SFX VOLUME: ", 800, 380);
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
            statePanel = inGame;
            previous.setState(previous.getLastState());
            previous.setLastState(previous.getState());
            gamePanel.setState(previous);
        }
        else if(buttons[7].isInButton(mouseX, mouseY)){
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
        else if(buttons[3].isInButton(mouseX, mouseY)){
            previous.bgMusic.stop();
            previous.bgMusic.setVolume(0.0f);
            previous.bgMusic.play();
        }
        else if(buttons[4].isInButton(mouseX, mouseY)){
            previous.decreaseVolume();
        }
        else if(buttons[5].isInButton(mouseX, mouseY)){
            previous.increaseVolume();
        }
        else if(buttons[6].isInButton(mouseX, mouseY)){
            previous.muteVolume();
        }
    }
}
