package com.gamestudio.gameobject;

import com.gamestudio.effect.Animation;
import com.gamestudio.effect.CacheDataLoader;
import com.gamestudio.state.GameWorldState;

import java.awt.*;

public class BubbleBullet extends Bullet{
    private Animation forwardBulletAnim;
    private Animation backBulletAnim;

    private long startTimeForChangeSpeedY;

    public BubbleBullet(float x, float y, GameWorldState gameWorld) {

        super(x, y, 30, 30, 1.0f, 10, gameWorld);

        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("BubbleBullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("BubbleBullet");
        forwardBulletAnim.flipAllImage();



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
            if(getSpeedY() > 0){
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            if(getSpeedY() > 0){
                backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setSpeedY(getSpeedX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setSpeedY(-getSpeedX());
        }else {
            setSpeedY(0);

        }
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub
        super.Update();

        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
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
