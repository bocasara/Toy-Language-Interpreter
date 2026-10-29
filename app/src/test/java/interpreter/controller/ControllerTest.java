package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    // Ex1: int v; v = 2; print(v)
    private static IStmt example1() {
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    // Ex2: int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)
    private static IStmt example2() {
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp(ArithOp.ADD, new ValExp(new IntValue(2)),
                                        new ArithExp(ArithOp.MUL, new ValExp(new IntValue(3)),
                                                new ValExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp(ArithOp.ADD, new VarExp("a"),
                                                new ValExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));
    }

    // Ex3: bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)
    private static IStmt example3() {
        return new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValExp(new IntValue(2))),
                                        new AssignStmt("v", new ValExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
    }

    private static PrgState run(IStmt program) throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        Controller controller = new Controller(new Repository(state));
        controller.setDisplayFlag(false);
        controller.allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(example1());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(example2());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(example3());
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        Controller controller = new Controller(new Repository(state));
        controller.setDisplayFlag(false);
        controller.allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }
}
