package sofka.assesment.accountmovements.repository.movement;

import sofka.assesment.accountmovements.model.DAO.MovementObjDao;

import java.util.List;

public interface MovementDao {
    void addMovement(MovementObjDao mov);
    MovementObjDao getMovement(Long id);
    List<MovementObjDao> getMovements();
    void deleteMovement(Long id);
    void updateMovement(MovementObjDao acc);
}
