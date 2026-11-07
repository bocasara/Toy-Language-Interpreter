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

class RelationalExpTest {
    private IDictionary<String, IValue> symtable;


    @BeforeEach
    void setUp() {
        symtable = new MyDictionary<>();
    }

    private static IExp num(int n){
        return new ValExp(new IntValue(n));
    }

    private static IExp bool(boolean b){
        return new ValExp(new BoolValue(b));
    }

    @Test
    void twoEqualNumbers() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, num(2), num(2)).eval(symtable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, num(2), num(3)).eval(symtable));
    }

    @Test
    void twoNotEqualNumbers() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, num(2), num(2)).eval(symtable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, num(2), num(3)).eval(symtable));
    }

    @Test
    void greaterThan() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(4), num(3)).eval(symtable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(3), num(4)).eval(symtable));
    }
    @Test
    void greaterOrEqual() throws MyException {
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, num(4), num(3)).eval(symtable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(3), num(4)).eval(symtable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, num(3), num(3)).eval(symtable));
    }

    @Test
    void lessThan() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, num(4), num(3)).eval(symtable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, num(3), num(4)).eval(symtable));
    }
    @Test
    void lessOrEqual() throws MyException {
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, num(4), num(3)).eval(symtable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(3), num(4)).eval(symtable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, num(3), num(3)).eval(symtable));
    }

    @Test
    void throwsWhenRightOperandNotInt() throws MyException {
        ExpressionException e =  assertThrows(ExpressionException.class, () -> {
            new RelationalExp(RelOp.EQ, bool(true), num(3)).eval(symtable);
        });

        assertEquals("First operand is not an integer.", e.getMessage());
    }

    @Test
    void throwsWhenLeftOperandNotInt() throws MyException {
        ExpressionException e =  assertThrows(ExpressionException.class, () -> {
            new RelationalExp(RelOp.EQ, num(4), bool(true)).eval(symtable);
        });

        assertEquals("Second operand is not an integer.", e.getMessage());
    }



}