package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record CloseFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new MyException("closeFile " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("openRFile: " + fileName + " is not opened");
        BufferedReader reader = fileTable.lookup(fileName);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException("openRFile: cannot close " + fileName
                    + ": " + e.getMessage());
        }
        fileTable.remove(fileName);
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}
