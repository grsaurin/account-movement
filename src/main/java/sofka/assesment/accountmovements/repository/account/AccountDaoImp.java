package sofka.assesment.accountmovements.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sofka.assesment.accountmovements.model.AccountsMovementsReport;
import sofka.assesment.accountmovements.model.DAO.AccountObjDao;
import sofka.assesment.accountmovements.model.DAO.AccountsMovementsReportObjDao;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class AccountDaoImp implements AccountDao{

    private final AccountJPA accountJPA;

    @Autowired
    public AccountDaoImp(AccountJPA accountJPA) {
        this.accountJPA = accountJPA;
    }


    @Override
    public void addAccount(AccountObjDao acc) {
        accountJPA.saveAndFlush(acc);
    }

    @Override
    public AccountObjDao getAccount(Long id) {
        return accountJPA.findById(id).orElse(null);
    }

    @Override
    public List<AccountObjDao> getAccounts() {
        return accountJPA.findAll();
    }

    @Override
    public void deleteAccount(Long id) {
        accountJPA.deleteById(id);
    }

    @Override
    public void updateAccount(AccountObjDao acc) {
        accountJPA.saveAndFlush(acc);
    }

    @Override
    public List<AccountsMovementsReportObjDao> getAccMovReportData(Timestamp begin, Timestamp end) {
        return accountJPA.accMovReport(begin, end);
    }
}
