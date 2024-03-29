package com.gamestudio.gameobject;

import com.gamestudio.state.GameWorldState;
import com.gamestudio.effect.Animation;
import com.gamestudio.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class YellowFlowerBullet extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public YellowFlowerBullet(float x, float y, GameWorldState gameWorld) {
            super(x, y, 30, 30, 1.0f, 10, gameWorld);
            forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
            backBulletAnim = CacheDataLoader.getInstance().getAnimation("yellow_flower_bullet");
            backBulletAnim.flipAllImage();
    }

    
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
            // TODO Auto-generated method stub
            return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
            // TODO Auto-generated method stub
        if(getSpeedX() > 0){          
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
            // TODO Auto-generated method stub
        super.Update();
    }

    @Override
    public double getVolume() {
        return 0;
    }

    @Override
    public void setVolume(double deltaVolume) {

    }

    @Override
    public void attack() {}

}
