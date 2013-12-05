package utils;

/**
 * User: Charles
 * Date: 4/30/13
 *
 * This needs to be updated to use the apache codec
 *
 */
import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptionUtil {

    private static final String KEY = "y3A-1-g0T-a-r4sH!!!!!!!!";
    private BasicTextEncryptor textEncryptor = null;

    public EncryptionUtil() throws Exception {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(KEY);
    }

    /**
     * Method To Encrypt The String
     */
    public String encrypt(String unencryptedString) {
        return textEncryptor.encrypt(unencryptedString);
    }
    /**
     * Method To Decrypt An Ecrypted String
     */
    public String decrypt(String encryptedString) {
        return textEncryptor.decrypt(encryptedString);
    }
}
