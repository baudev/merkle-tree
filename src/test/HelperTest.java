import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    public void stringToBytes()
    {
        String value = "test";
        byte[] result = Helper.stringToBytes(value);
        String stringResult = new String(result, StandardCharsets.UTF_8);
        assertEquals(value, stringResult);
    }

    @Test
    public void encode()
    {
        // checks there is no error
        Helper.encode("test");
    }

    @Test
    public void concatBytesArray()
    {
        // checks concatenation work
        byte[] array1 = new byte[]{1};
        byte[] array2 = new byte[]{38};
        byte[] arrayResult = new byte[]{1, 38};
        assertArrayEquals(arrayResult, Helper.concatBytesArray(array1, array2));


        String firstValue = "first";
        String secondValue = "second";
        byte[] result = Helper.concatBytesArray(Helper.stringToBytes(firstValue), Helper.stringToBytes(secondValue));
        String stringResult = new String(result, StandardCharsets.UTF_8);
        assertEquals(firstValue + secondValue, stringResult);
    }

}