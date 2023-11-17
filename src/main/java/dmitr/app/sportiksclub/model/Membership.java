package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dmitr.app.sportiksclub.util.DateUtils;

import java.sql.Date;

@DatabaseTable(tableName = "memberships")
public class Membership {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Customer customer;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private MembershipType membershipType;

    @DatabaseField(canBeNull = false)
    private Date beginDate;

    @DatabaseField(canBeNull = false)
    private Date endDate;

    public Membership() {
    }

    public Membership(Customer customer, MembershipType membershipType, Date beginDate) {
        this.customer = customer;
        this.membershipType = membershipType;
        this.beginDate = beginDate;
        this.endDate = DateUtils.addDaysToDate(beginDate, membershipType.getDuration());
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
