**deep dive into Maps in Java**, including all implementations like `HashMap`, `SortedMap`, `TreeMap`, and `LinkedHashMap`, we’ll go iteratively:

---

# **1. Introduction to Map in Java**

* A `Map` in Java is a **collection of key-value pairs**.
* Keys are **unique** (no duplicates allowed).
* Values may be **duplicated**.
* Not part of `Collection` interface but belongs to `java.util`.

### **Key Characteristics:**

* `Map<K,V>` is a generic interface.
* Each `Map` implementation (`HashMap`, `LinkedHashMap`, `TreeMap`) has **different internal storage + ordering rules**.

---

# **2. Map Interface – Important Methods**

| **Method**                                 | **Description**                              |
| ------------------------------------------ | -------------------------------------------- |
| `put(K key, V value)`                      | Inserts or replaces a key-value mapping.     |
| `putIfAbsent(K key, V value)`              | Inserts only if key is absent.               |
| `get(Object key)`                          | Returns the value for a key.                 |
| `getOrDefault(Object key, V defaultValue)` | Returns default value if key not found.      |
| `containsKey(Object key)`                  | Checks if a key exists.                      |
| `containsValue(Object value)`              | Checks if a value exists.                    |
| `remove(Object key)`                       | Removes entry for given key.                 |
| `replace(K key, V value)`                  | Replaces value if key exists.                |
| `keySet()`                                 | Returns `Set` of all keys.                   |
| `values()`                                 | Returns `Collection` of all values.          |
| `entrySet()`                               | Returns `Set<Map.Entry<K,V>>` for iteration. |
| `size()`                                   | Returns number of mappings.                  |
| `clear()`                                  | Removes all mappings.                        |
| `isEmpty()`                                | Checks if map is empty.                      |

---

# **3. Implementations**

---

## **3.1 HashMap**

* **Unordered** collection of key-value pairs.
* Uses **hash table** internally.
* Allows **one null key** and multiple null values.
* Average time complexity: **O(1)** for `get/put`.

### Example:

```java
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
    }
}
```

---

## **3.2 LinkedHashMap**

* Maintains **insertion order** of keys.
* Uses **doubly-linked list** + hashing.
* Slightly slower than `HashMap` due to ordering overhead.

### Example:

```java
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
    }
}
```

---

## **3.3 SortedMap (Interface) & TreeMap**

* `SortedMap` is an interface that guarantees keys in **sorted order**.
* `TreeMap` implements `SortedMap`.
* Uses **Red-Black Tree** internally.
* **Does not allow null key**, but allows multiple null values.

### Example:

```java
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
```

---

# **4. Iterative Understanding Approach**

When we use:

* `HashMap` → Best for **fast lookups**, order doesn’t matter.
* `LinkedHashMap` → When **order of insertion** is needed.
* `TreeMap` → When **sorted keys** are required.

---

# **5. Comparison Table**

| Feature / Class           | **HashMap**                    | **LinkedHashMap**                | **TreeMap**                       |
| ------------------------- | ------------------------------ | -------------------------------- | --------------------------------- |
| **Ordering**              | No order                       | Maintains insertion order        | Natural ordering (sorted by keys) |
| **Null Keys**             | One allowed                    | One allowed                      | Not allowed                       |
| **Null Values**           | Multiple allowed               | Multiple allowed                 | Multiple allowed                  |
| **Performance (get/put)** | O(1) average                   | O(1) average (slightly slower)   | O(log n)                          |
| **Underlying DS**         | Hash table                     | Hash table + Doubly Linked List  | Red-Black Tree                    |
| **When to Use**           | Fast lookups                   | Need predictable iteration order | Need sorted keys                  |
| **Iteration Complexity**  | O(n)                           | O(n)                             | O(n log n)                        |
| **Duplicates**            | Keys unique, values may repeat | Keys unique, values may repeat   | Keys unique, values may repeat    |

---

# **6. Key Differences Summary**

1. **HashMap** – Fastest, no order, allows null key.
2. **LinkedHashMap** – Slightly slower, maintains order, allows null key.
3. **TreeMap** – Sorted order, no null key, slower due to tree balancing.
4. **SortedMap** – Just an interface, usually backed by `TreeMap`.

---

✅ This covers:

* Methods of `Map`
* Implementation classes with code
* Iterative explanations
* Full comparison table

---

let’s go **deep into all distinct methods of Maps in Java**.
 **all methods**, we’ll split them into:

1. **Core Map Interface (`java.util.Map`)**
2. **Default methods added in Java 8+ (functional style, streams)**
3. **SortedMap (extra methods)**
4. **NavigableMap (extra methods)**
5. **Specific implementation behaviors (HashMap, LinkedHashMap, TreeMap)**

---

# **1. Map Interface – Core Methods**

```java
// Basic CRUD operations
V put(K key, V value)                   // Insert/replace a key-value mapping
V get(Object key)                       // Retrieve value for a key
V remove(Object key)                    // Remove mapping by key
boolean containsKey(Object key)         // Check if key exists
boolean containsValue(Object value)     // Check if value exists
int size()                              // Number of key-value pairs
boolean isEmpty()                       // Check if map is empty
void clear()                            // Remove all mappings

// Bulk operations
void putAll(Map<? extends K, ? extends V> m)  // Copy all entries from another map

// Collection views
Set<K> keySet()                         // All keys as a Set
Collection<V> values()                  // All values as a Collection
Set<Map.Entry<K,V>> entrySet()          // All entries as a Set
```

---

# **2. Default Methods (Java 8+)**

These came to make `Map` more **functional programming friendly**:

```java
V getOrDefault(Object key, V defaultValue)   // Return value or fallback

V putIfAbsent(K key, V value)                // Insert only if key is absent
boolean remove(Object key, Object value)     // Remove only if key maps to value
boolean replace(K key, V oldValue, V newVal) // Replace only if key maps to oldVal
V replace(K key, V value)                    // Replace value if key exists

void forEach(BiConsumer<? super K,? super V> action)   // Apply action to each entry
void replaceAll(BiFunction<? super K,? super V,? extends V> function) // Replace values

V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)
V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)

V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction)
```

---

# **3. SortedMap (Interface extending Map)**

Specialized for **sorted key order**:

```java
Comparator<? super K> comparator()       // Comparator used (null if natural ordering)
K firstKey()                             // Lowest key
K lastKey()                              // Highest key
SortedMap<K,V> headMap(K toKey)          // Keys strictly less than toKey
SortedMap<K,V> tailMap(K fromKey)        // Keys greater/equal to fromKey
SortedMap<K,V> subMap(K fromKey, K toKey)// Range of keys [fromKey, toKey)
```

---

# **4. NavigableMap (extends SortedMap)**

Enhances `SortedMap` with **navigation methods** (used by TreeMap):

```java
Map.Entry<K,V> lowerEntry(K key)         // Entry < key
K lowerKey(K key)                        // Key < key
Map.Entry<K,V> floorEntry(K key)         // Entry <= key
K floorKey(K key)                        // Key <= key
Map.Entry<K,V> ceilingEntry(K key)       // Entry >= key
K ceilingKey(K key)                      // Key >= key
Map.Entry<K,V> higherEntry(K key)        // Entry > key
K higherKey(K key)                       // Key > key

Map.Entry<K,V> firstEntry()              // Smallest entry
Map.Entry<K,V> lastEntry()               // Largest entry
Map.Entry<K,V> pollFirstEntry()          // Remove & return first
Map.Entry<K,V> pollLastEntry()           // Remove & return last

NavigableSet<K> navigableKeySet()        // Keys as NavigableSet
NavigableMap<K,V> descendingMap()        // Reverse order view
NavigableSet<K> descendingKeySet()       // Keys in reverse order
```

---

# **5. Specific Implementations**

### **HashMap**

* Inherits all methods from `Map`.
* Special behavior: **one null key allowed**, multiple null values allowed.

### **LinkedHashMap**

* Same as HashMap, but maintains **insertion order** or **access order** (if `accessOrder = true` in constructor).
* Adds no new methods.

### **TreeMap**

* Implements **NavigableMap**.
* Adds **all navigation + sorted methods**.
* Does **not allow null keys**.

---

# ✅ **Summary Table of Methods**

| **Category**          | **Methods**                                                                                                                                                                                                                        |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Core CRUD**         | `put`, `get`, `remove`, `size`, `isEmpty`, `clear`, `putAll`                                                                                                                                                                       |
| **Checks**            | `containsKey`, `containsValue`                                                                                                                                                                                                     |
| **Views**             | `keySet`, `values`, `entrySet`                                                                                                                                                                                                     |
| **Java 8+ Additions** | `getOrDefault`, `putIfAbsent`, `remove(key, value)`, `replace`, `replaceAll`, `forEach`, `compute`, `computeIfAbsent`, `computeIfPresent`, `merge`                                                                                 |
| **SortedMap**         | `comparator`, `firstKey`, `lastKey`, `headMap`, `tailMap`, `subMap`                                                                                                                                                                |
| **NavigableMap**      | `lowerEntry`, `lowerKey`, `floorEntry`, `floorKey`, `ceilingEntry`, `ceilingKey`, `higherEntry`, `higherKey`, `firstEntry`, `lastEntry`, `pollFirstEntry`, `pollLastEntry`, `navigableKeySet`, `descendingMap`, `descendingKeySet` |

---

👉 Would you like me to now **write a big code example** that demonstrates **all these methods one by one** with commented outputs — so you get a practical reference for interviews and projects?
