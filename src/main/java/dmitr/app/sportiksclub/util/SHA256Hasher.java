package dmitr.app.sportiksclub.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHA256Hasher {

    public static String getHash(String value) {
        return Hashing.sha256().hashString(value, StandardCharsets.UTF_8).toString();
    }

}
