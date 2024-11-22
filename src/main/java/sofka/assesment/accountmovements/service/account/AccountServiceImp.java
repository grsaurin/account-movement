package sofka.assesment.accountmovements.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sofka.assesment.accountmovements.model.Account;
import sofka.assesment.accountmovements.model.AccountsMovementsReport;
import sofka.assesment.accountmovements.model.ClientMinData;
import sofka.assesment.accountmovements.model.DAO.AccountObjDao;
import sofka.assesment.accountmovements.model.DAO.AccountsMovementsReportObjDao;
import sofka.assesment.accountmovements.repository.account.AccountDao;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService{

    @Value("${client.service.host}")
    private String clientServiceHost;

    @Value("${client.service.port}")
    private String clientServicePort;

    private final AccountDao accountDao;
    private final RestTemplate template;

    @Autowired
    public AccountServiceImp(AccountDao accountDao, RestTemplate template) {
        this.accountDao = accountDao;
        this.template = template;
    }

    @Override
    public void addAccount(Account acc) {
        accountDao.addAccount(convertAccountToAccountDao(acc));
    }

    @Override
    public Account getAccount(Long id) {
        return convertAccountDaoToAccount(accountDao.getAccount(id));
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> result = new ArrayList<>();
        for(AccountObjDao aod : accountDao.getAccounts()){
            result.add(convertAccountDaoToAccount(aod));
        }

        return result;
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }

    @Override
    public void updateAccount(Account acc) {
        //as improvement the service should call Clients MS to check client_id value
        accountDao.updateAccount(convertAccountToAccountDao(acc));
    }

    @Override
    public List<AccountsMovementsReport> getAccMovCliReport(Timestamp begin, Timestamp end) throws ExecutionException, InterruptedException {
        //get acc and mov data
        List<AccountsMovementsReportObjDao> amrod =  accountDao.getAccMovReportData(begin, end);
        List<AccountsMovementsReport> result = new ArrayList<>();
        Map<Long, List<AccountsMovementsReportObjDao>> amrodMap = new HashMap<>();

        if(amrod.size() > 0) {
            //create client id list
            for(AccountsMovementsReportObjDao amr : amrod) {
                List<AccountsMovementsReportObjDao> innerList = amrodMap.containsKey(amr.getClient_id()) ? amrodMap.get(amr.getClient_id()) : new ArrayList<>();
                innerList.add(amr);
                amrodMap.put(amr.getClient_id(), innerList);
            }

            //call clients ms to get names by id
            CompletableFuture<List<ClientMinData>> cmdL = getClientsMinData(amrodMap.keySet().stream().toList());

            for(ClientMinData cmd : cmdL.get()){
                for(AccountsMovementsReportObjDao elem : amrodMap.get(cmd.getId())) {
                    //create report
                    result.add(createReportData(elem, cmd.getName()));
                }
            }
        }

        return result;
    }

    private AccountObjDao convertAccountToAccountDao(Account acc) {
        AccountObjDao aod = new AccountObjDao();
        aod.setAccount_number(acc.getAccount_number());
        aod.setAccount_type(acc.getAccount_type());
        aod.setBalance(acc.getBalance());
        aod.setAccount_status(acc.getAccount_status());
        aod.setClient_id(acc.getClient_id());

        return aod;
    }

    private Account convertAccountDaoToAccount(AccountObjDao aod) {
        Account acc = new Account();
        if(Objects.nonNull(aod)) {
            acc.setId(aod.getId());
            acc.setAccount_number(aod.getAccount_number());
            acc.setAccount_type(aod.getAccount_type());
            acc.setBalance(aod.getBalance());
            acc.setAccount_status(aod.getAccount_status());
            acc.setClient_id(aod.getClient_id());
        }
        return acc;
    }

    @Async
    public CompletableFuture<List<ClientMinData>> getClientsMinData(List<Long> ids) {
        String url = "http://".concat(clientServiceHost).concat(":").concat(clientServicePort).concat("/search");
        ResponseEntity<ClientMinData[]> res = template.postForEntity(url, ids, ClientMinData[].class);

        return CompletableFuture.completedFuture(Arrays.stream(res.getBody()).collect(Collectors.toList()));
    }

    private AccountsMovementsReport createReportData(AccountsMovementsReportObjDao amr, String name) {

        return new AccountsMovementsReport(name, amr.getAccount_number(), amr.getAccount_type(), amr.getBalance(), amr.getAccount_status(), amr.getMov_balance(), amr.getAmount(), amr.getDate());
    }
}