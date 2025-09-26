import java.util.*;

public class TreeMapDemo {
    public static void main(String[] args) {
        SortedMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(3, "Orange");
        treeMap.put(1, "Apple");
        treeMap.put(2, "Banana");
        treeMap.put(4, "Grapes");

        System.out.println("TreeMap (keys sorted): " + treeMap);

        // Access methods of SortedMap
        System.out.println("First key: " + treeMap.firstKey());
        System.out.println("Last key: " + treeMap.lastKey());
        System.out.println("HeadMap (keys < 3): " + treeMap.headMap(3));
        System.out.println("TailMap (keys >= 2): " + treeMap.tailMap(2));
        System.out.println("SubMap (2 to 4): " + treeMap.subMap(2, 4));
    }
}
