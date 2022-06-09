/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import com.gamestudio.effect.CacheDataLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author phamn
 */
public class PhysicalMap extends GameObject{

    public int[][][] phys_map;
    private int tileSize;
    private int round;
    
    public PhysicalMap(float x, float y, int round, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        this.tileSize = 30;
        this.round = round;
        phys_map = CacheDataLoader.getInstance().getPhysicalMap();
    }
    
    public int getTileSize(){
        return tileSize;
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


    public Rectangle haveCollisionWithTop(Rectangle rect){

        int posX1 = rect.x/tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width)/tileSize;
        posX2 += 2;

        //int posY = (rect.y + rect.height)/tileSize;
        int posY = rect.y/tileSize;

        if(posX1 < 0) posX1 = 0;
        
        if(posX2 >= phys_map[round - 1][0].length) posX2 = phys_map[round - 1][0].length - 1;
        
        for(int y = posY; y >= 0; y--){
            for(int x = posX1; x <= posX2; x++){
                
                if(phys_map[round - 1][y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
    
    public Rectangle haveCollisionWithLand(Rectangle rect){

        int posX1 = rect.x/tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width)/tileSize;
        posX2 += 2;

        int posY = (rect.y + rect.height)/tileSize;

        if(posX1 < 0) posX1 = 0;
        
        if(posX2 >= phys_map[round - 1][0].length) posX2 = phys_map[round - 1][0].length - 1;
        for(int y = posY; y < phys_map[round - 1].length;y++){
            for(int x = posX1; x <= posX2; x++){
                
                if(phys_map[round - 1][y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
    public Rectangle haveCollisionWithRightWall(Rectangle rect){
   
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 + 3;
        if(posX2 >= phys_map[round - 1][0].length) posX2 = phys_map[round - 1][0].length - 1;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= phys_map[round - 1].length) posY2 = phys_map[round - 1].length - 1;
        
        
        for(int x = posX1; x <= posX2; x++){
            for(int y = posY1; y <= posY2;y++){
                if(phys_map[round - 1][y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }
    
    public Rectangle haveCollisionWithLeftWall(Rectangle rect){
        
   
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 - 3;
        if(posX2 < 0) posX2 = 0;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= phys_map[round - 1].length) posY2 = phys_map[round - 1].length - 1;
        
        
        for(int x = posX1; x >= posX2; x--){
            for(int y = posY1; y <= posY2;y++){
                if(phys_map[round - 1][y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }
    
    public void draw(Graphics2D g2){
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.GRAY);
        for(int i = 0;i< phys_map[round - 1].length;i++)
            for(int j = 0;j<phys_map[round - 1][0].length;j++)
                if(phys_map[round - 1][i][j]!=0) g2.fillRect((int) getPosX() + j*tileSize - (int) camera.getPosX(),
                        (int) getPosY() + i*tileSize - (int) camera.getPosY(), tileSize, tileSize);
        
    }
    
}
