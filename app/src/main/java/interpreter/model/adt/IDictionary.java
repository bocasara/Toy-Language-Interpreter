package interpreter.model.adt;

public interface IDictionary<K, V>
{
    boolean isDefined(K key);
    V lookup(K key);
    void put(K key, V value);
    void update(K key, V value);

}
