package com.gamestudio.userinterface;

import com.gamestudio.state.MenuState;
import com.gamestudio.state.State;

import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public static int mouseX;
    public static int mouseY;

    public static int statePanel = 1;

    public static int inGame = 0;
    public static int outGame = 1;
    public static int loadingPage = 2;

    State gameState;

    InputManager inputManager;
    
    Thread gameThread;

    public boolean isRunning = true;

    public GamePanel(){
        gameState = new MenuState(this);
        inputManager = new InputManager(gameState);
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        long previousTime = System.nanoTime();
        long currentTime;
        long sleepTime;

        long period = 1000000000/80;

        while(isRunning){
            gameState.Update();
            gameState.Render();
            repaint();
            currentTime = System.nanoTime();
            sleepTime = period - (currentTime - previousTime);
            try{
                    if(sleepTime > 0){
                        if(statePanel == inGame) Thread.sleep(sleepTime/1000000);
                        else if(statePanel == outGame) Thread.sleep(sleepTime/250000);
                        else if(statePanel == loadingPage) Thread.sleep(sleepTime/200000);
                    }
            }catch(Exception e){
                e.printStackTrace();
            }

            previousTime = System.nanoTime();
        }

    }

    public void paint(Graphics g){
        g.drawImage(gameState.getBufferedImage(), 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.setPressedButton(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.setReleasedButton(e.getKeyCode());
    }

    public void setState(State state) {
        gameState = state;
        inputManager.setState(state);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        inputManager.setPressedMouse(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
