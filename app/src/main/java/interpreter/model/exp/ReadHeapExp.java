package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        if (!(exp.eval(symTable, heap) instanceof RefValue refValue))
            throw new ExpressionException("rH: expression " + exp + " is not a reference");
        if (!heap.isDefined(refValue.address()))
            throw new ExpressionException("rH: address " + refValue.address() + " is not allocated in the heap");
        return heap.get(refValue.address());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}
