package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp(){
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n){
        return new ValExp(new IntValue(n));
    }

    private static IExp bool(boolean b){
        return new ValExp(new BoolValue(b));
    }

    @Test
    void addsTowNumbers() throws MyException{
        assertEquals(new IntValue(5), new ArithExp(ArithOp.ADD, num(2), num(3)).eval(symTable));
    }

    @Test
    void subtractsTwoNumbers() throws MyException{
        assertEquals(new IntValue(1), new ArithExp(ArithOp.SUB, num(3), num(2)).eval(symTable));
    }

    @Test
    void multipliesTwoNumbers() throws MyException{
        assertEquals(new IntValue(6), new ArithExp(ArithOp.MUL, num(2), num(3)).eval(symTable));
    }

    @Test
    void dividesTwoNumbers() throws MyException{
        assertEquals(new IntValue(2), new ArithExp(ArithOp.DIV, num(6), num(3)).eval(symTable));
    }

    @Test
    void throwsWhenDividingByZero() {
        ExpressionException e = assertThrows(ExpressionException.class, () -> {
            new ArithExp(ArithOp.DIV, num(6), num(0)).eval(symTable);
        });
        assertEquals("Division by zero", e.getMessage());
    }

    @Test
    void respectsOperatorPrecedence() throws MyException{
        IExp exp = new ArithExp(ArithOp.ADD, num(2), new ArithExp(ArithOp.MUL, num(3), num(4)));
        assertEquals(new IntValue(14), exp.eval(symTable));
    }

    @Test
    void  booleansAreNotAllowed() {
        ExpressionException e = assertThrows(ExpressionException.class, () -> {
            new ArithExp(ArithOp.ADD, bool(true), num(3)).eval(symTable);
        });
        assertEquals("Left operand is not an integer", e.getMessage());
    }

    @Test
    void booleansAreNotAllowed2() {
        ExpressionException e = assertThrows(ExpressionException.class, () -> {
            new ArithExp(ArithOp.ADD, num(3), bool(false)).eval(symTable);
        });
        assertEquals("Right operand is not an integer", e.getMessage());
    }

    @Test
    void usesVariablesFromSymbolTable() throws MyException{
        symTable.put("x", new IntValue(5));
        symTable.put("y", new IntValue(3));
        IExp exp = new ArithExp(ArithOp.ADD, new VarExp("x"), new VarExp("y"));
        assertEquals(new IntValue(8), exp.eval(symTable));
    }

    @Test
    void evaluationDoesNotChangeSymbolTable() throws MyException{
        symTable.put("x", new IntValue(5));
        IExp exp = new ArithExp(ArithOp.ADD, new VarExp("x"), num(3));
        exp.eval(symTable);
        assertEquals(new IntValue(5), symTable.lookup("x"));
    }

}
