/**
 * ExampleVectors.java
 *
 * Purpose:
 *   Demonstrates usage and differences between ArrayList, LinkedList, Vector, and Stack.
 *
 * Changelog:
 *   2025-09-24 - created: added demos for capacity, iteration, Enumeration, and stack ops.
 *
 * Note:
 *   This is a compact demo for learning. In production prefer:
 *     - ArrayList for non-concurrent dynamic arrays
 *     - Collections.synchronizedList(...) or concurrent collections for concurrency
 *     - ArrayDeque or Deque for stack/queue semantics instead of Stack/Vector
 */
import java.util.*;

public class ExampleVectors {
    public static void main(String[] args) {
        // Setup: create data structures
        Vector<Integer> vec = new Vector<>(2, 5);            // initial capacity 2, capacityIncrement 5
        ArrayList<Integer> al = new ArrayList<>(2);          // initial capacity 2
        LinkedList<Integer> ll = new LinkedList<>();         // default empty linked list
        Stack<Integer> st = new Stack<>();                   // legacy stack (extends Vector)

        // Fill them with same values for comparison
        for (int i = 1; i <= 5; i++) {
            vec.add(i);
            al.add(i);
            ll.add(i);
            st.push(i);
        }

        // Vector specifics: capacity-related info and legacy enumeration
        System.out.println("Vector size=" + vec.size() + ", capacity=" + vec.capacity());
        vec.ensureCapacity(20);
        System.out.println("After ensureCapacity(20), capacity=" + vec.capacity());
        vec.trimToSize();
        System.out.println("After trimToSize(), capacity=" + vec.capacity());

        // Iteration differences
        System.out.print("Vector via Enumeration: ");
        Enumeration<Integer> en = vec.elements();
        while (en.hasMoreElements()) {
            System.out.print(en.nextElement() + " ");
        }
        System.out.println();

        System.out.print("Vector via Iterator (fail-fast): ");
        Iterator<Integer> it = vec.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // Demonstrate fail-fast behavior (comment/uncomment to test - will throw ConcurrentModificationException)
        // for (Integer x : vec) { if (x == 3) vec.remove(x); } // simultaneous modification -> CME with iterator

        // LinkedList specific deque methods
        ll.addFirst(0);
        ll.addLast(6);
        System.out.println("LinkedList getFirst()=" + ll.getFirst() + ", getLast()=" + ll.getLast());

        // Stack specifics
        System.out.println("Stack before pop: " + st);
        int top = st.pop();
        System.out.println("Popped from stack: " + top + ", stack now: " + st);

        // Synchronization vs external sync demonstration (example patterns)
        List<Integer> synchronizedArrayList = Collections.synchronizedList(new ArrayList<>());
        synchronizedArrayList.addAll(al);
        synchronized(synchronizedArrayList) {
            Iterator<Integer> sit = synchronizedArrayList.iterator();
            while (sit.hasNext()) {
                System.out.print(sit.next() + " ");
            }
            System.out.println();
        }

        // Final sizes
        System.out.println("Final sizes: ArrayList=" + al.size() + ", Vector=" + vec.size() + ", LinkedList=" + ll.size());
    }
}
