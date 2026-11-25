package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WhileStmtTest {

    @TempDir
    Path dir;

    private PrgState state() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executePushesBodyThenLoopWhenConditionTrue() throws MyException {
        var body = new PrintStmt(new ValExp(new IntValue(1)));
        var state = state();
        var loop = new WhileStmt(new ValExp(new BoolValue(true)), body);

        assertEquals(state, loop.execute(state));
        assertEquals(body, state.getExeStack().pop());  // body runs first
        assertEquals(loop, state.getExeStack().pop());   // loop re-tests after the body
    }

    @Test
    void executeDoesNotPushAnythingWhenConditionFalse() throws MyException {
        var body = new PrintStmt(new ValExp(new IntValue(1)));
        var state = state();
        var loop = new WhileStmt(new ValExp(new BoolValue(false)), body);

        loop.execute(state);
        assertFalse(state.getExeStack().toList().contains(body));
        assertFalse(state.getExeStack().toList().contains(loop));
    }

    @Test
    void executeRejectsNonBooleanCondition() {
        var exception = assertThrows(StatementException.class,
                () -> new WhileStmt(new ValExp(new IntValue(1)), new NopStmt()).execute(state()));
        assertEquals("Condition is not a boolean", exception.getMessage());
    }

    @Test
    void toStringUsesWhileSyntax() {
        assertEquals("while(true) { nop }",
                new WhileStmt(new ValExp(new BoolValue(true)), new NopStmt()).toString());
    }

    @Test
    void loopCountsDownAndPrintsEachValue() throws MyException {
        // int x; x = 3; while (x > 0) { print(x); x = x - 1 }
        IStmt body = new CompStmt(
                new PrintStmt(new VarExp("x")),
                new AssignStmt("x", new ArithExp(ArithOp.SUB, new VarExp("x"), new ValExp(new IntValue(1)))));
        IStmt program = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValExp(new IntValue(3))),
                        new WhileStmt(new RelationalExp(RelOp.GT, new VarExp("x"), new ValExp(new IntValue(0))), body)));

        PrgState state = PrgState.of(program);
        Controller controller = new Controller(
                new Repository(state, dir.resolve("log.txt").toString()), false);
        controller.allStep();

        var out = state.getOut();
        assertEquals(3, out.size());
        assertEquals(new IntValue(3), out.get(0));
        assertEquals(new IntValue(2), out.get(1));
        assertEquals(new IntValue(1), out.get(2));
    }
}
