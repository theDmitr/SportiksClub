package dmitr.app.sportiksclub.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "membershipTypes")
public class MembershipType {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @DatabaseField(canBeNull = false)
    private int duration;

    @DatabaseField(canBeNull = false)
    private boolean hasTrainer;

    public MembershipType() {
    }

    public MembershipType(String name, int duration, boolean hasTrainer) {
        this.name = name;
        this.duration = duration;
        this.hasTrainer = hasTrainer;
    }

    /**
     * Возвращает ID абонемента
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает наименование абонемента
     * @return наименование
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает длительность абонемента
     * @return длительность
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Возвращает наличие тренера
     * @return наличие тренера
     */
    public boolean hasTrainer() {
        return hasTrainer;
    }

}
