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
        System.out.println("Map size: " + map.size());
        // Check if the map is empty

        

        // Clear the map
        map.clear();
        System.out.println("After clearing: " + map.isEmpty());


        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Orange");
        map.put(4, "Grapes");

        // Replace value for existing key
        map.put(2, "Mango");
        map.put(5, "Banana"); // Duplicate value, different key
        System.out.println("After clearing: " + map);
        // Note: HashMap does not maintain the insertion order.
        // If you need to maintain insertion order, consider using LinkedHashMap or SortedMap.
        // For more information, refer to Java's official documentation on Collections and Concurrency.
        // Let us see the other HashMap methods
        // Creating a HashMap with initial capacity and load factor
        Map<Integer, String> map2 = new HashMap<>(16, 0.75f);

        // Converting a Map to a Set
        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        System.out.println("Entry Set: " + entrySet);
        // Converting a Set to a List

        List<Map.Entry<Integer, String>> list = new ArrayList<>(entrySet);
        // Note: Converting a Set to a List may result in duplicate entries if the 
        //Set contains duplicate entries.
        System.out.println("List from entrySet: " + list);
        
        // Converting a Map to a List of values         
        List<String> values = new ArrayList<>(map.values());
        System.out.println("List of values: " + values);

        // Converting a Map to a List of keys   
        List<Integer> keys = new ArrayList<>(map.keySet());
        System.out.println("List of keys: " + keys);


        //Rules for ccoversion of Hashmap to a set
        //  HashMap to Set:
        // 1. The Set will contain Map.Entry objects representing key-value pairs.  
        // 2. The Set will not maintain the insertion order of the key-value pairs.
        // 3. The Set will not allow duplicate keys.
        // 4. The Set will not allow null keys or values.
        // 5. The Set will not allow null key-value pairs.
        // 6. The Set will not allow modification of the key-value pairs.

        // Rules for conversion of Hashmap to a List
        //  HashMap to List:
        // 1. The List will contain Map.Entry objects representing key-value pairs.
        // 2. The List will maintain the insertion order of the key-value pairs.
        // 3. The List will not allow duplicate keys.
        // 4. The List will not allow null keys or values.
        // 5. The List will not allow null key-value pairs.
        // 6. The List will allow modification of the key-value pairs.

        // Rules for conversion of Hashmap to a List of values
        //  HashMap to List of values:
        // 1. The List will contain values from the HashMap.
        // 2. The List will maintain the insertion order of the values.
        // 3. The List will allow duplicate values.
        // 4. The List will allow null values.
        // 5. The List will allow modification of the values.
        // 6. The List will not allow null values.
        // Rules for conversion of Hashmap to a List of keys

        //  HashMap to List of keys:
        // 1. The List will contain keys from the HashMap.
        // 2. The List will maintain the insertion order of the keys.
        // 3. The List will not allow duplicate keys.       
        // 4. The List will not allow null keys.
        // 5. The List will allow modification of the keys.
        // 6. The List will not allow null keys.    


    }
}
