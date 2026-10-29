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

class CompStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executePushesStatementsInExecutionOrder() throws MyException {
        var first = new PrintStmt(new ValExp(new IntValue(1)));
        var second = new PrintStmt(new ValExp(new IntValue(2)));
        var state = state();

        assertEquals(state, new CompStmt(first, second).execute(state));
        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
    }

    @Test
    void toStringUsesCompoundSyntax() {
        var first = new NopStmt();
        var second = new NopStmt();

        assertEquals("(nop; nop)", new CompStmt(first, second).toString());
    }
}
