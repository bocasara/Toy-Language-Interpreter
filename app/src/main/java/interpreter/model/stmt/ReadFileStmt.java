package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        if(!symTable.isDefined(varName))
            throw new StatementException("readfile: variable " + varName + " is not declared");
        if(!(symTable.lookup(varName).getType() instanceof IntType))
            throw new StatementException("readfile: variable " + varName + " is not an integer");
        if(!(exp.eval(symTable) instanceof StringValue fileName))
            throw new StatementException("readfile: expression " + exp.eval(symTable) + " is not a string");

        var fileTable = state.getFileTable();
        if(!fileTable.isDefined(fileName))
            throw new StatementException("readfile: file " + fileName + " is not declared");

        BufferedReader reader = fileTable.lookup(fileName);
        try{
            String line = reader.readLine();
            int value = (line == null || line.isEmpty()) ? 0 : Integer.parseInt(line);
            symTable.update(varName, new IntValue(value));
        }catch (IOException e){
            throw new StatementException("readfile: cannot read from " + fileName
                + ": " + e.getMessage());
        }
        catch (NumberFormatException e){
            throw new StatementException("readfile: " + fileName + " does not contain an integer");
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
