
---

# 📘 **List Interface in Java**

* The **`List`** interface is part of the **`java.util`** package.
* It represents an **ordered collection (sequence)** of elements that:

  1. **Allows duplicates** (unlike `Set`).
  2. Preserves the **insertion order**.
  3. Provides **indexed access** (like arrays, but dynamic).

```java
// Declaration
List<Type> list = new ArrayList<>();
```

---

# 🔑 **Common Methods of List Interface**

(All list implementations share these)

| Method                      | Description                           |
| --------------------------- | ------------------------------------- |
| `add(E e)`                  | Appends element to the end            |
| `add(int index, E e)`       | Inserts element at given index        |
| `get(int index)`            | Returns element at index              |
| `set(int index, E e)`       | Replaces element at index             |
| `remove(int index)`         | Removes element at index              |
| `remove(Object o)`          | Removes first occurrence of object    |
| `size()`                    | Returns number of elements            |
| `isEmpty()`                 | Checks if list is empty               |
| `contains(Object o)`        | Checks if list contains object        |
| `indexOf(Object o)`         | Returns first index of object         |
| `lastIndexOf(Object o)`     | Returns last index of object          |
| `clear()`                   | Removes all elements                  |
| `iterator()`                | Returns iterator for traversal        |
| `listIterator()`            | Returns list iterator (bidirectional) |
| `subList(int from, int to)` | Returns a view (portion) of the list  |

---

# ⚙️ **Implementations of List**

Java provides four main **concrete classes**:

1. **ArrayList**
2. **LinkedList**
3. **Vector**
4. **Stack** (subclass of Vector)

---

## 1️⃣ **ArrayList**

* Backed by a **dynamic array**.
* Fast **random access** (`O(1)` for `get`).
* Slower **insertions/deletions** in middle (`O(n)`).

```java
import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {
        // Create ArrayList of Strings
        List<String> arrayList = new ArrayList<>();

        // Add elements
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("C++");
        arrayList.add(1, "Go");  // Insert at index 1

        // Access elements
        System.out.println("First element: " + arrayList.get(0));

        // Modify element
        arrayList.set(2, "Rust");

        // Iterate
        for(String lang : arrayList){
            System.out.println(lang);
        }

        // Remove
        arrayList.remove("C++");
        arrayList.remove(0);

        System.out.println("Final ArrayList: " + arrayList);
    }
}
```

---

## 2️⃣ **LinkedList**

* Backed by a **doubly linked list**.
* Fast **insertions/deletions** (`O(1)` if reference known).
* Slower **random access** (`O(n)` for `get`).

```java
import java.util.*;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        // Add elements
        linkedList.add(10);
        linkedList.add(20);
        linkedList.addFirst(5);  // LinkedList specific
        linkedList.addLast(30);

        // Access
        System.out.println("First: " + linkedList.getFirst());
        System.out.println("Last: " + linkedList.getLast());

        // Remove
        linkedList.removeFirst();
        linkedList.removeLast();

        // Iterate
        for(int val : linkedList){
            System.out.println(val);
        }
    }
}
```

---

## 3️⃣ **Vector**

* Legacy class (from JDK 1.0).
* Similar to `ArrayList` but **synchronized** (thread-safe).
* Slower compared to `ArrayList` in single-threaded use.

```java
import java.util.*;

public class VectorExample {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();

        // Add
        vector.add("Red");
        vector.add("Green");
        vector.add("Blue");

        // Access
        System.out.println("Element at index 1: " + vector.get(1));

        // Remove
        vector.remove("Red");

        // Iterate
        for(String color : vector){
            System.out.println(color);
        }
    }
}
```

---

## 4️⃣ **Stack**

* Subclass of Vector.
* Implements **LIFO (Last In, First Out)**.
* Useful for recursion, expression evaluation, backtracking.

```java
import java.util.*;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        // Push elements
        stack.push(10);
        stack.push(20);
        stack.push(30);

        // Peek (top element)
        System.out.println("Top: " + stack.peek());

        // Pop (remove top)
        System.out.println("Popped: " + stack.pop());

        // Iterate
        for(int val : stack){
            System.out.println(val);
        }
    }
}
```

---

# 📊 **Comparison Table of List Implementations**

| Feature                     | **ArrayList**                      | **LinkedList**                                             | **Vector**                              | **Stack**                      |
| --------------------------- | ---------------------------------- | ---------------------------------------------------------- | --------------------------------------- | ------------------------------ |
| **Underlying DS**           | Dynamic Array                      | Doubly Linked List                                         | Dynamic Array                           | Dynamic Array (extends Vector) |
| **Access (get/set)**        | O(1)                               | O(n)                                                       | O(1)                                    | O(1)                           |
| **Insert/Delete at End**    | Amortized O(1)                     | O(1)                                                       | Amortized O(1)                          | O(1)                           |
| **Insert/Delete at Middle** | O(n)                               | O(n) traversal + O(1) removal                              | O(n)                                    | O(n)                           |
| **Thread Safety**           | ❌ Not synchronized                 | ❌ Not synchronized                                         | ✅ Synchronized                          | ✅ Synchronized                 |
| **Null Values**             | ✅ Allowed                          | ✅ Allowed                                                  | ✅ Allowed                               | ✅ Allowed                      |
| **Best Use Case**           | Fast random access, frequent reads | Frequent insertions/deletions                              | Legacy code requiring sync              | LIFO operations                |
| **Extra Methods**           | –                                  | `addFirst()`, `addLast()`, `removeFirst()`, `removeLast()` | Legacy `capacity()`, `ensureCapacity()` | `push()`, `pop()`, `peek()`    |

---

# 🔑 **Key Differences**

1. **Performance**:

   * `ArrayList` best for **search/read**.
   * `LinkedList` best for **insert/delete**.
   * `Vector` & `Stack` rarely used now, mostly replaced by modern concurrency utilities.

2. **Thread Safety**:

   * Only `Vector` and `Stack` are synchronized.
   * For thread-safe `ArrayList`, use `Collections.synchronizedList()` or `CopyOnWriteArrayList`.

3. **Legacy vs Modern**:

   * `ArrayList` and `LinkedList` are **modern and preferred**.
   * `Vector` and `Stack` are **legacy**, included for backward compatibility.

---


