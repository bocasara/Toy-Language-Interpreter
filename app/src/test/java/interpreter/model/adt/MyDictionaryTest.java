package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp(){
        dict = new MyDictionary<>();
    }

    @Test
    void keyNotDefined(){
        assertFalse(dict.isDefined("key"));
    }

    @Test
    void keyDefined(){
        dict.put("key", 1);
        assertTrue(dict.isDefined("key")); // Simplified double negative
    }

    @Test
    void lookupReturnsValue() throws ADTException {
        dict.put("key", 1);
        assertEquals(1, dict.lookup("key")); // Replaced standard assert
    }

    @Test
    void lookupUndefinedKeyThrows(){
        assertThrows(ADTException.class, () -> dict.lookup("key"));
    }

    @Test
    void updateChangesValue() throws ADTException {
        dict.put("key", 1);
        dict.update("key", 2);
        assertEquals(2, dict.lookup("key")); // Replaced standard assert
    }

    @Test
    void updateOfMissingKey(){
        assertThrows(ADTException.class, () -> dict.update("key", 1));
    }

    @Test
    void updateDoesNotAddKeys(){
        try{
            dict.update("key", 1);
        } catch (ADTException e){
        }
        assertFalse(dict.isDefined("key"));
    }

    @Test
    void toStringContainsKeysAndValues() throws ADTException {
        dict.put("key1", 1);
        dict.put("key2", 2);
        String str = dict.toString();

        assertTrue(str.contains("key1") && str.contains("1") &&
                str.contains("key2") && str.contains("2"));
    }
}