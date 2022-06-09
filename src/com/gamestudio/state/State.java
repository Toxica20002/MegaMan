/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.state;

import com.gamestudio.userinterface.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author phamn
 */
public abstract class State {
    
    protected GamePanel gamePanel;
    protected Font pixel;
    public State(GamePanel gamePanel) {
       this.gamePanel = gamePanel;
        try{
            pixel = Font.createFont(Font.TRUETYPE_FONT, new File("data\\FFFFORWA.TTF")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data\\FFFFORWA.TTF")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public abstract void Update();
    public abstract void Render();
    public abstract BufferedImage getBufferedImage();
    
    public abstract void setPressedButton(int code);
    public abstract void setReleasedButton(int code);
    public abstract void setPressedMouse(int code);
}
