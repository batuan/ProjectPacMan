package PhysicsEngine.CollisionEngine;

import Entity.Entity;

import java.awt.geom.Ellipse2D;

public class CollisionCircle implements CollisionEngine{
    @Override
    public boolean isOutOfMaze(Entity e1, int mazeHeight, int mazeWidth) {
        return false;
    }

    /**
     * e1 with center (x1,y1) and radius r1;
     * e2 with center (x2,y2) and radius r2.
     *  So you can detect collision if:
     *  (x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
     * @param e1 Entity 1
     * @param e2 Entity 2
     * @return true if has a collision between e1 and e2.
     */

    @Override
    public boolean isCollision(Entity e1, Entity e2) {
        double centerXE1 = e1.getPixelPosition().x + (double)e1.getSize() / 2;
        double centerYE1 = e1.getPixelPosition().y + (double)e1.getSize() / 2;
        double centerXE2 = e2.getPixelPosition().x + (double)e1.getSize() / 2;
        double centerYE2 = e2.getPixelPosition().y + (double)e1.getSize() / 2;
        double maxsize = (double) e1.getSize()/2 + (double) e2.getSize()/2;
        double distance = (centerXE1-centerXE2)*(centerXE1-centerXE2) + (centerYE1-centerYE2)*(centerYE1-centerYE2);
        return (distance<=maxsize*maxsize);
    }
}
