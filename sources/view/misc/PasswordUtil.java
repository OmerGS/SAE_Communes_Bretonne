package view.misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * The PasswordUtil class provides utility methods for hashing passwords using SHA-256 algorithm with a salt.
 * 
 * This class includes two main methods:
 * <ul>
 *   <li>{@link #hashPassword(String, byte[])}: Hashes a password using SHA-256 algorithm with provided salt.</li>
 *   <li>{@link #getSalt()}: Generates a random salt used for password hashing.</li>
 * </ul>
 * 
 * @author O.Gunes
 */
public class PasswordUtil {

    /**
     * Hashes the given password using SHA-256 algorithm with the provided salt.
     * 
     * @param password The plain password before hashing.
     * @param salt The salt used in hashing.
     * @return The hashed password encoded in Base64.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available in the environment.
     */
    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    /**
     * Generates a random salt for password hashing.
     * 
     * @return The generated salt as a byte array.
     * @throws NoSuchAlgorithmException If the SHA1PRNG algorithm is not available in the environment.
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
