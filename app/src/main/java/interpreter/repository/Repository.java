package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private final List<PrgState> prgStates;

    public Repository(PrgState initialPrgState) {
        this.prgStates = new ArrayList<>();
        this.prgStates.add(initialPrgState);
    }

    @Override
    public PrgState getCrtPrg() {
        if (prgStates.isEmpty()) {
            throw new RuntimeException("No program states available.");
        }
        return prgStates.getFirst();
    }
}
