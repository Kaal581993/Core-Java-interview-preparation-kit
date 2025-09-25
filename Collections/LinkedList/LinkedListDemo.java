import java.util.*;

public class LinkedListDemo {
    public static void main(String[] args) {
        
        // ✅ LinkedList creation
        LinkedList<String> list = new LinkedList<>();
        
        System.out.println("Initial List is empty: " + list);

        // ✅ Adding elements
        list.add("Apple");       // Adds to the end
        list.add("Banana");
        list.add("Cherry");
        list.addFirst("Mango");  // Adds at the beginning
        list.addLast("Orange");  // Adds at the end
        list.add(2, "Grapes");   // Adds at index 2
        list.add("Banana");      // Allows duplicates


        // ✅ Displaying LinkedList
        System.out.println("After List Initialization LinkedList: " + list);
        
        // ✅ Accessing elements
        System.out.println("First Element: " + list.getFirst());
        System.out.println("Last Element: " + list.getLast());
        System.out.println("Element at index 2: " + list.get(2));
        
        // ✅ Removing elements
        list.removeFirst();     // Removes first element
        System.out.println("After 1st Element Removal: " + list);

        list.removeLast();      // Removes last element
        System.out.println("After Last Element Removals: " + list);

        list.remove("Banana");  // Removes by value
        System.out.println("After Banana Removals: " + list);
        
        // ✅ Iterating
        System.out.print("Iteration using Iterator: ");
        Iterator<String> it = list.iterator();
        // Using Iterator to traverse the list
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.print("Iteration using for-each loop: ");
        for (String fruit : list) {
            System.out.print(fruit + " ");
        }
        System.out.println();

        // ✅ Using ListIterator
        ListIterator<String> listIt = list.listIterator();
        System.out.print("Iteration using ListIterator: ");
        while (listIt.hasNext()) {
            System.out.print(listIt.next() + " ");
        }
        System.out.println();

        // ✅ Using Deque methods
        list.push("Pineapple");  // Adds at the beginning
        System.out.println("After Adding Pineapple in the List using Push: " + list);


        
        System.out.println();
        
        // ✅ Using Deque methods
        list.push("Kiwi");   // Adds at the beginning
                System.out.println("After Adding Kiwi in the List using Push: " + list);

        list.pop();          // Removes first element
        System.out.println("After pop: " + list);
        
        // ✅ Queue methods
        list.offer("Pineapple");  // Adds to the end        
        System.out.println("After offer: " + list);

        list.poll();              // Removes first element
        System.out.println("After poll: " + list);
    }
}
