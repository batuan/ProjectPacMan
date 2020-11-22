package App;

import AudioPlayer.GameAudioPlayer;
import Entity.Coin;
import Entity.Ghost;
import Entity.Pacman;
import Entity.Wall;
import PhysicsEngine.CollisionEngine.CollisionCircle;
import PhysicsEngine.CollisionEngine.CollisionInMap;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

enum GameState{PAUSE, VICTORY, GAMEOVER, PLAY}

public class CoreKernel {
    public Pacman pacman;
    public CopyOnWriteArrayList<Ghost> ghosts;
    public ArrayList<Coin> coins;
    public ArrayList<Wall> walls;
    public GameState gameState;
    private CollisionInMap checkCollisionInMap = new CollisionInMap();
    CollisionCircle collisionCircle = new CollisionCircle();
    public Graphics coreGraphic;
    public int score = 0;
    final GameAudioPlayer coinEat = new GameAudioPlayer("Sounds/coin_eat.wav");
    public CoreKernel(){

    }
    //TODO: Make pacman can choose the path to run.
    public void takeAnimation() {
        for (Wall wall : walls) {
            checkCollisionInMap.collisionWithWall(pacman, wall);
        }
        pacman.verifyNextDirection(this.walls);
        pacman.move();
        for (Coin coin:coins) {
            if(collisionCircle.isCollision(pacman, coin)) {
                coinEat.start();
                coins.remove(coin);
                score += 10;
                break;
            }
        }
    }
}
