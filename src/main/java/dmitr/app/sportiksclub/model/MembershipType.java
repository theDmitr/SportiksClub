package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

@DatabaseTable(tableName = "membershipTypes")
public class MembershipType {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private Date duration;

    @DatabaseField(canBeNull = false)
    private boolean hasTrainer;

    public MembershipType() {
    }

    public MembershipType(String name, Date duration, boolean hasTrainer) {
        this.name = name;
        this.duration = duration;
        this.hasTrainer = hasTrainer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDuration() {
        return duration;
    }

    public boolean hasTrainer() {
        return hasTrainer;
    }

}
