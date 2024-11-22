package sofka.assesment.accountmovements.model.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofka.assesment.accountmovements.model.Common;

@Entity(name = "AccountObjDao")
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountObjDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String account_number;
    @Column(name = "account_type")
    private Common.Account_Type account_type;
    @Column(name = "balance")
    private Float balance;
    @Column(name = "account_status")
    private Common.Account_Status account_status;

    @Column(name = "fk_client_id")
    private Long client_id;
}
