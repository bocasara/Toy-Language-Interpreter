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

class WriteHeapStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executeUpdatesValueAtAddress() throws MyException {
        var state = state();
        int address = state.getHeap().allocate(new IntValue(1));
        state.getSymTable().put("v", new RefValue(address, new IntType()));

        assertEquals(state, new WriteHeapStmt("v", new ValExp(new IntValue(7))).execute(state));
        assertEquals(new IntValue(7), state.getHeap().get(address));
    }

    @Test
    void executeRejectsUndefinedVariable() {
        var exception = assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValExp(new IntValue(1))).execute(state()));
        assertEquals("wH: variable v is not defined", exception.getMessage());
    }

    @Test
    void executeRejectsNonReferenceVariable() {
        var state = state();
        state.getSymTable().put("v", new IntValue(1));

        var exception = assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValExp(new IntValue(1))).execute(state));
        assertEquals("wH: variable v is not a reference", exception.getMessage());
    }

    @Test
    void executeRejectsUnallocatedAddress() {
        var state = state();
        state.getSymTable().put("v", new RefValue(99, new IntType()));

        var exception = assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValExp(new IntValue(1))).execute(state));
        assertEquals("wH: address 99 is not allocated in the heap", exception.getMessage());
    }

    @Test
    void executeRejectsTypeMismatch() {
        var state = state();
        int address = state.getHeap().allocate(new IntValue(1));
        state.getSymTable().put("v", new RefValue(address, new IntType()));

        var exception = assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValExp(new BoolValue(true))).execute(state));
        assertEquals("wH: type of expression does not match the location type of v", exception.getMessage());
    }

    @Test
    void toStringUsesWriteHeapSyntax() {
        assertEquals("wH(v, 42)", new WriteHeapStmt("v", new ValExp(new IntValue(42))).toString());
    }
}
