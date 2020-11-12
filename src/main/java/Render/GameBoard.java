package Render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

import Entity.Coin;
import Entity.Ghost;
import Entity.Map;
import Entity.Pacman;
import PhysicsEngine.GameKeyAdapter;

public class GameBoard extends JPanel implements KeyListener {
	Timer redrawTimer;
    ActionListener redrawAL;
    int[][] map;
    Image[] mapSegments;
    Image foodImage;
    Image[] pfoodImage;
    Image goImage;
    Image vicImage;
    Pacman pacman;
    ArrayList<Coin> foods;
    ArrayList<Coin> pufoods;
    ArrayList<Ghost> ghosts;
    //ArrayList<TeleportTunnel> teleports;
    boolean isCustom = false;
    boolean isGameOver = false;
    boolean isWin = false;
    boolean drawScore = false;
    boolean clearScore = false;
    int scoreToAdd = 0;
    int score;
    JLabel scoreboard;
    //LoopPlayer siren;
    boolean mustReactivateSiren = false;
    //LoopPlayer pac6;
    public Point ghostBase;
    public int mx;
    public int my;
    //MapData md_backup;
    private MazeWindow parent;

    @Override
    public MazeWindow getParent() {
        return parent;
    }
    
    public GameBoard(JLabel scoreboard, Map md, MazeWindow pw) {
    	this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        this.parent = pw;
        mx = 29;
        my = 27;
        this.map = md.getMapGraphic();
        this.isCustom = false;
        this.ghostBase = md.getGhostBasePosition();
        pacman = new Pacman(md.getPacmanPosition(), this);
        foods = new ArrayList<>();
        pufoods = new ArrayList<>();
        ghosts = new ArrayList<>();
        //TODO : read food from mapData (Map 1)

        if(isCustom) {
            for (int i = 0; i < mx; i++) {
                for (int j = 0; j < my; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Coin(i, j));
                }
            }
        }else{
            for(Point foodPosition: md.getCoinPositions()) {
                foods.add(new Coin(foodPosition.x, foodPosition.y));
            }

        }

        for(Point puFood : md.getPuCoinPositions()) {
            pufoods.add(new Coin(puFood.x, puFood.y));
        }

        ghosts = new ArrayList<>();
        for(Ghost gd : md.getGhostsData()){
            gd.setGameBoard(this);
        	ghosts.add(gd);
        }
        //teleports = md.getTeleports();
        setLayout(null);
        setSize(20* mx,20* my);
        setBackground(Color.black);
        mapSegments = new Image[28];
        mapSegments[0] = null;
        for(int ms=1;ms<27;ms++){
            try {
            	URL path = this.getClass().getClassLoader().getResource("Image/map_segments/"+ms+".png");
                mapSegments[ms] = new ImageIcon(path).getImage();
            }catch(Exception e){
                System.out.println("ms: " + ms);
                e.printStackTrace();
            }
        }
        pfoodImage = new Image[5];
        for(int ms=0 ;ms<5;ms++){
            try {
                URL path = this.getClass().getClassLoader().getResource("Image/Coin/Coin-0.png");
                pfoodImage[ms] = new ImageIcon(path).getImage();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try{
            URL path = this.getClass().getClassLoader().getResource("Image/Coin/Coin-0.png");
            foodImage = new ImageIcon(path).getImage();
            //goImage = ImageIO.read(this.getClass().getResource("resources/images/gameover.png"));
            //vicImage = ImageIO.read(this.getClass().getResource("resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}
        this.addKeyListener(this);
	}

	void changeLocation(int speed) {

    }

    @Override
    protected void paintComponent(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paintComponent(g);
    	g.setColor(Color.blue);
        for(int i = 0; i< mx; i++){
            for(int j = 0; j< my; j++){
                if(map[i][j]>0){
                    //g.drawImage(10+i*28,10+j*28,28,28);
                    int x = 10 + j * 28;
                    int y = 10 + i * 28;
                    g.drawImage(mapSegments[map[i][j]], x, y,null);
                }
            }
        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Coin f : foods){
            //g.fillOval(f.position.x*28+22,f.position.y*28+22,4,4);
            int x = 10 + f.getPosition().x * 28;
            int y = 10 + f.getPosition().y * 28;
            g.drawImage(foodImage, x, y, null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(Coin f : pufoods){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            int x = 10 + f.getPosition().x * 28;
            int y = 10 + f.getPosition().y * 28;
            g.drawImage(pfoodImage[0], x, y,null);
        }

        //Draw Pacman
        int xPacman = pacman.getPixelPosition().x;
        int yPacman = pacman.getPixelPosition().y;
        g.drawImage(pacman.getImage(), xPacman, yPacman, null);

        //Draw Ghosts
        for(Ghost gh : ghosts) {
            Image ghostImage = gh.getImage();
            int xGhost = gh.getPosition().x * 28 + 10;
            int yGhost = gh.getPosition().y * 28 + 10;
            g.drawImage(ghostImage, xGhost, yGhost, null);
        }
        if(clearScore){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore =false;
        }

        if(drawScore) {
            //System.out.println("must draw score !");
            g.setFont(new Font("Arial",Font.BOLD, 15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd*100;
            g.drawString(s.toString(), pacman.getPixelPosition().x + 13, pacman.getPixelPosition().y + 50);
            //drawScore = false;
            score += s;
            scoreboard.setText("    Score : "+score);
            clearScore = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            System.out.println("left");
            this.pacman.goLeft();
            this.pacman.move();
        } else if (key == KeyEvent.VK_RIGHT) {
            System.out.println("right");
            this.pacman.goRight();
            this.pacman.move();
        } else if (key == KeyEvent.VK_UP) {
            System.out.println("up");
            this.pacman.goUp();
            this.pacman.move();
        } else if (key == KeyEvent.VK_DOWN) {
            System.out.println("down");
            this.pacman.goDown();
            this.pacman.move();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
