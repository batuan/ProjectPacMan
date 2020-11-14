package Render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import App.CoreKernel;
import Entity.*;

//TODO: Render Entity with parallel programming to enhance performance.
//TODO: Reformat Game board with kernel.

public class GameBoard extends JPanel implements KeyListener {
	Timer coreKernelTimer;
	CoreKernel coreKernel;
    int[][] map;
    Image[] mapSegments;
    Image foodImage;
    Image[] pfoodImage;
    Pacman pacman;
    ArrayList<Coin> foods;
    ArrayList<Coin> pufoods;
    ArrayList<Ghost> ghosts;
    ArrayList<Wall> walls;
    boolean drawScore = true;
    boolean clearScore = false;
    int scoreToAdd = 0;
    int score;
    JLabel scoreboard;
    boolean mustReactivateSiren = false;
    public Point ghostBase;
    public int mx;
    public int my;
    private MazeWindow parent;
    private Map md;
    @Override
    public MazeWindow getParent() {
        return parent;
    }
    
    public GameBoard(JLabel scoreboard, Map map, MazeWindow pw) {
        coreKernel = new CoreKernel();
        this.md = map;
    	this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        this.parent = pw;
        mx = 29;
        my = 27;
        this.map = md.getMapGraphic();
        this.ghostBase = md.getGhostBasePosition();
        initImage();
        initEntity();
        initTimer();
        initKernel();
        startGame();
	}

	void startGame() {
        coreKernelTimer.start();
    }
	void initTimer(){
        coreKernelTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coreKernel.takeAnimation();
            }
        });
    }

    void initKernel() {
        coreKernel.pacman = this.pacman;
        coreKernel.walls = this.walls;
        coreKernel.coins = this.foods;
        coreKernel.coreGraphic = this.getGraphics();
    }

	void initEntity(){
        pacman = new Pacman(md.getPacmanPosition(), this);
        foods = new ArrayList<>();
        pufoods = new ArrayList<>();
        ghosts = new ArrayList<>();
        walls = new ArrayList<>();
        for(Point foodPosition: md.getCoinPositions()) {
            foods.add(new Coin(foodPosition.x, foodPosition.y));
        }
        for(Point puFood : md.getPuCoinPositions()) {
            Coin coin = new Coin(puFood.x, puFood.y);
            int random = new Random().nextInt(5);
            coin.setImage(pfoodImage[random]);
            pufoods.add(coin);

        }
        ghosts = new ArrayList<>();
        for(Ghost gd : md.getGhostsData()){
            gd.setGameBoard(this);
            ghosts.add(gd);
        }
        this.addKeyListener(this);

        //TODO : Add Maps
        for(Point mapPoint : md.getWallPositions()) {
            Wall wall = new Wall(mapPoint);
            wall.setImage(mapSegments[map[mapPoint.y][mapPoint.x]]);
            walls.add(wall);
        }
    }

    void initImage() {
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
                URL path = this.getClass().getClassLoader().getResource("Image/Coin/fruit-"+ms+".png");
                pfoodImage[ms] = new ImageIcon(path).getImage();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try{
            URL path = this.getClass().getClassLoader().getResource("Image/Coin/coin-normal.png");
            foodImage = new ImageIcon(path).getImage();
            //goImage = ImageIO.read(this.getClass().getResource("resources/images/gameover.png"));
            //vicImage = ImageIO.read(this.getClass().getResource("resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}
    }

    @Override
    protected void paintComponent(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paintComponent(g);
    	g.setColor(Color.blue);

        for (Wall wall : walls) {
            g.drawImage(wall.getImage(), wall.getPixelPosition().x, wall.getPixelPosition().y, null);
        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Coin f : foods){
            //g.fillOval(f.position.x*28+22,f.position.y*28+22,4,4);
            int x = f.getPixelPosition().x;
            int y = f.getPixelPosition().y;
            g.drawImage(foodImage, x, y, null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(Coin f : pufoods){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            int x = f.getPixelPosition().x;
            int y = f.getPixelPosition().y;
            g.drawImage(f.getImage(), x, y,null);
        }

        //Draw Pacman
        int xPacman = pacman.getPixelPosition().x;
        int yPacman = pacman.getPixelPosition().y;
        g.drawImage(pacman.getImage(), xPacman, yPacman, null);

        //Draw Ghosts
        for(Ghost gh : ghosts) {
            Image ghostImage = gh.getImage();
            int xGhost = gh.getPixelPosition().x;
            int yGhost = gh.getPixelPosition().y;
            g.drawImage(ghostImage, xGhost, yGhost, null);
        }

        if(drawScore) {
            //System.out.println("must draw score !");
            scoreboard.setText("    Score : " + coreKernel.score + "         Life:  " + pacman.getLife());
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
            //this.pacman.move();
        } else if (key == KeyEvent.VK_RIGHT) {
            System.out.println("right");
            this.pacman.goRight();
            //this.pacman.move();
        } else if (key == KeyEvent.VK_UP) {
            System.out.println("up");
            this.pacman.goUp();
            //this.pacman.move();
        } else if (key == KeyEvent.VK_DOWN) {
            System.out.println("down");
            this.pacman.goDown();
            //this.pacman.move();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
