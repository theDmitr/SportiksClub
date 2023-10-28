package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;

@DatabaseTable(tableName = "memberships")
public class Membership {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private int customerId;

    @DatabaseField(foreign = true)
    private int membershipTypeId;

    @DatabaseField(canBeNull = false)
    private boolean status;

    @DatabaseField(canBeNull = false)
    private LocalDate beginDate;

    @DatabaseField(canBeNull = false)
    private LocalDate endDate;

    @DatabaseField(canBeNull = false)
    private float discount;

    public Membership() { }

    public Membership(int customerId, int membershipTypeId, LocalDate beginDate, LocalDate endDate, float discount) {
        this.customerId = customerId;
        this.membershipTypeId = membershipTypeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getMembershipTypeId() {
        return membershipTypeId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public float getDiscount() {
        return discount;
    }

}
