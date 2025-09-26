import java.util.*;

public class MapJava8Methods {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Apple");
        map.put(2, "Banana");

        // getOrDefault()
        System.out.println("GetOrDefault key 3: " + map.getOrDefault(3, "Not Found"));

        // putIfAbsent()
        map.putIfAbsent(2, "Mango"); // Won’t replace since 2 already exists
        map.putIfAbsent(3, "Cherry"); // Inserts since 3 not present
        System.out.println("After putIfAbsent: " + map);

        // remove(key, value)
        map.remove(2, "Banana"); // Removes since matches both key and value
        System.out.println("After remove(key, value): " + map);

        // replace(key, value)
        map.replace(1, "Green Apple"); // Replaces value at key 1
        System.out.println("After replace(key, value): " + map);

        // replace(key, oldValue, newValue)
        map.replace(3, "Cherry", "Dark Cherry"); // Only if matches old value
        System.out.println("After replace(key, oldValue, newValue): " + map);

        // forEach()
        System.out.print("forEach output: ");
        map.forEach((k, v) -> System.out.print(k + "=" + v + " "));
        System.out.println();

        // replaceAll()
        map.replaceAll((k, v) -> v.toUpperCase());
        System.out.println("After replaceAll: " + map);

        // compute()
        map.compute(1, (k, v) -> v + " (Fresh)");
        System.out.println("After compute: " + map);

        // computeIfAbsent()
        map.computeIfAbsent(4, k -> "Date");
        System.out.println("After computeIfAbsent: " + map);

        // computeIfPresent()
        map.computeIfPresent(3, (k, v) -> v + " Tree");
        System.out.println("After computeIfPresent: " + map);

        // merge()
        map.merge(3, " Fruit", (oldVal, newVal) -> oldVal + newVal);
        System.out.println("After merge: " + map);
    }
}
