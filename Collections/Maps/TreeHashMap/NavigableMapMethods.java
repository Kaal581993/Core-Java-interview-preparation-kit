import java.util.*;

public class NavigableMapMethods {
    public static void main(String[] args) {
        NavigableMap<Integer, String> map = new TreeMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");
        map.put(40, "Forty");

        System.out.println("NavigableMap: " + map);

        // lowerEntry() and lowerKey()
        System.out.println("LowerEntry(25): " + map.lowerEntry(25));
        System.out.println("LowerKey(25): " + map.lowerKey(25));

        // floorEntry() and floorKey()
        System.out.println("FloorEntry(30): " + map.floorEntry(30));
        System.out.println("FloorKey(30): " + map.floorKey(30));

        // ceilingEntry() and ceilingKey()
        System.out.println("CeilingEntry(25): " + map.ceilingEntry(25));
        System.out.println("CeilingKey(25): " + map.ceilingKey(25));

        // higherEntry() and higherKey()
        System.out.println("HigherEntry(30): " + map.higherEntry(30));
        System.out.println("HigherKey(30): " + map.higherKey(30));

        // firstEntry() and lastEntry()
        System.out.println("FirstEntry: " + map.firstEntry());
        System.out.println("LastEntry: " + map.lastEntry());

        // pollFirstEntry(), pollLastEntry()
        System.out.println("PollFirstEntry: " + map.pollFirstEntry());
        System.out.println("PollLastEntry: " + map.pollLastEntry());
        System.out.println("After polling: " + map);

        // descendingMap() and descendingKeySet()
        System.out.println("DescendingMap: " + map.descendingMap());
        System.out.println("DescendingKeySet: " + map.descendingKeySet());

        // navigableKeySet()
        System.out.println("NavigableKeySet: " + map.navigableKeySet());
    }
}
