package com.gamestudio.state;

import com.gamestudio.control.Button;
import com.gamestudio.control.RectangleButton;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static com.gamestudio.userinterface.GamePanel.*;

public class PauseState extends State{
    private final GameWorldState previous;
    public final int NUMBER_OF_BUTTON = 5;
    protected BufferedImage bufferedImage;
    private int buttonSelected = 0;
    GamePanel gamePanel;
    private final Button[] buttons;

    public PauseState(GamePanel gamePanel, GameWorldState previous)
    {
        super(gamePanel);
        statePanel = outGame;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.previous= previous;
        this.gamePanel= gamePanel;
        buttons= new Button[NUMBER_OF_BUTTON];

        buttons[0]= new RectangleButton("RESUME", 350, 100,150,40,15,25, Color.ORANGE);
        buttons[0].setHoverBgColor(Color.YELLOW);
        buttons[0].setPressedBgColor(Color.GREEN);

        buttons[1]= new RectangleButton("MENU", 350, 160,150,40,15,25, Color.ORANGE);
        buttons[1].setHoverBgColor(Color.YELLOW);
        buttons[1].setPressedBgColor(Color.GREEN);

        buttons[2]= new RectangleButton("VOLUME UP", 150, 240,150,40,15,25, Color.ORANGE);
        buttons[2].setHoverBgColor(Color.YELLOW);
        buttons[2].setPressedBgColor(Color.GREEN);

        buttons[3]= new RectangleButton("MUTE", 350, 240,150,40,15,25, Color.ORANGE);
        buttons[3].setHoverBgColor(Color.YELLOW);
        buttons[3].setPressedBgColor(Color.GREEN);

        buttons[4]= new RectangleButton("VOLUME DOWN", 550, 240,150,40,15,25, Color.ORANGE);
        buttons[4].setHoverBgColor(Color.YELLOW);
        buttons[4].setPressedBgColor(Color.GREEN);

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
        Graphics2D g2= (Graphics2D) bufferedImage.getGraphics();

        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        for (Button bt : buttons) {
            bt.draw(g2);
        }
        System.out.println(previous.bgMusic.getVolume());

    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch(code) {
            case KeyEvent.VK_DOWN:
                if(buttonSelected <2)
                {
                    buttonSelected++;
                    buttonSelected %= 3;
                }
                break;
            case KeyEvent.VK_UP:
                buttonSelected--;
                if (buttonSelected < 0){
                    buttonSelected += 3;
                }
                buttonSelected=Math.min(1,buttonSelected);
                break;
            case KeyEvent.VK_LEFT:
                if(buttonSelected>=2 && buttonSelected<=4)
                {
                    if(buttonSelected-1 >=2)    buttonSelected--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(buttonSelected>=2 && buttonSelected<=4)
                {
                    if(buttonSelected+1 <=4)    buttonSelected++;
                }
                break;
            case KeyEvent.VK_ENTER:
                actionMenu();
                break;

        }
        System.out.println(buttonSelected);
    }

    @Override
    public void setReleasedButton(int code) {

    }

    @Override
    public void setPressedMouse(int code) {

    }

    private void actionMenu() {
        switch(buttonSelected) {
            case 0: // resume
                previous.setState(previous.getLastState());
                previous.setLastState(previous.getState());
                gamePanel.setState(previous);
                break;

            case 1: // menu
                previous.setState(GameWorldState.GAMEOVER);
                previous.setLastState(previous.getState());
                gamePanel.setState(previous);
                break;

            case 2: // add volume
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
                break;
            case 3:// mute
                previous.bgMusic.stop();
                previous.bgMusic.setVolume(0.0f);
                previous.bgMusic.play();
            case 4: // minus volume
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
    }
}
