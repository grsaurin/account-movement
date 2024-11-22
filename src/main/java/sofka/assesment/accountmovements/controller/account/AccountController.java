package sofka.assesment.accountmovements.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import sofka.assesment.accountmovements.model.Account;
import sofka.assesment.accountmovements.model.Movement;
import sofka.assesment.accountmovements.service.account.AccountService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAccounts() {
        try{
            List<Account> allAccounts = accountService.getAccounts();
            return new ResponseEntity(allAccounts, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccountById(@PathVariable("id") Long id) {
        try{
            Account acc = accountService.getAccount(id);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAccount(@RequestBody Account acc) {
        try{
            accountService.addAccount(acc);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAccountById(@PathVariable("id") Long id) {
        try{
            accountService.deleteAccount(id);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAccountById(@RequestBody Account account) {
        try{
            accountService.updateAccount(account);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @GetMapping(value = {"/report"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccountMovementClientReport(@RequestParam("begin") @DateTimeFormat(pattern = "MM.dd.yyyy")Date begin, @RequestParam("end") @DateTimeFormat(pattern = "MM.dd.yyyy")Date end) {
        try{
            Timestamp beginT = new Timestamp(begin.getTime());
            Timestamp endT = new Timestamp(end.getTime());
            return new ResponseEntity(accountService.getAccMovCliReport(beginT, endT) ,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
