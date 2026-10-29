package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IfStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executePushesThenStatementForTrueCondition() throws MyException {
        var thenStatement = new NopStmt();
        var elseStatement = new PrintStmt(new ValExp(new BoolValue(false)));
        var state = state();

        assertEquals(state, new IfStmt(new ValExp(new BoolValue(true)), thenStatement, elseStatement).execute(state));
        assertEquals(thenStatement, state.getExeStack().pop());
    }

    @Test
    void executePushesElseStatementForFalseCondition() throws MyException {
        var thenStatement = new NopStmt();
        var elseStatement = new PrintStmt(new ValExp(new BoolValue(false)));
        var state = state();

        new IfStmt(new ValExp(new BoolValue(false)), thenStatement, elseStatement).execute(state);
        assertEquals(elseStatement, state.getExeStack().pop());
    }

    @Test
    void executeRejectsNonBooleanCondition() {
        var state = state();

        var exception = assertThrows(StatementException.class,
                () -> new IfStmt(new ValExp(new IntValue(1)), new NopStmt(), new NopStmt()).execute(state));
        assertEquals("Condition is not a boolean", exception.getMessage());
    }

    @Test
    void toStringUsesConditionalSyntax() {
        var thenStatement = new NopStmt();
        var elseStatement = new NopStmt();

        assertEquals("if(true) then(nop) else(nop)",
                new IfStmt(new ValExp(new BoolValue(true)), thenStatement, elseStatement).toString());
    }
}
