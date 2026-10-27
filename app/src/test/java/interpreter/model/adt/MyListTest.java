package interpreter.model.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyListTest {
    private IList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAddsElement(){
        list.add(1);
        assert list.size() == 1;
    }

    @Test
    void getReturnsCorrectElement(){
        list.add(1);
        list.add(2);
        assert list.get(0) == 1;
        assert list.get(1) == 2;
    }

    @Test
    void sizeReturnsCorrectSize(){
        list.add(1);
        list.add(2);
        assert list.size() == 2;
    }

    @Test
    void toStringReturnsCorrectRepresentation(){
        list.add(1);
        list.add(2);
        String representation = list.toString();
        assert representation.contains("1");
        assert representation.contains("2");
    }
}
