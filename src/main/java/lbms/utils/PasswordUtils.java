package lbms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordUtils {

    private static final String HASH_ALGO = "SHA-256";
    private static final int SALT_LENGTH = 32; // 32 bytes

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(String rawPassword, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
        md.update(salt);
        md.update(rawPassword.getBytes());
        return md.digest();
    }

    public static boolean verifyPassword(String rawPassword, byte[] salt, byte[] expectedHash) throws NoSuchAlgorithmException {
        byte[] hash = hashPassword(rawPassword, salt);
        return Arrays.equals(hash, expectedHash);
    }
}
