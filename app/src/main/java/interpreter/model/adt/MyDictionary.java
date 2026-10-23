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
    public V lookup(K key) {
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        if (isDefined(key)) {
            dictionary.put(key, value);
        } else {
            throw new ADTException("Key not defined: " + key);
        }
    }


}
