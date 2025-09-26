import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        // Create a HashSet of Strings
        HashSet<String> hashSet = new HashSet<>();
        // HashSet automatically removes duplicate elements, and null values are not allowed

        // Adding elements
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // Duplicate, will NOT be added
        hashSet.add(null);    // Allows ONE null value

        // Iterating through HashSet (no guaranteed order)
        for (String item : hashSet) {
            System.out.println(item);
        }

        // Key Methods
        System.out.println("Contains Banana? " + hashSet.contains("Banana")); // true
        hashSet.remove("Banana");
        System.out.println("After removal: " + hashSet);

        //We will go through all the methods of HashSet here for demonstration purposes,
        // but in real-world usage, you would typically use only a subset of these methods.
        System.out.println("Size of HashSet: " + hashSet.size()); // Size of the set
        System.out.println("Is HashSet empty? " + hashSet.isEmpty());
        hashSet.clear(); // Remove all elements
        System.out.println("After clear, is empty? " + hashSet.isEmpty());          
        // Other methods: addAll(), retainAll(), removeAll()
        // Let's go with addAll() for demonstration purposes
        HashSet<String> anotherSet = new HashSet<>();
        anotherSet.add("Date");     
        anotherSet.add("Orange");   
        hashSet.addAll(anotherSet);
        //Copying the content of one set to another set

        System.out.println("After adding another set: " + hashSet); 

        // Note: HashSet is not synchronized. For multi-threaded scenarios, 
        //consider using Collections.synchronizedSet()
        // or use ConcurrentHashMap instead.
        // For synchronization, use ConcurrentHashMap or Collections.synchronizedMap()
        // or use java.util.concurrent.CopyOnWriteArrayList instead.
        // HashSet is not thread-safe, so if you need thread safety, consider using ConcurrentHashMap.
        // For more information, refer to Java's official documentation on Collections and Concurrency.
        


    }
}
