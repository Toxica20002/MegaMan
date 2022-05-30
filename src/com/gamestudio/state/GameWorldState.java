package com.gamestudio.state;

import com.gamestudio.effect.CacheDataLoader;
import com.gamestudio.effect.FrameImage;
import com.gamestudio.gameobject.BackgroundMap;
import com.gamestudio.gameobject.BulletManager;
import com.gamestudio.gameobject.Camera;
import com.gamestudio.gameobject.DarkRaise;
import com.gamestudio.gameobject.FinalBoss;
import com.gamestudio.gameobject.MegaMan;
import com.gamestudio.gameobject.ParticularObject;
import com.gamestudio.gameobject.ParticularObjectManager;
import com.gamestudio.gameobject.PhysicalMap;
import com.gamestudio.gameobject.RedEyeDevil;
import com.gamestudio.gameobject.RobotR;
import com.gamestudio.gameobject.SmallRedGun;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class GameWorldState extends State {
	public int round;

    protected BufferedImage bufferedImage;
    private int lastState;

    public ParticularObjectManager particularObjectManager;
    public BulletManager bulletManager;

    public MegaMan megaMan;
   
    public PhysicalMap physicalMap;
    public BackgroundMap backgroundMap;
    public Camera camera;

    public static final int finalBossX = 3600;
    
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0;
    public String[] texts1;

    public String textTutorial;
    public int currentSize = 1;
    
    protected boolean finalbossTrigger = true;
    ParticularObject boss;
    int bossY;
    
    FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");
    
    
    protected int numberOfLife = 3;

    protected int gateX;
    protected int gateY;
    protected int gateW;
    protected int gateH;
    protected int[][] tilesGate;
    protected int tileOfGate;
    
    public AudioClip bgMusic;

    public GameWorldState(GamePanel gamePanel){
            super(gamePanel);

        
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        megaMan = new MegaMan(400, 400, this);
        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
        bulletManager = new BulletManager(this);
        
        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(megaMan);
        
        initEnemies();

        bgMusic = CacheDataLoader.getInstance().getSound("bgmusic");
        
    }
    
    public abstract void initEnemies();

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
            case INTROGAME:
                
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY+=4;
                    }else storyTutorial ++;
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
            case MEETFINALBOSS:
                if(storyTutorial == 0){
                    if(openIntroGameY >= 450) {
                        openIntroGameY-=1;
                    }
                    if(camera.getPosX() < finalBossX){
                        camera.setPosX(camera.getPosX() + 2);
                    }
                    
                    if(megaMan.getPosX() < finalBossX + 150){
                        megaMan.setDirection(ParticularObject.RIGHT_DIR);
                        megaMan.run();
                        megaMan.Update();
                    }else{
                        megaMan.stopRun();
                    }
                    
                    if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && megaMan.getPosX() >= finalBossX + 150){ 
                        camera.lock();
                        storyTutorial++;
                        megaMan.stopRun();

                        for (int i = 0; i < gateW; i++) {
                            for (int j = 0; j < gateH; j++) {
                                backgroundMap.map[round - 1][gateY + j][gateX + i] = tileOfGate;
                                physicalMap.phys_map[round - 1][gateY + j][gateX + i] = 1;

                            }
                        }
//                        physicalMap.phys_map[0][14][120] = 1;
//                        physicalMap.phys_map[0][15][120] = 1;
//                        physicalMap.phys_map[0][16][120] = 1;
//                        physicalMap.phys_map[0][17][120] = 1;
//
//                        backgroundMap.map[0][14][120] = 17;
//                        backgroundMap.map[0][15][120] = 17;
//                        backgroundMap.map[0][16][120] = 17;
//                        backgroundMap.map[0][17][120] = 17;
                    }
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
        }
    }
    
    protected void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
    }
    
    protected void TutorialRender(Graphics2D g2){
        switch(tutorialState){
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                int y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                
                if(storyTutorial >= 1){
                    g2.drawImage(avatar.getImage(), 600, 350, null);
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 290, 480);
                }
                
                break;
            case MEETFINALBOSS:
                yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                break;
        }
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

    public void Render(){

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if(g2!=null){

            // NOTE: two lines below make the error splash white screen....
            // need to remove this line
            //g2.setColor(Color.WHITE);
            //g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            
            
            //physicalMap.draw(g2);
            
            switch(state){
                case INIT_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    if (round == 1) {
                        g2.drawString("ROUND 1", GameFrame.SCREEN_WIDTH / 2 - 30, GameFrame.SCREEN_HEIGHT / 2 - 10);
                    } else if (round == 2) {
                        g2.drawString("ROUND 2", GameFrame.SCREEN_WIDTH / 2 - 30, GameFrame.SCREEN_HEIGHT / 2 - 10);
                    }

                    break;
                case PAUSEGAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(300, 260, 500, 70);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    break;
                case TUTORIAL:
                    backgroundMap.draw(g2);
                    if(tutorialState == MEETFINALBOSS){
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    backgroundMap.draw(g2);
                    particularObjectManager.draw(g2);  
                    bulletManager.draw(g2);
                    
                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, megaMan.getBlood(), 20);
                    
                    for(int i = 0; i < numberOfLife; i++){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i*40, 18, null);
                    }
                    
                    
                    if(state == GAMEWIN){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }
                    
                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;

            }
            

        }

    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
       switch(code){
            
            case KeyEvent.VK_DOWN:
                megaMan.dick();
                break;
                
            case KeyEvent.VK_RIGHT:
                megaMan.setDirection(megaMan.RIGHT_DIR);
                megaMan.run();
                break;
                
            case KeyEvent.VK_LEFT:
                megaMan.setDirection(megaMan.LEFT_DIR);
                megaMan.run();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GameWorldState.INIT_GAME){
                    if(previousState == GameWorldState.GAMEPLAY)
                        switchState(GameWorldState.GAMEPLAY);
                    else switchState(GameWorldState.TUTORIAL);
                    
                    bgMusic.loop();
                }
                if(state == GameWorldState.TUTORIAL && storyTutorial >= 1){
                    if(storyTutorial <= texts1.length - 1){
                        storyTutorial ++;
                        currentSize = 1;
                        textTutorial = texts1[storyTutorial-1];
                    }else{
                        switchState(GameWorldState.GAMEPLAY);
                    }

                    // for meeting boss tutorial
                    if(tutorialState == GameWorldState.MEETFINALBOSS){
                        switchState(GameWorldState.GAMEPLAY);
                    }
                }
                break;
                
            case KeyEvent.VK_SPACE:
                megaMan.jump();
                break;
                
            case KeyEvent.VK_A:
                megaMan.attack();
                break;
                
        }}

    @Override
    public void setReleasedButton(int code) {
        switch(code){
            
            case KeyEvent.VK_UP:
                
                break;
                
            case KeyEvent.VK_DOWN:
                megaMan.standUp();
                break;
                
            case KeyEvent.VK_RIGHT:
                if(megaMan.getSpeedX() > 0)
                    megaMan.stopRun();
                break;
                
            case KeyEvent.VK_LEFT:
                if(megaMan.getSpeedX() < 0)
                    megaMan.stopRun();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    for (int i = 0; i < gateW; i++) {
                        for (int j = 0; j < gateH; j++) {
                            backgroundMap.map[round - 1][gateY + j][gateX + i] = tilesGate[i][j];
                            physicalMap.phys_map[round - 1][gateY + j][gateX + i] = 0;
                        }
                    }

                    if (state == GAMEWIN) {
                        GameWorldState newState;
                        if (round == 1) {
                            newState = new GameRoundTwoState(gamePanel);

                            gamePanel.setState(newState);
                        } else {
                            gamePanel.setState(new MenuState(gamePanel));
                        }
                    } else {
                        gamePanel.setState(new MenuState(gamePanel));
                    }




                } else if(state == PAUSEGAME) {
                    state = lastState;
                }
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
                
            case KeyEvent.VK_A:
                
                break;
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
                
        }}
	
}
