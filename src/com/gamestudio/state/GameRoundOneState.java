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

public class GameRoundOneState extends GameWorldState {

    public static final int finalBossX = 3600;

    public AudioClip bgMusic;

    public GameRoundOneState(GamePanel gamePanel){
        super(gamePanel);

        round = 1;

        physicalMap = new PhysicalMap(0, 0, round, this);
        backgroundMap = new BackgroundMap(0, 0, round, this);

        texts1 = new String[4];
        texts1[0] = "We are heroes, and our mission is protecting our Home\nEarth....";
        texts1[1] = "There was a Monster from University on Earth in 10 years\n"
                + "and we lived in the scare in that 10 years....";
        texts1[2] = "Now is the time for us, kill it and get freedom!....";
        texts1[3] = "      LET'S GO!.....";
        textTutorial = texts1[0];

        bossY = 460;


        gateY = 14;
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
        ParticularObject redeye = new RedEyeDevil(1250, 410, this);
        redeye.setDirection(ParticularObject.LEFT_DIR);
        redeye.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye);

//        ParticularObject smallRedGun = new SmallRedGun(1600, 180, this);
//        smallRedGun.setDirection(ParticularObject.LEFT_DIR);
//        smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(smallRedGun);

        ParticularObject darkraise = new DarkRaise(2000, 200, this);
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);

        ParticularObject darkraise2 = new DarkRaise(2800, 350, this);
        darkraise2.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise2);

//        ParticularObject robotR = new RobotR(900, 400, this);
//        robotR.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(robotR);

//        ParticularObject robotR2 = new RobotR(3400, 350, this);
//        robotR2.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(robotR2);


//        ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
//        redeye2.setDirection(ParticularObject.LEFT_DIR);
//        redeye2.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(redeye2);

        ParticularObject redeye3 = new RedEyeDevil(3450, 500, this);
        redeye3.setDirection(ParticularObject.LEFT_DIR);
        redeye3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye3);

        ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
        redeye4.setDirection(ParticularObject.RIGHT_DIR);
        redeye4.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redeye4);


        ParticularObject darkraise3 = new DarkRaise(750, 650, this);
        darkraise3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise3);

        ParticularObject robotR3 = new RobotR(1500, 1150, this);
        robotR3.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR3);


//
//        ParticularObject smallRedGun2 = new SmallRedGun(1700, 980, this);
//        smallRedGun2.setDirection(ParticularObject.LEFT_DIR);
//        smallRedGun2.setTeamType(ParticularObject.ENEMY_TEAM);
//        particularObjectManager.addObject(smallRedGun2);
    }
    public void Update(){
        super.Update();
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

                    boss = new FinalBoss(finalBossX + 700, bossY, this);
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
