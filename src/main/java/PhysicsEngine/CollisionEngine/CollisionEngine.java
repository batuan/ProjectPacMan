package PhysicsEngine.CollisionEngine;

import Entity.Entity;

/**
 * In this project, it has to type of collision: Rectangle: between ghost and pacman
 * and Circle collision: between Pacman and Coin. So this interface will take in two subclass.
 */

public interface CollisionEngine {
    public boolean isOutOfMaze( Entity e1, int mazeHeight, int mazeWidth);
    public boolean isCollision(Entity e1, Entity e2);
}
