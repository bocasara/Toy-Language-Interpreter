package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyHeapTest {
    private IHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsAddress() {
        int address = heap.allocate(new IntValue(10));
        assertTrue(heap.isDefined(address));
    }

    @Test
    void allocateGivesDistinctAddresses() {
        int first = heap.allocate(new IntValue(1));
        int second = heap.allocate(new IntValue(2));
        assertNotEquals(first, second);
    }

    @Test
    void allocateStartsAtOne() {
        assertEquals(1, heap.allocate(new IntValue(1)));
    }

    @Test
    void getReturnsStoredValue() throws ADTException {
        int address = heap.allocate(new IntValue(42));
        assertEquals(new IntValue(42), heap.get(address));
    }

    @Test
    void isDefinedFalseForUnknownAddress() {
        assertFalse(heap.isDefined(999));
    }

    @Test
    void updateChangesValue() throws ADTException {
        int address = heap.allocate(new IntValue(1));
        heap.update(address, new IntValue(2));
        assertEquals(new IntValue(2), heap.get(address));
    }

    @Test
    void updateOfMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.update(123, new IntValue(1)));
    }

    @Test
    void getContentReflectsAllocations() {
        int address = heap.allocate(new IntValue(7));
        Map<Integer, IValue> content = heap.getContent();
        assertEquals(1, content.size());
        assertEquals(new IntValue(7), content.get(address));
    }

    @Test
    void setContentReplacesHeap() throws ADTException {
        int address = heap.allocate(new IntValue(5));
        heap.setContent(Map.of(address, new IntValue(99)));
        assertEquals(new IntValue(99), heap.get(address));
    }

    @Test
    void storesValuesOfDifferentTypes() throws ADTException {
        int refAddress = heap.allocate(new IntValue(3));
        IValue stored = new interpreter.model.value.RefValue(refAddress, new IntType());
        int address = heap.allocate(stored);
        assertEquals(stored, heap.get(address));
    }
}
