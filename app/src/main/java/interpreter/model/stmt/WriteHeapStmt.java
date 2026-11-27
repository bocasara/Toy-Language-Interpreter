package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new StatementException("wH: variable " + varName + " is not defined");
        if (!(symTable.lookup(varName) instanceof RefValue refValue))
            throw new StatementException("wH: variable " + varName + " is not a reference");
        if (!heap.isDefined(refValue.address()))
            throw new StatementException("wH: address " + refValue.address() + " is not allocated in the heap");

        IValue value = exp.eval(symTable, heap);
        if (!value.getType().equals(refValue.locationType()))
            throw new StatementException("wH: type of expression does not match the location type of " + varName);

        heap.update(refValue.address(), value);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}
