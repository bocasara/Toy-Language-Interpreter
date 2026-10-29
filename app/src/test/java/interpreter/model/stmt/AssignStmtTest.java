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

class AssignStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executeUpdatesDefinedVariable() throws MyException {
        var state = state();
        state.getSymTable().put("x", new IntValue(0));

        assertEquals(state, new AssignStmt("x", new ValExp(new IntValue(42))).execute(state));
        assertEquals(new IntValue(42), state.getSymTable().lookup("x"));
    }

    @Test
    void executeRejectsUndefinedVariable() {
        var state = state();

        var exception = assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValExp(new IntValue(42))).execute(state));
        assertEquals("Variable x is not defined.", exception.getMessage());
    }

    @Test
    void executeRejectsMismatchedType() {
        var state = state();
        state.getSymTable().put("flag", new BoolValue(false));

        var exception = assertThrows(StatementException.class,
                () -> new AssignStmt("flag", new ValExp(new IntValue(1))).execute(state));
        assertEquals("Type of expression does not match type of variable flag.", exception.getMessage());
    }

    @Test
    void toStringUsesAssignmentSyntax() {
        assertEquals("x = 42", new AssignStmt("x", new ValExp(new IntValue(42))).toString());
    }
}
