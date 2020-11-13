package PhysicsEngine.CollisionEngine;

import Entity.MovableEntity;
import Entity.Wall;

public interface CollisionMap {
    public void collisionWithWall(MovableEntity entity, Wall wall);
}
