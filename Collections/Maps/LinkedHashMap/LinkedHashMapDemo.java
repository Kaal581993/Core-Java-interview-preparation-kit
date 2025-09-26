package Collections.Maps.LinkedHashMap;
import java.util.*;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();

        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4);

        System.out.println("LinkedHashMap (insertion order preserved):");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        //Uncommon method used in LinkedHashMap are as follows:
        System.out.println("Accessing key 'Two': " + map.get("Two")); // Accessing an element
        map.put("Two", 22); // Updating value for key 'Two'
        System.out.println("After updating, key 'Two': " + map.get("Two"));
        map.remove("Three"); // Removing an element
        System.out.println("After removing key 'Three': " + map);   
        System.out.println("Size of LinkedHashMap: " + map.size()); // Getting size
        System.out.println("Is LinkedHashMap empty? " + map.isEmpty()); // Check if empty       

        //map.clear(); // Clearing the map
        System.out.println("After clearing, is LinkedHashMap empty? " + map.isEmpty());

        // Other methods: putAll(), removeAll(), clear()
        // Let's see them one by one in detail

        // Creating a LinkedHashMap with initial capacity and load factor
        Map<String, Integer> map2 = new LinkedHashMap<>(16, 0.75f);
        map2.put("Five", 5);
        map2.put("Six", 6);
        //the putall method in LinkedHashMap is used to copy all of the mappings from the specified map to this map.
        map2.putAll(map); // Copying the content of one map to another map
        System.out.println("Second LinkedHashMap: " + map2);            


        // Converting a Map to a Set
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        System.out.println("Entry Set: " + entrySet);                       
    }
}
