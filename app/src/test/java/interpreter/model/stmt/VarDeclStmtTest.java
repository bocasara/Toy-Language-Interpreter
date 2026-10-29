package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VarDeclStmtTest {
    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executeDeclaresIntegerWithDefaultValue() throws MyException {
        var state = state();

        assertEquals(state, new VarDeclStmt("x", new IntType()).execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void executeDeclaresBooleanWithDefaultValue() throws MyException {
        var state = state();

        new VarDeclStmt("flag", new BoolType()).execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("flag"));
    }

    @Test
    void executeRejectsDuplicateVariable() {
        var state = state();
        state.getSymTable().put("x", new IntValue(1));

        var exception = assertThrows(StatementException.class,
                () -> new VarDeclStmt("x", new IntType()).execute(state));
        assertEquals("Variable x is already defined.", exception.getMessage());
    }

    @Test
    void toStringUsesTypeAndName() {
        assertEquals("int x", new VarDeclStmt("x", new IntType()).toString());
    }
}
