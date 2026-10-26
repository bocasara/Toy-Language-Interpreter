package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record IfStatement(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException{
        var symTable = state.getSymTable();
        var condValue = cond.eval(symTable);
        if (condValue.IsTrue()) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }
        return state;
    }

    @Override
    public String toString() {
        return "if(" + cond.toString() + ") then(" + thenS.toString() + ") else(" + elseS.toString() + ")";
    }

}
