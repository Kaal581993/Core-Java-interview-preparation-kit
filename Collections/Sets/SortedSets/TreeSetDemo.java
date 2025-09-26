import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();

        treeSet.add(50);
        treeSet.add(10);
        treeSet.add(30);
        treeSet.add(20);

        // Iteration gives sorted order
        System.out.println("TreeSet elements: " + treeSet);

        // SortedSet methods
        System.out.println("First: " + treeSet.first());
        System.out.println("Last: " + treeSet.last());
        System.out.println("HeadSet (<30): " + treeSet.headSet(30));
        System.out.println("TailSet (>=20): " + treeSet.tailSet(20));
        System.out.println("SubSet (10 to 30): " + treeSet.subSet(10, 30));
        
    }
}


// 3️⃣ SortedSet (Interface)

// Definition: Interface that extends Set, keeps elements in sorted order.

// Implementation: Most commonly implemented by TreeSet.

// Nulls: Does NOT allow null (throws NullPointerException if inserted).

// Important Methods
// E first();             // Returns lowest element
// E last();              // Returns highest element
// SortedSet<E> headSet(E toElement);   // Elements strictly less than given
// SortedSet<E> tailSet(E fromElement); // Elements greater or equal
// SortedSet<E> subSet(E from, E to);   // Elements between range
// Comparator<? super E> comparator(); // Comparator used (or null for natural ordering)
