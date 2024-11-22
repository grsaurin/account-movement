package sofka.assesment.accountmovements.model.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofka.assesment.accountmovements.model.Common;

import java.sql.Timestamp;

@Entity
@Table(name = "accounts")
@SecondaryTable(name = "movements", pkJoinColumns = @PrimaryKeyJoinColumn(name = "account_id"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountsMovementsReportObjDao {
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

    @Column(name = "mov_type", table = "movements")
    private Common.Movement_Types mov_type;

    @Column(name = "amount", table = "movements")
    private Float amount;

    @Column(name = "balance", table = "movements")
    private Float mov_balance;

    @Column(name = "date", table = "movements")
    private Timestamp date;
}
