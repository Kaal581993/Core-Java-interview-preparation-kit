import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {
        // Create a HashMap
        Map<Integer, String> map = new HashMap<>();

        // Insert key-value pairs
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Orange");
        map.put(4, "Grapes");

        // Replace value for existing key
        map.put(2, "Mango");

        // Iterating through entrySet()
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        // Get value
        System.out.println("Get key 3: " + map.get(3));

        // Check key existence
        System.out.println("Contains key 5? " + map.containsKey(5));

        // Remove a key
        map.remove(4);
        System.out.println("After removing key 4: " + map);
    }
}
