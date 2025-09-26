import java.util.*;

public class MapCoreMethods {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();

        // put() – insert or update
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Cherry");
        System.out.println("After put: " + map);

        // get() – retrieve value
        System.out.println("Get key 2: " + map.get(2));

        // containsKey() – check if key exists
        System.out.println("Contains key 3? " + map.containsKey(3));

        // containsValue() – check if value exists
        System.out.println("Contains value 'Banana'? " + map.containsValue("Banana"));

        // remove(Object key) – remove entry
        map.remove(2);
        System.out.println("After removing key 2: " + map);

        // size() – number of entries
        System.out.println("Size: " + map.size());

        // isEmpty() – check if empty
        System.out.println("Is empty? " + map.isEmpty());

        // putAll() – copy from another map
        Map<Integer, String> anotherMap = new HashMap<>();
        anotherMap.put(4, "Date");
        anotherMap.put(5, "Elderberry");
        map.putAll(anotherMap);
        System.out.println("After putAll: " + map);

        // keySet() – all keys
        System.out.println("Keys: " + map.keySet());

        // values() – all values
        System.out.println("Values: " + map.values());

        // entrySet() – all entries
        System.out.println("Entries: " + map.entrySet());

        // clear() – remove everything
        map.clear();
        System.out.println("After clear: " + map);
    }
}
