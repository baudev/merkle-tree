import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuditorClassTest {

    @Test
    void isMember() throws Exception {

        AuditorClass auditorClass = new AuditorClass(new LogServer("logs.txt"));

        assertTrue(auditorClass.isMember("SECOND EVENT"));
        assertFalse(auditorClass.isMember("RANDOM VALUE"));

    }
}