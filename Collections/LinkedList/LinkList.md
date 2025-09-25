
# 📌 1. Introduction to `LinkedList` in Java

`LinkedList` is a **doubly-linked list implementation** of the `List` and `Deque` interfaces. Unlike `ArrayList` (which is backed by a dynamic array), `LinkedList` stores elements in **nodes** that are connected by references (pointers).

* Each node stores:

  * `data` (the element)
  * `next` (reference to the next node)
  * `prev` (reference to the previous node)

---

# 📌 2. Key Characteristics

* Allows **duplicate elements**.
* Maintains **insertion order**.
* Provides **constant-time insertions/deletions** (O(1)) if the reference is known.
* Random access (`get(index)`) is **O(n)**, unlike `ArrayList` which is O(1).

---

# 📌 3. Implementation Example (with Comments)

```java
import java.util.*;

public class LinkedListDemo {
    public static void main(String[] args) {
        
        // ✅ LinkedList creation
        LinkedList<String> list = new LinkedList<>();
        
        // ✅ Adding elements
        list.add("Apple");       // Adds to the end
        list.add("Banana");
        list.add("Cherry");
        list.addFirst("Mango");  // Adds at the beginning
        list.addLast("Orange");  // Adds at the end
        
        // ✅ Displaying LinkedList
        System.out.println("LinkedList: " + list);
        
        // ✅ Accessing elements
        System.out.println("First Element: " + list.getFirst());
        System.out.println("Last Element: " + list.getLast());
        System.out.println("Element at index 2: " + list.get(2));
        
        // ✅ Removing elements
        list.removeFirst();     // Removes first element
        list.removeLast();      // Removes last element
        list.remove("Banana");  // Removes by value
        System.out.println("After Removals: " + list);
        
        // ✅ Iterating
        System.out.print("Iteration using Iterator: ");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        
        System.out.println();
        
        // ✅ Using Deque methods
        list.push("Kiwi");   // Adds at the beginning
        list.pop();          // Removes first element
        System.out.println("After push/pop: " + list);
        
        // ✅ Queue methods
        list.offer("Pineapple");  // Adds to the end
        list.poll();              // Removes first element
        System.out.println("After offer/poll: " + list);
    }
}
```

---

# 📌 4. Important `LinkedList` Methods

| Method                              | Description                               |
| ----------------------------------- | ----------------------------------------- |
| `add(E e)`                          | Appends element to end                    |
| `add(int index, E element)`         | Inserts at given position                 |
| `addFirst(E e)`                     | Inserts at head                           |
| `addLast(E e)`                      | Inserts at tail                           |
| `remove()`                          | Removes first element                     |
| `remove(int index)`                 | Removes element at index                  |
| `remove(Object o)`                  | Removes first occurrence of object        |
| `removeFirst()` / `removeLast()`    | Removes head/tail                         |
| `get(int index)`                    | Returns element at index                  |
| `getFirst()` / `getLast()`          | Returns head/tail                         |
| `set(int index, E e)`               | Replaces element at index                 |
| `peek() / peekFirst() / peekLast()` | Retrieves head/tail without removal       |
| `poll() / pollFirst() / pollLast()` | Retrieves head/tail and removes           |
| `push(E e)`                         | Adds at head (stack behavior)             |
| `pop()`                             | Removes and returns head (stack behavior) |
| `iterator()`                        | Iterator for traversal                    |
| `descendingIterator()`              | Iterator in reverse order                 |

---

# 📌 5. Comparison with Other List Implementations

| Feature / Class         | **ArrayList**            | **LinkedList**             | **Vector**                                  | **Stack**              |
| ----------------------- | ------------------------ | -------------------------- | ------------------------------------------- | ---------------------- |
| **Underlying DS**       | Dynamic Array            | Doubly Linked List         | Dynamic Array (synchronized)                | Extends Vector (LIFO)  |
| **Order Maintained**    | Yes                      | Yes                        | Yes                                         | Yes                    |
| **Duplicates Allowed**  | Yes                      | Yes                        | Yes                                         | Yes                    |
| **Nulls Allowed**       | Yes                      | Yes                        | Yes                                         | Yes                    |
| **Access (get/set)**    | O(1)                     | O(n)                       | O(1)                                        | O(1)                   |
| **Insertion at End**    | O(1) amortized           | O(1)                       | O(1) amortized                              | O(1)                   |
| **Insertion in Middle** | O(n)                     | O(1) (if pointer known)    | O(n)                                        | Not applicable         |
| **Deletion**            | O(n)                     | O(1) (if pointer known)    | O(n)                                        | O(1)                   |
| **Thread-Safe**         | No                       | No                         | Yes                                         | Yes                    |
| **Implements**          | List, RandomAccess       | List, Deque, Queue         | List, RandomAccess                          | Extends Vector         |
| **Best Use Case**       | Frequent read operations | Frequent insert/delete ops | Legacy thread-safe alternative to ArrayList | Stack (LIFO) structure |

---

# 📌 6. Real-World Mapping

* **ArrayList** → Best for **lookup-heavy** apps (e.g., search engines storing cache results).
* **LinkedList** → Best for **frequent insert/delete** apps (e.g., playlist management, job scheduling).
* **Vector** → Legacy, used in **multi-threaded** contexts before `Collections.synchronizedList()`.
* **Stack** → Useful for **expression evaluation, undo/redo, backtracking** problems.

---

✅ With this, you have:

1. Full `LinkedList` code demo.
2. Explanation of methods.
3. Detailed comparison table with `ArrayList`, `LinkedList`, `Vector`, and `Stack`.
4. Real-world use case mapping.

---

