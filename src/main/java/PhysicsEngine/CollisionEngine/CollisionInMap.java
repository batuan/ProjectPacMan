package PhysicsEngine.CollisionEngine;

import Entity.MovableEntity;
import Entity.Wall;
import PhysicsEngine.Movement.MovementType;

public class CollisionInMap implements CollisionMap{
    /**
     * This function check when the movable entity collision with map, it will stop the movement.
     * There are four type of movement must check: UP, DOWN, Left, Right. This class will check pacman or ghost
     * should be stop or not when have a collision with wall
     * @param movableEntity may be pacman or ghost
     * @param wall wal in map
     */
    @Override
    public void collisionWithWall(MovableEntity movableEntity, Wall wall) {
        MovementType direction = movableEntity.getDirection();
        int movableX = movableEntity.getPixelPosition().x;
        int movableY = movableEntity.getPixelPosition().y;
        int wallX = wall.getPixelPosition().x;
        int wallY = wall.getPixelPosition().y;
        int wallSize  =  wall.getSize();
        int movableSize = movableEntity.getSize();
        switch (direction){
            case UP: {
                //System.out.println("in up");
                int checkY = (movableY-wallY);
                int checkX = Math.abs(movableX-wallX);
                if(checkY>=0 && checkY<=wallSize && checkX<wallSize) {
                    System.out.println("Up collision with map");
                    System.out.println("Pacman: " + movableX + " " + movableY);
                    System.out.println("Wall: " + wallX + " " + wallY);
                    movableEntity.stop();
                }
                break;
            }

            case DOWN: {
                //System.out.println("in down");
                int checkY = (wallY-movableY);
                int checkX = Math.abs(movableX-wallX);
                if(checkY >= 0 && checkY<=wallSize && checkX<wallSize) {
                    movableEntity.stop();
                    System.out.println("Pacman: " + movableX + " " + movableY);
                    System.out.println("Wall: " + wallX + " " + wallY);
                    System.out.println("Down collision with map");
                }
                break;
            }
            case LEFT: {
               //System.out.println("in left");
                int checkX = (movableX-wallX);
                int checkY = Math.abs(movableY - wallY);
                if(checkX>=0&&checkX<=wallSize&&(checkY<wallSize)) {
                    System.out.println("Pacman: " + movableX + " " + movableY + " " + movableSize);
                    System.out.println("Wall: " + wallX + " " + wallY + " " + wallSize);
                    System.out.println("Left collision with map");
                    movableEntity.stop();
                }
                break;
            }

            case RIGHT:{
                //System.out.println("in right");
                int checkX = (wallX - movableX);
                int checkY = Math.abs(wallY-movableY);
                if(checkX >= 0 && checkX <=wallSize && checkY<wallSize) {
                    movableEntity.stop();
                    System.out.println("Right collision with map");
                    System.out.println("Pacman: " + movableX + " " + movableY);
                    System.out.println("Wall: " + wallX + " " + wallY);
                }
                break;
            }
        }
    }
}
