package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IType;

public record BoolValue(boolean val) implements IValue  {

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean IsTrue() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
