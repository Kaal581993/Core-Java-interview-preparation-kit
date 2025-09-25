class Address implements Cloneable {
    String city;
    Address(String city) { this.city = city; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // shallow copy of Address
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // shallow clone
    }
}

public class ShallowExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address addr = new Address("New York");
        Person p1 = new Person("Alice", addr);
        Person p2 = (Person) p1.clone();

        // Change address of original
        p1.address.city = "London";

        System.out.println("Original: " + p1.address.city); // London
        System.out.println("Clone: " + p2.address.city);    // London (same reference)
    }
}
