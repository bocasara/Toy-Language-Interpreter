package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NopStmtTest {
    @Test
    void executeReturnsUnchangedState() throws MyException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());

        assertEquals(state, new NopStmt().execute(state));
    }

    @Test
    void toStringReturnsNop() {
        assertEquals("nop", new NopStmt().toString());
    }
}
