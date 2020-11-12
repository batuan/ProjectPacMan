package PhysicsEngine.CollisionEngine;

import Entity.Entity;

import java.awt.*;

public class CollisionRectangle implements CollisionEngine{

    @Override
    public boolean isOutOfMaze(Entity e1, int mazeHeight, int mazeWidth) {
        return false;
    }

    // https://stackoverflow.com/questions/31022269/collision-detection-between-two-rectangles-in-java
    @Override
    public boolean isCollision(Entity e1, Entity e2) {
        Rectangle r1 = new Rectangle(e1.getPixelPosition().x, e1.getPixelPosition().y, e1.getSize(), e1.getSize());
        Rectangle r2 = new Rectangle(e2.getPixelPosition().x, e2.getPixelPosition().y, e2.getSize(), e2.getSize());
        return r1.intersects(r2);
    }

    Boolean isIntersect(
            int Ax, int Ay, int Aw, int Ah,
            int Bx, int By, int Bw, int Bh)
    {
        return  Bx + Bw > Ax && By + Bh > Ay
                && Ax + Aw > Bx && Ay + Ah > By;
    }
}
