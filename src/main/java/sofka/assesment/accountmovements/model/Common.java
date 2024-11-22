package sofka.assesment.accountmovements.model;

public abstract class Common {
    public enum Account_Type {savings, deposit, regular, company}
    public enum Account_Status {ACTIVE, INACTIVE, IDLE, BLOQUED}
    public enum Movement_Types {rent, transfer, payment, others}
}
