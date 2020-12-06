package AI;

import App.CoreKernel;
import Entity.Ghost;
import Entity.Wall;
import PhysicsEngine.Movement.MovementType;

import java.awt.*;
import java.util.Random;

public class RandomMovement implements AiInterface{
    Ghost ghost;
    @Override
    public MovementType getMovement(Ghost ghost, CoreKernel coreKernel) {
        this.ghost = ghost;
        MovementType nextMove = MovementType.STOP;
        if(ghost.getDirection() != MovementType.STOP) {
            nextMove = checkMove(ghost.getDirection(), coreKernel);
            return nextMove;
        } else {
            Random randomMove = new Random();
            int random = randomMove.nextInt(4);
            System.out.println("random: "+ random);
            switch (random){
                case 0:
                    nextMove = MovementType.DOWN;
                    nextMove = checkMove(nextMove, coreKernel);
                    return nextMove;
                case 1:
                    nextMove= MovementType.UP;
                    nextMove = checkMove(nextMove, coreKernel);
                    return nextMove;
                case 2:
                    nextMove= MovementType.LEFT;
                    nextMove = checkMove(nextMove, coreKernel);
                    return nextMove;
                case 3:
                    nextMove= MovementType.RIGHT;
                    nextMove = checkMove(nextMove, coreKernel);
                    return nextMove;
                default:
                    return MovementType.STOP;
            }
        }
    }

    private MovementType checkMove(MovementType movement, CoreKernel coreKernel){
        Ghost next = new Ghost(ghost.getPosition().x, ghost.getPosition().y, ghost.getColor());
        Point currentPixel = new Point(ghost.getPixelPosition());
        switch (movement) {
            case RIGHT:
                currentPixel.x+=1;
                next.setPixelPosition(currentPixel);
                break;
            case LEFT:
                currentPixel.x-=1;
                next.setPixelPosition(currentPixel);
                break;
            case UP:
                currentPixel.y-=1;
                next.setPixelPosition(currentPixel);
                break;
            case DOWN:
                currentPixel.y-=1;
                next.setPixelPosition(currentPixel);
                break;
        }

        for(Wall w : coreKernel.walls){
            if(coreKernel.collisionRectangle.isCollision(next, w)) {
                System.out.println("collision in random move");
                System.out.println("ghost: " + ghost.getPixelPosition());
                System.out.println("wall: " + w.getPixelPosition());
                movement = MovementType.STOP;
                return movement;
            }
        }
        return movement;
    }
}
