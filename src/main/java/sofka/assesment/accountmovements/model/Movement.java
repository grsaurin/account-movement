package sofka.assesment.accountmovements.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movement {
    private Long id;
    private Common.Movement_Types movement_types;
    private String amount;
    private Float balance;
    private Timestamp date;
    private Long account_id;
}