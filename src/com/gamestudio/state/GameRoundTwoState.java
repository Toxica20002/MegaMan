package com.gamestudio.state;

import com.gamestudio.effect.CacheDataLoader;
import com.gamestudio.effect.FrameImage;
import com.gamestudio.gameobject.*;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameRoundTwoState extends GameWorldState {

    public static final int finalBossX = 3600;

    public AudioClip bgMusic;

    public GameRoundTwoState(GamePanel gamePanel){
        super(gamePanel);

        round = 2;

        physicalMap = new PhysicalMap(0, 0, round, this);
        backgroundMap = new BackgroundMap(0, 0, round, this);

        texts1 = new String[1];
        texts1[0] = "Hello\nHello....";
        textTutorial = texts1[0];

        bossY = 1210;

        gateY = 39;
        gateX = 120;
        gateW = 1;
        gateH = 4;

        tileOfGate = 17;

        tilesGate = new int[gateW][gateH];
        for (int i = 0; i < gateW; i++) {
            for (int j = 0; j < gateH; j++) {
                tilesGate[i][j] = backgroundMap.map[round - 1][gateY + j][gateX + i];
            }
        }

    }

    @Override
    public void initEnemies() {

        ParticularObject smallRedGun = new SmallRedGun(600, 180, this);
        smallRedGun.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun);

        ParticularObject smallRedGun2 = new SmallRedGun(3300, 1000, this);
        smallRedGun2.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun2);

        ParticularObject darkraise = new DarkRaise(2700, 820, this);
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);


        ParticularObject darkraise2 = new DarkRaise(900, 650, this);
        darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise2);

        ParticularObject robotR2 = new RobotR(700, 1175, this);
        robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR2);


        ParticularObject redeye4 = new RedEyeDevil(2000, 1120, this);
        redeye4.setDirection(ParticularObject.LEFT_DIR);
        redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);


//        ParticularObject darkraise3 = new DarkRaise(750, 650, this);
//        darkraise3.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(darkraise3);

        ParticularObject robotR3 = new RobotR(2900, 940, this);
        robotR3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR3);


    }
    public void Update(){

        switch(state){
            case INIT_GAME:

                break;
            case TUTORIAL:
                TutorialUpdate();

                break;
            case GAMEPLAY:
                particularObjectManager.UpdateObjects();
                bulletManager.UpdateObjects();

                physicalMap.Update();
                camera.Update();


                if(megaMan.getPosX() > finalBossX && finalbossTrigger){
                    finalbossTrigger = false;
                    switchState(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;

                    boss = new FinalBoss2(finalBossX + 700, bossY, this);
                    boss.setTeamType(ParticularObject.ENEMY_TEAM);
                    boss.setDirection(ParticularObject.LEFT_DIR);
                    particularObjectManager.addObject(boss);

                }

                if(megaMan.getState() == ParticularObject.DEATH){
                    numberOfLife --;
                    if(numberOfLife > 0){
                        megaMan.setBlood(100);
                        megaMan.setPosY(megaMan.getPosY() - 50);
                        megaMan.setState(ParticularObject.NOBEHURT);
                        particularObjectManager.addObject(megaMan);
                    }else{
                        switchState(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if(!finalbossTrigger && boss.getState() == ParticularObject.DEATH)
                    switchState(GAMEWIN);

                break;
            case GAMEOVER:

                break;
            case GAMEWIN:

                break;
        }


    }


}
