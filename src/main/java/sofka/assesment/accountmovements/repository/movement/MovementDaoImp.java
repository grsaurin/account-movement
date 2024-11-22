package sofka.assesment.accountmovements.repository.movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sofka.assesment.accountmovements.model.DAO.MovementObjDao;

import java.util.List;

@Repository
public class MovementDaoImp implements MovementDao {

    private final MovementJPA movementJPA;

    @Autowired
    public MovementDaoImp(MovementJPA movementJPA) {
        this.movementJPA = movementJPA;
    }


    @Override
    public void addMovement(MovementObjDao mov) {
        movementJPA.saveAndFlush(mov);
    }

    @Override
    public MovementObjDao getMovement(Long id) {
        return movementJPA.findById(id).orElse(null);
    }

    @Override
    public List<MovementObjDao> getMovements() {
        return movementJPA.findAll();
    }

    @Override
    public void deleteMovement(Long id) {
        movementJPA.deleteById(id);
    }

    @Override
    public void updateMovement(MovementObjDao acc) {
        movementJPA.saveAndFlush(acc);

    }
}
