package dmitr.app.sportiksclub.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SHA256HasherTest {

    @Test
    void getHash() {
        String line = "Hello, SportiksClub!";
        String hash = SHA256Hasher.getHash(line);
        int hashLength = 64;
        assertEquals(hash.length(), hashLength);
    }
}