package Entity;

import PhysicsEngine.Movement.Movement;
import PhysicsEngine.Movement.MovementType;

public class MovableEntity extends Entity implements Movement {
    private MovementType direction = MovementType.STOP;

    public MovementType getDirection() {
        return direction;
    }

    private Boolean canMove = true;

    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public void setDirection(MovementType direction) {
        this.direction = direction;
    }

    @Override
    public Boolean canMove() {
        return this.canMove;
    }

    @Override
    public void move() {

    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void goUp() {

    }

    @Override
    public void goDown() {

    }

    @Override
    public void goLeft() {

    }

    @Override
    public void goRight() {

    }

    @Override
    public void stop() {

    }
}
