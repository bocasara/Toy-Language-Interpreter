package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static interpreter.controller.GarbageCollector.safeGarbageCollector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GarbageCollectorTest {

    // A cell reachable only *through* another heap cell must survive.
    // heap: {1 -> 20, 2 -> Ref(1), 3 -> 30}, roots {2, 3}
    // Address 1 is reachable as 2 -> Ref(1) -> 1, so all three cells are kept.
    @Test
    void keepsCellReachableThroughAnotherHeapCell() {
        Map<Integer, IValue> heap = new HashMap<>();
        heap.put(1, new IntValue(20));
        heap.put(2, new RefValue(1, new IntType()));
        heap.put(3, new IntValue(30));
        Set<Integer> roots = Set.of(2, 3);

        Map<Integer, IValue> result = safeGarbageCollector(roots, heap);

        assertEquals(3, result.size());
        assertTrue(result.containsKey(1));
        assertEquals(new IntValue(20), result.get(1));
        assertTrue(result.containsKey(2));
        assertTrue(result.containsKey(3));
    }

    // An unreachable cell is collected even when it points at a live cell.
    // heap: {1 -> 10, 2 -> Ref(1), 3 -> 5}, roots {1, 3}
    // Nothing points at address 2, so cell 2 is garbage and is dropped.
    @Test
    void collectsGarbageCellThatPointsAtALiveCell() {
        Map<Integer, IValue> heap = new HashMap<>();
        heap.put(1, new IntValue(10));
        heap.put(2, new RefValue(1, new IntType()));
        heap.put(3, new IntValue(5));
        Set<Integer> roots = Set.of(1, 3);

        Map<Integer, IValue> result = safeGarbageCollector(roots, heap);

        assertEquals(2, result.size());
        assertFalse(result.containsKey(2));
        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(3));
    }
}
