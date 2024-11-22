package sofka.assesment.accountmovements.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    private Long id;
    private String account_number;
    private Common.Account_Type account_type;
    private Float balance;
    private Common.Account_Status account_status;
    private Long client_id;
}

