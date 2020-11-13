package App;

import Entity.Coin;
import Entity.Ghost;
import Entity.Pacman;
import Entity.Wall;
import PhysicsEngine.CollisionEngine.CollisionInMap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

enum GameState{PAUSE, VICTORY, GAMEOVER, PLAY}

public class CoreKernel {
    public Pacman pacman;
    public CopyOnWriteArrayList<Ghost> ghosts;
    public CopyOnWriteArrayList<Coin> coins;
    public ArrayList<Wall> walls;
    public GameState gameState;
    private CollisionInMap checkCollisionInMap = new CollisionInMap();
    public CoreKernel(){

    }

    public void takeAnimation() {
        for (Wall wall : walls) {
            checkCollisionInMap.collisionWithWall(pacman, wall);
        }
        pacman.move();
    }
}
