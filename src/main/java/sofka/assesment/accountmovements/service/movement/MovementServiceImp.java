package sofka.assesment.accountmovements.service.movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sofka.assesment.accountmovements.exceptions.UnableProcessMovementException;
import sofka.assesment.accountmovements.model.Account;
import sofka.assesment.accountmovements.model.DAO.MovementObjDao;
import sofka.assesment.accountmovements.model.Movement;
import sofka.assesment.accountmovements.repository.movement.MovementDao;
import sofka.assesment.accountmovements.service.account.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MovementServiceImp implements MovementService{

    private final MovementDao movementDao;
    private final AccountService accountService;

    @Autowired
    public MovementServiceImp(MovementDao movementDao, AccountService accountService) {
        this.movementDao = movementDao;
        this.accountService = accountService;
    }

    @Override
    public void addMovement(Movement mov) throws UnableProcessMovementException {
        //check existence of account number
        Account acc = accountService.getAccount(mov.getAccount_id());
        if(Objects.isNull(acc)){
            throw new UnableProcessMovementException("account not found", HttpStatus.BAD_REQUEST);
        }

        //Get the amount value to be deleted or added to the account
        Float amount = getAmountFromMov(mov.getAmount());

        //check operation
        if(amount < 0 ) {
            //check for sufficient funds
            if(acc.getBalance() - amount < 0) {
                //throw error: insufficient founds
                throw new UnableProcessMovementException("insufficient founds", HttpStatus.PRECONDITION_FAILED);
            }
        }

        //update balance in the account
        acc.setBalance(acc.getBalance()+amount);
        accountService.updateAccount(acc);

        //update balance in the movements if it doesn't match with account balance
        if(mov.getBalance() != acc.getBalance()){
            mov.setBalance(acc.getBalance());
        }

        //insert movement
        movementDao.addMovement(convertMovementToMovementDao(mov));
    }

    @Override
    public Movement getMovement(Long id) {
        return convertMovementDaoToMovement(movementDao.getMovement(id));
    }

    @Override
    public List<Movement> getMovements() {
        List<Movement> result = new ArrayList<>();

        for(MovementObjDao mod : movementDao.getMovements()){
            result.add(convertMovementDaoToMovement(mod));
        }

        return result;
    }

    @Override
    public void deleteMovement(Long id) {
        movementDao.deleteMovement(id);
    }

    @Override
    public void updateMovement(Movement mov) {
        movementDao.updateMovement(convertMovementToMovementDao(mov));
    }

    private MovementObjDao convertMovementToMovementDao(Movement mov){
        MovementObjDao mod = new MovementObjDao();
        if(Objects.nonNull(mov)){
            mod.setAccount_id(mov.getAccount_id());
            mod.setMov_type(mov.getMovement_types());
            mod.setAmount(getAmountFromMov(mov.getAmount()));
            mod.setBalance(mov.getBalance());
            mod.setDate(mov.getDate());
        }

        return mod;
    }

    private Movement convertMovementDaoToMovement(MovementObjDao mod){
        Movement mov = new Movement();
        if(Objects.nonNull(mod)){
            mov.setAccount_id(mod.getAccount_id());
            mov.setMovement_types(mod.getMov_type());
            mov.setAmount(getAmountFromDao(mod.getAmount()));
            mov.setBalance(mod.getBalance());
            mov.setDate(mod.getDate());
        }

        return mov;
    }

    private String getAmountFromDao(Float amount) {
        String result = "";
        if(amount < 0) {
            result = result.concat("Retiro de " + amount);
        } else {
            result = result.concat("Depósito de " + amount);
        }

        return result;
    }

    private Float getAmountFromMov(String op) {
        String[] sepOp = op.split(" ");
        Float result = Float.valueOf(sepOp[3]);

        if(new String("retiro").equals(sepOp[0].toLowerCase())) {
             result *= -1;
        }

        return result;
    }


}
