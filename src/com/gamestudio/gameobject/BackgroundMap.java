
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import com.gamestudio.effect.CacheDataLoader;
import com.gamestudio.userinterface.GameFrame;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author phamn
 */
public class BackgroundMap extends GameObject {

    public int[][][] map;
    private int tileSize;
    private int round;
    
    public BackgroundMap(float x, float y, int round, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        map = CacheDataLoader.getInstance().getBackgroundMap();
        tileSize = 30;
        this.round = round;
    }

    @Override
    public void Update() {}

    @Override
    public double getVolume() {
        return 0;
    }

    @Override
    public void setVolume(double deltaVolume) {

    }

    public void draw(Graphics2D g2){
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.RED);
        for(int i = 0;i< map[round - 1].length;i++)
            for(int j = 0;j<map[round - 1][0].length;j++)
                if(map[round - 1][i][j]!=0 && j*tileSize - camera.getPosX() > -30 && j*tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
                        && i*tileSize - camera.getPosY() > -30 && i*tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT){

                    g2.scale(1, 1);
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+ round + "_"+map[round - 1][i][j]).getImage(), (int) getPosX() + j*tileSize - (int) camera.getPosX(),
                        (int) getPosY() + i*tileSize - (int) camera.getPosY(), null);
                    //g2.scale(1, 1);
                }
        
    }
    
}
