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

public class LogicExpTest {
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
    void comparesTwoBooleans() throws MyException{
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, bool(true), bool(true)).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, bool(false), bool(false)).eval(symTable));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, bool(true), bool(false)).eval(symTable));
    }

    @Test
    void comparesTwoBooleansWithNot() throws MyException{
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.NOT, bool(true), null).eval(symTable));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.NOT, bool(false), null).eval(symTable));
    }

    @Test
    void respectsOperatorPrecedence() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, new LogicExp(LogicOp.AND, bool(true), bool(false)), bool(true)).eval(symTable));
    }

    @Test
    void throwsWhenLeftOperandIsNotBoolean() {
        ExpressionException e = assertThrows(ExpressionException.class, () -> {
            new LogicExp(LogicOp.AND, num(1), bool(true)).eval(symTable);
        });
        assertEquals("Left operand is not a boolean", e.getMessage());
    }

    @Test
    void throwsWhenRightOperandIsNotBoolean() {
        ExpressionException e = assertThrows(ExpressionException.class, () -> {
            new LogicExp(LogicOp.AND, bool(true), num(1)).eval(symTable);
        });
        assertEquals("Right operand is not a boolean", e.getMessage());
    }

}
