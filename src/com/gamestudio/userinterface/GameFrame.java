package com.gamestudio.userinterface;

import com.gamestudio.effect.CacheDataLoader;

import java.awt.*;
import java.io.IOException;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 650;

    GamePanel gamePanel;

    public GameFrame(){

        super("Mega Man java game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension solution = toolkit.getScreenSize();

        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setBounds((solution.width - SCREEN_WIDTH)/2, (solution.height - SCREEN_HEIGHT)/2, SCREEN_WIDTH, SCREEN_HEIGHT);

        gamePanel = new GamePanel();
        addKeyListener(gamePanel);
        addMouseMotionListener(gamePanel);
        addMouseListener(gamePanel);
        add(gamePanel);
    }

    public void startGame(){
        gamePanel.startGame();
        this.setVisible(true);
    }

    public static void main(String[] arg){
        GameFrame gameFrame = new GameFrame();
        gameFrame.startGame();
        // add sth
    }

}
