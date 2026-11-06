package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

}