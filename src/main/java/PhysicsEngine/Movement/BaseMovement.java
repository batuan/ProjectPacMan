package PhysicsEngine.Movement;

public class BaseMovement implements Movement{
    protected String name;
    MovementType direction = MovementType.UP;
    private int speed = 0;
    private int xPosition;
    private int yPosition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public Boolean canMove() {
        return true;
    }

    @Override
    public void move() {
        switch (direction) {
            case UP:
                yPosition -= speed;
                break;
            case DOWN:
                yPosition += speed;
                break;
            case LEFT:
                xPosition -= speed;
                break;
            case RIGHT:
                xPosition += speed;
                break;
            default: break;
        }
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void goUp() {
        direction = MovementType.UP;
    }

    @Override
    public void goDown() {
        direction = MovementType.DOWN;
    }

    @Override
    public void goLeft() {
        direction = MovementType.LEFT;
    }

    @Override
    public void goRight() {
        direction = MovementType.RIGHT;
    }

    @Override
    public void stop() {

    }
}
