package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements IExp {

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if(!(left.eval(symTable) instanceof interpreter.model.value.BoolValue(boolean b1)))
            throw new ExpressionException("Left operand is not a boolean");

        if (op == LogicOp.NOT) {
            return new BoolValue(!b1);
        }

        if(!(right.eval(symTable) instanceof interpreter.model.value.BoolValue(boolean b2)))
            throw new ExpressionException("Right operand is not a boolean");
        return switch (op) {
            case AND -> new BoolValue(b1 && b2);
            case OR -> new BoolValue(b1 || b2);
            default -> throw new ExpressionException("Invalid logical operator");
        };
    }


    @Override
    public String toString() {
        if (op == LogicOp.NOT) {
            return "(" + op + " " + left + ")";
        }
        return "(" + left + " " + op + " " + right + ")";
    }

}
