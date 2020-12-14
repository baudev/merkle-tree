import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {

    /**
     * Converts string to bytes array
     * @param string string to be converted
     * @return byte[] bytes array
     */
    public static byte[] stringToBytes(String string)
    {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Concatenates two bytes array
     * @param firstArray byte[] First array
     * @param secondArray byte[] Second array
     * @return byte[] the concatenation of the two arrays
     */
    public static byte[] concatBytesArray(byte[] firstArray, byte[] secondArray)
    {
        byte[] concatArray = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, concatArray, 0, firstArray.length);
        System.arraycopy(secondArray, 0, concatArray, firstArray.length, secondArray.length);
        return concatArray;
    }

    /**
     * Encodes a string into SHA-256
     * @param string String to be hashed
     * @return byte[] the hash
     */
    public static byte[] encode(String string) {
        return Helper.encode(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Encodes a byte array into SHA-256
     * @param bytesArray bytes array to be hashed
     * @return byte[] the hash
     */
    public static byte[] encode(byte[] bytesArray) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(bytesArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
