import java.util.*;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        // Add elements
        linkedList.add(10);
        linkedList.add(20);
        linkedList.addFirst(5);  // LinkedList specific
        linkedList.addLast(30);

        // Access
        System.out.println("First: " + linkedList.getFirst());
        System.out.println("Last: " + linkedList.getLast());

        // Remove
        linkedList.removeFirst();
        linkedList.removeLast();

        // Iterate
        for(int val : linkedList){
            System.out.println(val);
        }
    }
}
