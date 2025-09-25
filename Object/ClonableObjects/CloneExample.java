class Person implements Cloneable {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Step 2: Override clone() method
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


public class CloneExample {
    public static void main(String[] args) {
        try {
            Person p1 = new Person("Alice", 25);

            // Step 2: Clone the object
            Person p2 = (Person) p1.clone();

            // Step 3: Show that it's a new object with the same data
            System.out.println("Original: " + p1.name + " " + p1.age);
            System.out.println("Clone: " + p2.name + " " + p2.age);

            // Step 4: Prove they are different objects
            System.out.println("Are they same object? " + (p1 == p2));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}


// | Feature                   | Original Object                                            | Cloned Object                                              |
// | ------------------------- | ---------------------------------------------------------- | ---------------------------------------------------------- |
// | **Memory Address**        | Stored at its own memory location.                         | Stored at a new memory location.                           |
// | **Data**                  | Holds original field values.                               | Holds a copy of the original field values.                 |
// | **Equality Check** (`==`) | `obj == obj` → true                                        | `obj == clone` → false (different objects).                |
// | **Class Type**            | Same class.                                                | Same class (after casting).                                |
// | **Changes**               | Modifying original does not affect clone (for primitives). | Modifying clone does not affect original (for primitives). |
