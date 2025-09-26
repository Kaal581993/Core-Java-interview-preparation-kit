import java.util.*;  

public class IteratorExample {
    public static void main(String[] args) {
        // STEP 1: Create a collection (ArrayList is used here)
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Mango");

        // STEP 2: Get the iterator from the collection
        Iterator<String> itr = fruits.iterator();

        // STEP 3: Traverse using the iterator
        while (itr.hasNext()) {   // hasNext() checks if another element is available
            String fruit = itr.next();   // next() moves cursor forward and returns element
            System.out.println(fruit);

            // STEP 4: Conditionally remove an element
            if (fruit.equals("Banana")) {
                itr.remove();  // Safe removal during iteration
            }
        }

        System.out.println("Final List: " + fruits);
    }
}
