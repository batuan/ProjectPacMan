package PhysicsEngine.Movement;

public interface Movement {
    Boolean canMove();
    void move();
    int Movement_delay = 70;
    String name();
    void goUp();
    void goDown();
    void goLeft();
    void goRight();
    void stop();
}
