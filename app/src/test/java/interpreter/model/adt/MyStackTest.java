package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void pushAddsElement() {
        stack.push(1);
        assert !stack.isEmpty();
    }

    @Test
    void popRemovesAndReturnsElement() throws ADTException {
        stack.push(1);
        int value = stack.pop();
        assert value == 1;
        assert stack.isEmpty();
    }

    @Test
    void popOnEmptyStackThrows() {
        try {
            stack.pop();
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void isEmptyReturnsTrueForNewStack() {
        assert stack.isEmpty();
    }

    @Test
    void toStringReturnsCorrectRepresentation() {
        stack.push(1);
        stack.push(2);
        String representation = stack.toString();
        assert representation.contains("1");
        assert representation.contains("2");
    }

}
