package App;

import Entity.Coin;
import Entity.Ghost;
import Entity.Pacman;
import Entity.Wall;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

enum GameState{PAUSE, VICTORY, GAMEOVER, PLAY}

public class Kernel {
    //public Engine engine;
    //public CollideBehavior collBeha;
    //public OutOfBoardBehavior OOBBeha;

    public Pacman pacman;
    public CopyOnWriteArrayList<Ghost> ghosts;
    public CopyOnWriteArrayList<Coin> coins;
    public List<Wall> walls;

    public GameState gameState;
    double canvasWidth;
    double canvasHeight;
    public int score;
    public int timer;
}
