package dmitr.app.sportiksclub.database;

import dmitr.app.sportiksclub.model.MembershipType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DatabaseHelperTest {

    @Test
    void authUser() {
        String login = "admin";
        String password = "admin";
        boolean result, flag;

        result = DatabaseHelper.authUser(login, password);
        flag = DatabaseHelper.getAuthorizedUser() != null;

        assertEquals(result, flag);
    }

    @Test
    void removeMembershipType() {
        DatabaseHelper.createMembershipType("Test", 16, true);
        List<MembershipType> memberships = DatabaseHelper.getMembershipTypes();

        MembershipType membership = memberships.get(memberships.size() - 1);

        DatabaseHelper.removeMembershipType(membership);

        assertFalse(DatabaseHelper.getMembershipTypes().contains(membership));
    }

}