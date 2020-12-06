package AI;

import App.CoreKernel;
import Entity.Ghost;
import PhysicsEngine.Movement.MovementType;

public interface AiInterface {
    MovementType getMovement(Ghost ghost, CoreKernel coreKernel);
}
