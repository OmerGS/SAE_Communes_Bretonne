package view.misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
* PasswordUtil is a class allow to hash password with a salt
* @author O.Gunes 
*/
public class PasswordUtil {

    /**
    * Allow to hash the password with the "SHA-256" algorithm
    *
    * @param password The plain password before hash
    * @param salt The salt
    * @return The hashed password
    * @throws NoSuchAlgorithmException If the algorithm doesn't present in the environment
    */
    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    /**
    * Allw to generate a salt
    *
    * @return Salt
    * @throws NoSuchAlgorithmException If the algorithm doesn't present in the environment
    */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
