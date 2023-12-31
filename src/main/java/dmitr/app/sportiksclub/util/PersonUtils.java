package dmitr.app.sportiksclub.util;

import dmitr.app.sportiksclub.model.Person;

public class PersonUtils {

    /**
     * Возвращает инициалы Персоны
     *
     * @param person сущность персоны
     * @return инициалы
     */
    public static String getInitials(Person person) {
        return String.join(
                " ",
                new String[]{
                        person.getSurname(),
                        person.getName().charAt(0) + ".",
                        !person.getPatronymic().isEmpty() ? person.getPatronymic().charAt(0) + "." : ""
                }
        );
    }

}
