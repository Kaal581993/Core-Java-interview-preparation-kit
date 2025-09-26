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

        hashSet.clear(); // Remove all elements
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Date");     
        hashSet.add("Orange");  
        //Let's see the HashSet with retaining all elements from another set using retainAll()
        System.out.println("Current Hash Set: " + hashSet);

        hashSet.retainAll(anotherSet);
        System.out.println("After retaining elements from another set: " + hashSet);
        // Note: retainAll() modifies the original set, 
        // it doesn't return a new set with the retained elements.
        // Where does the new elements go? They are still in the original set.
        System.out.println("Original set after retainAll: " + hashSet);
        System.out.println("Anotherset after retainAll: " + anotherSet);
        //So Retain All works like this:
        // It retains only the elements that are present in both sets.
        // It modifies the original set to keep only those elements which are common between both sets.
        // An intersection of two sets.

        //Now let's see how to use removeAll() method
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Date");     
        hashSet.add("Orange");  

        System.out.println("Current Hash Set: " + hashSet);

        hashSet.removeAll(anotherSet);
        System.out.println("After removing all elements from another set: " + hashSet);
        // Note: removeAll() modifies the original set,
    }
}
