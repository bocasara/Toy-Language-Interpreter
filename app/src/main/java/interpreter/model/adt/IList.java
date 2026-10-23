package interpreter.model.adt;

public interface IList<T> {
    void add(T value);
    T get(int index);
    int size();

}
