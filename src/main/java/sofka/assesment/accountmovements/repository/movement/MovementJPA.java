package sofka.assesment.accountmovements.repository.movement;

import org.springframework.data.jpa.repository.JpaRepository;
import sofka.assesment.accountmovements.model.DAO.MovementObjDao;

public interface MovementJPA extends JpaRepository<MovementObjDao, Long> {
}
