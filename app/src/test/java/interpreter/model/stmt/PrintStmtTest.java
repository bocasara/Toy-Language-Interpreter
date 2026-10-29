package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintStmtTest {
    @Test
    void executeAddsEvaluatedValueToOutput() throws MyException {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());

        assertEquals(state, new PrintStmt(new ValExp(new IntValue(7))).execute(state));
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    @Test
    void toStringUsesPrintSyntax() {
        assertEquals("print(7)", new PrintStmt(new ValExp(new IntValue(7))).toString());
    }
}
