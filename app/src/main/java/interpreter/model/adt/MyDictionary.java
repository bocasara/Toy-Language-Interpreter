package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new java.util.HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("Key not defined: " + key);
        }
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (dictionary.containsKey(key)) {
            dictionary.put(key, value);
        } else {
            throw new ADTException("Cannot update. Key not defined: " + key);
        }
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("Cannot remove. Key not defined: " + key);
        }
        dictionary.remove(key);
    }

    @Override
    public Map<K, V> toMap() {
        return new java.util.HashMap<>(dictionary);
    }

    @Override
    public String toString(){
        return dictionary.toString();
    }
}