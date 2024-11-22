package sofka.assesment.accountmovements.repository.account;

import sofka.assesment.accountmovements.model.AccountsMovementsReport;
import sofka.assesment.accountmovements.model.DAO.AccountObjDao;
import sofka.assesment.accountmovements.model.DAO.AccountsMovementsReportObjDao;

import java.sql.Timestamp;
import java.util.List;

public interface AccountDao {
    void addAccount(AccountObjDao acc);
    AccountObjDao getAccount(Long id);
    List<AccountObjDao> getAccounts();
    void deleteAccount(Long id);
    void updateAccount(AccountObjDao acc);
    List<AccountsMovementsReportObjDao> getAccMovReportData(Timestamp begin, Timestamp end);

}
