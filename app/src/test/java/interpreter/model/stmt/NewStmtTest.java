package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executeAllocatesValueAndPointsVariableAtIt() throws MyException {
        var state = state();
        state.getSymTable().put("v", new RefValue(0, new IntType()));

        assertEquals(state, new NewStmt("v", new ValExp(new IntValue(42))).execute(state));

        var ref = (RefValue) state.getSymTable().lookup("v");
        assertTrue(ref.address() != 0);
        assertEquals(new IntType(), ref.locationType());
        assertEquals(new IntValue(42), state.getHeap().get(ref.address()));
    }

    @Test
    void executeRejectsUndefinedVariable() {
        var exception = assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValExp(new IntValue(1))).execute(state()));
        assertEquals("new: variable v is not defined", exception.getMessage());
    }

    @Test
    void executeRejectsNonReferenceVariable() {
        var state = state();
        state.getSymTable().put("v", new IntValue(1));

        var exception = assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValExp(new IntValue(1))).execute(state));
        assertEquals("new: variable v is not a reference", exception.getMessage());
    }

    @Test
    void executeRejectsTypeMismatch() {
        var state = state();
        state.getSymTable().put("v", new RefValue(0, new IntType()));

        var exception = assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValExp(new BoolValue(true))).execute(state));
        assertEquals("new: type of expression does not match the location type of v", exception.getMessage());
    }

    @Test
    void toStringUsesNewSyntax() {
        assertEquals("new(v, 42)", new NewStmt("v", new ValExp(new IntValue(42))).toString());
    }
}
