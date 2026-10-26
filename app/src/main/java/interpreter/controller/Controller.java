package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

import java.util.prefs.PreferencesFactory;

public class Controller {
    private IRepository repository;
    private boolean displayFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = true;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException{
        IStack<IStmt> stack = state.getExeStack();

        if(stack.isEmpty()){
            throw new StatementException("PrgState stack is empty");
        }

        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException{
        PrgState prg = repository.getCrtPrg();

        if(displayFlag){
            System.out.println(prg.toString());
        }

        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);

            if(displayFlag){
                System.out.println(prg.toString());
            }
        }
    }
}