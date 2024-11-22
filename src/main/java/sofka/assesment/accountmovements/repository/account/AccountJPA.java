package sofka.assesment.accountmovements.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sofka.assesment.accountmovements.model.DAO.AccountObjDao;
import sofka.assesment.accountmovements.model.DAO.AccountsMovementsReportObjDao;

import java.sql.Timestamp;
import java.util.List;

public interface AccountJPA extends JpaRepository<AccountObjDao, Long> {


    @Query(value = "SELECT 'a.account_number', 'a.account_type', 'a.balance', 'a.account_status', 'm.amount', 'm.balance', 'm.date' " +
            "FROM AccountObjDao as a INNER JOIN MovementObjDao as m ON 'a.id' = 'm.account_id' " +
            "WHERE 'm.date'  BETWEEN ?1 AND ?2 ORDER BY 'a.id'")
    List<AccountsMovementsReportObjDao> accMovReport(Timestamp begin, Timestamp end);
}
