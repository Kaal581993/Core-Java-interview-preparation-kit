import java.util.LinkedHashSet;

public class LinkedHashSetDemo {
    public static void main(String[] args) {
        LinkedHashSet<String> linkedSet = new LinkedHashSet<>();

        linkedSet.add("Dog");
        linkedSet.add("Cat");
        linkedSet.add("Horse");
        linkedSet.add("Dog"); // Duplicate ignored

        // Iterates in the order elements were added
        for (String item : linkedSet) {
            System.out.println(item);
        }

        // Key Methods
        System.out.println("Contains Cat? " + linkedSet.contains("Cat")); // true
        linkedSet.remove("Cat");
        System.out.println("After removal: " + linkedSet);

        // Other methods: addAll(), retainAll(), removeAll(), iterator()    
        LinkedHashSet<String> anotherSet = new LinkedHashSet<>();
        anotherSet.add("Elephant");     
        anotherSet.add("Lion");   
        linkedSet.addAll(anotherSet);
        System.out.println("After adding another set: " + linkedSet);   
        // Copying the content of one set to another set
        linkedSet.retainAll(anotherSet);        
        System.out.println("After retainAll: " + linkedSet);                        
        linkedSet.removeAll(anotherSet);
        System.out.println("After removeAll: " + linkedSet);
        // Iterator
        
        
        linkedSet.clear(); // Remove all elements
        System.out.println("After clear, is empty? " + linkedSet.isEmpty());        
        // Note: LinkedHashSet is not synchronized. For multi-threaded scenarios, 
        // consider using Collections.synchronizedSet()
        // or use ConcurrentHashMap instead.
        // For synchronization, use ConcurrentHashMap or Collections.synchronizedMap()
        // or use java.util.concurrent.CopyOnWriteArrayList instead.
        // LinkedHashSet is not thread-safe, so if you need thread safety, consider using
        // ConcurrentHashMap.
        // For more information, refer to Java's official documentation on Collections and Concurrency.
        //let us see how to use synchronizedSet
        try{
        LinkedHashSet<String> syncSet = new LinkedHashSet<>();
        syncSet.add("Tiger");
        syncSet.add("Leopard");
        syncSet.add("Cheetah");         
        LinkedHashSet<String> synchronizedLinkedSet = (LinkedHashSet<String>) java.util.Collections.synchronizedSet(syncSet);
        synchronized(synchronizedLinkedSet) {
            for (String item : synchronizedLinkedSet) {     
                System.out.println(item);
        
            }
        }
         } catch (Exception e) {
            System.out.println(e.getMessage());    
    
      //  e.printStackTrace();    
        }
    }
}
