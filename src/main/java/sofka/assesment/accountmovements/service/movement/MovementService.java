package sofka.assesment.accountmovements.service.movement;

import sofka.assesment.accountmovements.exceptions.UnableProcessMovementException;
import sofka.assesment.accountmovements.model.Movement;

import java.util.List;

public interface MovementService {
    void addMovement(Movement mov) throws UnableProcessMovementException;
    Movement getMovement(Long id);
    List<Movement> getMovements();
    void deleteMovement(Long id);
    void updateMovement(Movement acc);
}
