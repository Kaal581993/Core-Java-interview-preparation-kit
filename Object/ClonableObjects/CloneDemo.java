class Address implements Cloneable {
    String city;
    Address(String city) { this.city = city; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Person implements Cloneable {
    String name;
    int age;
    Address address;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Shallow clone (default)
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Deep clone (manual)
    protected Object deepClone() throws CloneNotSupportedException {
        Person cloned = (Person) super.clone();
        cloned.address = (Address) address.clone(); // independent reference
        return cloned;
    }
}

public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        // 1. Original Object
        Person p1 = new Person("Alice", 25);
        System.out.println("Original: " + p1.name); // Alice

        // 2. Basic Clone
        Person p2 = (Person) p1.clone();
        System.out.println("p1 == p2 ? " + (p1 == p2)); // false

        // 3. Shallow Clone Example
        Address addr = new Address("New York");
        Person p3 = new Person("Bob", addr);
        Person p4 = (Person) p3.clone();
        p3.address.city = "London"; // modify original
        System.out.println("Shallow Clone - p4 address: " + p4.address.city); // London (shared)

        // 4. Deep Clone Example
        Person p5 = new Person("Charlie", new Address("Paris"));
        Person p6 = (Person) p5.deepClone();
        p5.address.city = "Berlin"; // modify original
        System.out.println("Deep Clone - p6 address: " + p6.address.city); // Paris (independent)
    }
    
}
