package com.gamestudio.state;

import com.gamestudio.effect.CacheDataLoader;
import com.gamestudio.effect.FrameImage;
import com.gamestudio.gameobject.BackgroundMap;
import com.gamestudio.gameobject.BulletManager;
import com.gamestudio.gameobject.Camera;
import com.gamestudio.gameobject.MegaMan;
import com.gamestudio.gameobject.ParticularObject;
import com.gamestudio.gameobject.ParticularObjectManager;
import com.gamestudio.gameobject.PhysicalMap;
import com.gamestudio.userinterface.GameFrame;
import com.gamestudio.userinterface.GamePanel;



import javafx.scene.media.AudioClip;

import java.awt.*;
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

    public int getLastState() {
        return lastState;
    }

    public void setLastState(int lastState) {
        this.lastState = lastState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String textTutorial;
    public int currentSize = 1;
    
    protected boolean finalbossTrigger= true;
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
        bgMusic.setCycleCount(AudioClip.INDEFINITE);
        bgMusic.play();
        
    }
    
    public abstract void initEnemies();

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    protected void TutorialUpdate(){
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
    
    public void Update()
    {
        switch (state) {
            case GAMEOVER, GAMEWIN -> bgMusic.stop();
        }


    }

    public void Render(){

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if(g2!=null){
            switch (state) {
                case INIT_GAME -> {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    if (round == 1) {
                        g2.setFont(pixel);
                        g2.setColor(Color.WHITE);
                        g2.drawString("ROUND 1", GameFrame.SCREEN_WIDTH/2-80, GameFrame.SCREEN_HEIGHT / 2-30);
                        g2.drawString("(Press Enter to Continue)", GameFrame.SCREEN_WIDTH/2-250, GameFrame.SCREEN_HEIGHT / 2+50);
                    } else if (round == 2) {
                        g2.setFont(pixel);
                        g2.setColor(Color.WHITE);
                        g2.drawString("ROUND 2", GameFrame.SCREEN_WIDTH/2-80, GameFrame.SCREEN_HEIGHT / 2-30);
                        g2.drawString("(Press Enter to Continue)", GameFrame.SCREEN_WIDTH/2-250, GameFrame.SCREEN_HEIGHT / 2+50);
                    }
                }
                case PAUSEGAME -> gamePanel.setState(new PauseState(gamePanel, this));
                case TUTORIAL -> {
                    backgroundMap.draw(g2);
                    if (tutorialState == MEETFINALBOSS) {
                        particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                }
                case GAMEWIN, GAMEPLAY -> {
                    backgroundMap.draw(g2);
                    particularObjectManager.draw(g2);
                    bulletManager.draw(g2);
                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, megaMan.getBlood(), 20);
                    for (int i = 0; i < numberOfLife; i++) {
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getImage(), 20 + i * 40, 18, null);
                    }
                    if (state == GAMEWIN) {
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(), 300, 300, null);
                    }
                }
                case GAMEOVER -> {
                    Image image = Toolkit.getDefaultToolkit().getImage("data\\gameover.gif");
                    g2.drawImage(image, 0, 0, null);
                    g2.setFont(pixel);
                    g2.setColor(Color.YELLOW);
                    g2.drawString("(Press Enter to Quit)", 400, 380);
                    bgMusic.stop();
                }
            }
            

        }

    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_DOWN -> megaMan.dick();
            case KeyEvent.VK_RIGHT -> {
                megaMan.setDirection(ParticularObject.RIGHT_DIR);
                megaMan.run();
            }
            case KeyEvent.VK_LEFT -> {
                megaMan.setDirection(ParticularObject.LEFT_DIR);
                megaMan.run();
            }
            case KeyEvent.VK_ENTER -> {
                if (state == GameWorldState.INIT_GAME) {
                    if (previousState == GameWorldState.GAMEPLAY)
                        switchState(GameWorldState.GAMEPLAY);
                    else switchState(GameWorldState.TUTORIAL);

                }
                if (state == GameWorldState.TUTORIAL && storyTutorial >= 1) {
                    if (storyTutorial <= texts1.length - 1) {
                        storyTutorial++;
                        currentSize = 1;
                        textTutorial = texts1[storyTutorial - 1];
                    } else {
                        switchState(GameWorldState.GAMEPLAY);
                    }

                    // for meeting boss tutorial
                    if (tutorialState == GameWorldState.MEETFINALBOSS) {
                        switchState(GameWorldState.GAMEPLAY);
                    }
                }
            }
            case KeyEvent.VK_UP -> megaMan.jump();
            case KeyEvent.VK_SPACE -> megaMan.attack();
        }
    }

    @Override
    public void setReleasedButton(int code) {
        switch(code){
            
            case KeyEvent.VK_UP:

            case KeyEvent.VK_SPACE:

            case KeyEvent.VK_A:

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
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
                
        }}
	
}
