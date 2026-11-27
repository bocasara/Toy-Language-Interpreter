package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadHeapExpTest {

    @Test
    void evalReturnsValueStoredAtAddress() throws MyException {
        IHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(5));
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("v", new RefValue(address, new IntType()));

        assertEquals(new IntValue(5), new ReadHeapExp(new VarExp("v")).eval(symTable, heap));
    }

    @Test
    void evalRejectsNonReferenceExpression() {
        var exception = assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new ValExp(new IntValue(1))).eval(new MyDictionary<>(), new MyHeap()));
        assertEquals("rH: expression 1 is not a reference", exception.getMessage());
    }

    @Test
    void evalRejectsUnallocatedAddress() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        symTable.put("v", new RefValue(99, new IntType()));

        var exception = assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new VarExp("v")).eval(symTable, new MyHeap()));
        assertEquals("rH: address 99 is not allocated in the heap", exception.getMessage());
    }

    @Test
    void toStringUsesReadHeapSyntax() {
        assertEquals("rH(v)", new ReadHeapExp(new VarExp("v")).toString());
    }
}
