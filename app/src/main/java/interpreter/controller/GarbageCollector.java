package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector(){}

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
       return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    // unsafeGarbageCollector removes all the entries from the heap that are not referenced by any variable in the symbol table
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));     }

    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr, Map<Integer, IValue> heap) {
        Set<Integer> reachableAddresses = getReachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Set<Integer> getReachableAddresses(Set<Integer> symTableAddr, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(symTableAddr);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Integer address : new HashSet<>(reachable)) {
                if (heap.get(address) instanceof RefValue ref && reachable.add(ref.address())) {
                    changed = true;
                }
            }
        }
        return reachable;
    }
}
