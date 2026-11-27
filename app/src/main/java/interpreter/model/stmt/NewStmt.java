package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record NewStmt(String varName, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StatementException("new: variable " + varName + " is not defined");
        if (!(symTable.lookup(varName).getType() instanceof RefType refType))
            throw new StatementException("new: variable " + varName + " is not a reference");

        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(refType.inner()))
            throw new StatementException("new: type of expression does not match the location type of " + varName);

        int address = state.getHeap().allocate(value);
        symTable.update(varName, new RefValue(address, value.getType()));
        return state;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp + ")";
    }
}
