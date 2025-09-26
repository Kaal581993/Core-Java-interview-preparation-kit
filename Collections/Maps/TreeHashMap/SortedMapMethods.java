import java.util.*;

public class SortedMapMethods {
    public static void main(String[] args) {
        SortedMap<Integer, String> map = new TreeMap<>();
        map.put(3, "C");
        map.put(1, "A");
        map.put(2, "B");
        map.put(5, "E");
        map.put(4, "D");

        System.out.println("SortedMap: " + map);

        // comparator()
        System.out.println("Comparator: " + map.comparator()); // null = natural ordering

        // firstKey(), lastKey()
        System.out.println("First key: " + map.firstKey());
        System.out.println("Last key: " + map.lastKey());

        // headMap()
        System.out.println("HeadMap(<3): " + map.headMap(3));

        // tailMap()
        System.out.println("TailMap(>=3): " + map.tailMap(3));

        // subMap()
        System.out.println("SubMap(2 to 5): " + map.subMap(2, 5));
    }
}
