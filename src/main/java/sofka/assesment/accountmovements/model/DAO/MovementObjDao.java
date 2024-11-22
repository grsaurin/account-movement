package sofka.assesment.accountmovements.model.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofka.assesment.accountmovements.model.Common;

import java.sql.Timestamp;

@Entity(name = "MovementObjDao")
@Table(name = "movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementObjDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private Long account_id;
    @Column(name = "mov_type")
    private Common.Movement_Types mov_type;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "date")
    private Timestamp date;
}
