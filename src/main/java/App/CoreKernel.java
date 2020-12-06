package App;

import AI.AstarAI;
import AI.RandomMovement;
import AudioPlayer.GameAudioPlayer;
import Entity.Coin;
import Entity.Ghost;
import Entity.Pacman;
import Entity.Wall;
import PhysicsEngine.CollisionEngine.CollisionCircle;
import PhysicsEngine.CollisionEngine.CollisionInMap;
import PhysicsEngine.CollisionEngine.CollisionRectangle;
import PhysicsEngine.Movement.MovementType;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

enum GameState{PLAY, PAUSE, STOP, GAMEOVER, VICTORY}

public class CoreKernel {
    public Pacman pacman;
    //public CopyOnWriteArrayList<Ghost> ghosts;
    public ArrayList<Ghost> ghosts;
    public ArrayList<Coin> coins;
    public ArrayList<Wall> walls;
    public ArrayList<Point> wallPoint;
    public GameState gameState;
    public CollisionInMap checkCollisionInMap = new CollisionInMap();
    CollisionCircle collisionCircle = new CollisionCircle();
    public CollisionRectangle collisionRectangle = new CollisionRectangle();
    public Graphics coreGraphic;
    public int score = 0;
    final GameAudioPlayer coinEat = new GameAudioPlayer("Sounds/coin_eat.wav");
    public CoreKernel(){

    }
    //TODO: Make pacman can choose the path to run.
    int i = 0;
    public void takeAnimation() {
        if(gameState==GameState.VICTORY) {
            //victoryPush();
        }
        for (Wall wall : walls) {
            checkCollisionInMap.collisionWithWall(pacman, wall);
        }
        pacman.verifyNextDirection(this.walls);
        pacman.move();
        for(Ghost ghost : ghosts) {
            //ghost.setDirection(MovementType.LEFT);
            ghost.nextMoveCalculateByAI(this);
            ghost.move();
        }
//        ghosts.get(0).nextMoveCalculateByAI(this);
//        ghosts.get(0).move();
//        if(i == 0) {
//            ghosts.get(0).nextMoveCalculateByAI(this);
//            i+=1;
//        }
        for(Ghost ghost: ghosts){
            if(collisionRectangle.isCollision(pacman, ghost)){
                ghostCatchPacman();
                //if(f.getState() == Fantom.FantomState.NORMAL) playerCatched();
                //if(f.getState() == Fantom.FantomState.KILLABLE) fantomCatched(f);
            }
        }

        for (Coin coin:coins) {
            if(collisionCircle.isCollision(pacman, coin)) {
                coinEat.start();
                coins.remove(coin);
                score += 10;
                break;
            }
            if(coins.isEmpty()) {
                gameState = GameState.VICTORY;
            }
        }
    }

    public void setAIForGhost(){
        //ghosts.get(0).AI = new AstarAI();
        for (int i = 0; i<ghosts.size(); i++){
            if(i==2) ghosts.get(i).AI = new AstarAI();
            else this.ghosts.get(i).AI = new RandomMovement();
        }
    }

    public void ghostCatchPacman(){
        gameState = GameState.GAMEOVER;
    }

    public int[][] getWallsPosition() {
        int[][] walls = new int[wallPoint.size()][2];
        int i = 0;
        for (Point point:wallPoint){
            walls[i] = new int[]{point.y, point.x};
            i+=1;
        }
        return walls;
    }
}
