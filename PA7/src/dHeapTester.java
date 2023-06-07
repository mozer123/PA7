import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class dHeapTester {

    private dHeap<Integer> maxHeap1;
    private dHeap<Integer> maxHeap2;
    private dHeap<Integer> maxHeap22;
    private dHeap<Integer> maxHeap;

    private dHeap<Integer> minHeap1;
    private dHeap<Integer> minHeap11;
    private dHeap<Integer> minHeap2;
    private dHeap<Integer> minHeap;

    private MyPriorityQueue<Integer> minMine;
    private PriorityQueue<Integer> minOfficial;


    @BeforeEach
    public void setup() {
        maxHeap1 = new dHeap<>();
        maxHeap2 = new dHeap<>(10);
        maxHeap22 = new dHeap<>(11);
        maxHeap = new dHeap<>(2, 10, true);

        minHeap1 = new dHeap<>();
        minHeap11 = new dHeap<>();
        minHeap2 = new dHeap<>(5);
        minHeap = new dHeap<>(3, 10, false);

        minMine = new MyPriorityQueue<>(10);
        minOfficial = new PriorityQueue<>(10);
    }

    @Test
    public void testPriorityQueue() {
        int numElements = 1000;

        for (int i = 0; i < numElements; i++) {
            int randomNumber = (int) (Math.random() * 100);

            minMine.offer(randomNumber);
            minOfficial.offer(randomNumber);
        }

        while (!minOfficial.isEmpty()) {
            assertEquals(minOfficial.poll(), minMine.poll());
        }
    }

    @Test
    public void testConstructors() {
        assertEquals(0, maxHeap1.size());
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap1.element();
        });

        assertEquals(0, maxHeap2.size());
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap2.element();
        });

        assertEquals(0, maxHeap.size());
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap.element();
        });

        // constructor with d=0 (Invalid value)
        assertThrows(IllegalArgumentException.class, () -> {
            maxHeap = new dHeap<>(0, 20, true);
        });
    }

    @Test
    public void testRemove() {
        maxHeap.add(20);
        maxHeap.add(5);
        maxHeap.add(10);
        maxHeap.add(15);


        assertEquals(20, maxHeap.remove());
        assertEquals(15, maxHeap.remove());
        assertEquals(10, maxHeap.remove());
        assertEquals(5, maxHeap.remove());

        assertEquals(0, maxHeap.size());
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap.remove();
        });
    }

    @Test
    public void testClear() {
        minHeap.add(5);
        minHeap.add(10);
        minHeap.add(15);

        assertEquals(5, minHeap.element());
        assertEquals(3, minHeap.size());

        minHeap.clear();
        assertEquals(0, minHeap.size());
        assertThrows(NoSuchElementException.class, () -> minHeap.remove());

        minHeap.clear();
        minHeap.add(12);
        assertEquals(12, minHeap.element());
        assertEquals(1, minHeap.size());

        minHeap.clear();
        assertEquals(0, minHeap.size());
    }

    @Test
    public void testEasyMin() {
        minHeap.add(11);
        minHeap.add(5);
        minHeap.add(19);
        minHeap.add(10);
        minHeap.add(12);
        minHeap.printHeap();

        assertEquals(5, minHeap.element());
        minHeap.remove();
        minHeap.printHeap();


        assertEquals(10, minHeap.element());
        minHeap.remove();
        assertEquals(11, minHeap.element());
        minHeap.remove();
        assertEquals(12, minHeap.element());
        minHeap.remove();
        assertEquals(19, minHeap.element());
        minHeap.remove();
        minHeap.printHeap();
        assertThrows(NoSuchElementException.class, () -> {
            minHeap.element();
        });

    }
    @Test
    public void testComplexMin() {
        minHeap.add(-12);
        minHeap.add(-2);
        minHeap.add(11);
        minHeap.add(-10);
        minHeap.add(5);
        minHeap.add(19);
        minHeap.add(-6);
        minHeap.add(-19);
        minHeap.add(-8);
        minHeap.add(-1);
        minHeap.add(10);
        minHeap.add(-12);

        assertEquals(-19, minHeap.element());
        minHeap.remove();
        assertEquals(-12, minHeap.element());
        minHeap.remove();
        assertEquals(-12, minHeap.element());
        minHeap.remove();
        assertEquals(-10, minHeap.element());
        minHeap.remove();
        assertEquals(-8, minHeap.element());
        minHeap.remove();
        assertEquals(-6, minHeap.element());
        minHeap.remove();
        assertEquals(-2, minHeap.element());
        minHeap.remove();
        assertEquals(-1, minHeap.element());
        minHeap.remove();
        assertEquals(5, minHeap.element());
        minHeap.remove();
        assertEquals(10, minHeap.element());
        minHeap.remove();
        assertEquals(11, minHeap.element());
        minHeap.remove();
        assertEquals(19, minHeap.element());
        minHeap.remove();
        assertThrows(NoSuchElementException.class, () -> {
            minHeap.element();
        });

    }

    @Test
    public void testEasyMax() {
        maxHeap.add(11);
        maxHeap.add(5);
        maxHeap.add(19);
        maxHeap.add(10);
        maxHeap.add(12);

        assertEquals(19, maxHeap.element());
        maxHeap.remove();
        assertEquals(12, maxHeap.element());
        maxHeap.remove();
        assertEquals(11, maxHeap.element());
        maxHeap.remove();
        assertEquals(10, maxHeap.element());
        maxHeap.remove();
        assertEquals(5, maxHeap.element());
        maxHeap.remove();
        assertThrows(NoSuchElementException.class, () -> {
            maxHeap.element();
        });

    }

}