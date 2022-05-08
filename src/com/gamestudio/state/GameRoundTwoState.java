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

        ParticularObject smallRedGun = new SmallRedGun(1600, 180, this);
        smallRedGun.setDirection(ParticularObject.LEFT_DIR);
        smallRedGun.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(smallRedGun);

        ParticularObject darkraise = new DarkRaise(2000, 200, this);
        darkraise.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkraise);


        ParticularObject robotR = new RobotR(900, 400, this);
        robotR.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(robotR);


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


    }


}
