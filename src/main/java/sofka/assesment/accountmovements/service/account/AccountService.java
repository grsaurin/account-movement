package sofka.assesment.accountmovements.service.account;

import sofka.assesment.accountmovements.model.Account;
import sofka.assesment.accountmovements.model.AccountsMovementsReport;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface AccountService {
    void addAccount(Account acc);
    Account getAccount(Long id);
    List<Account> getAccounts();
    void deleteAccount(Long id);
    void updateAccount(Account acc);
    List<AccountsMovementsReport> getAccMovCliReport(Timestamp begin, Timestamp end) throws ExecutionException, InterruptedException;
}
