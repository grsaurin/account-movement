package sofka.assesment.accountmovements.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountsMovementsReport {

    private String client_mame;
    private String account_number;
    private Common.Account_Type account_type;
    private Float balance;
    private Common.Account_Status account_status;
    private Float mov_balance;
    private Float amount;
    private Timestamp date;
}
