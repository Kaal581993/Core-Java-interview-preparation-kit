// Let us understand this iterative solution of deep cloning in Java
// Deep cloning means creating a new object that is a copy of an existing object,
// including all objects referenced by the original object, recursively.
// This ensures that changes to the cloned object do not affect the original object and vice versa.
// This is particularly important when the object contains references to mutable objects.   

class Address implements Cloneable {
    // Step 1: Create a class that extends to Clonable interface

    String city;
    Address(String city) { this.city = city; }

    // Step 2: Override the clone() method to make it public
    // the Object class's clone() method is protected
    // so we need to override it and make it public
    // to allow cloning from outside the class
    // Also, we call super.clone() to create a shallow copy of Address   
    //But the cloning done here is still shallow  even if it's recursive nature       
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // clone Address separately
        //the super keyword here reserves the functionality of the parent class's clone() method
    }
    // The Object class is the root class of all classes in Java
    // and it provides the clone() method to create a copy of an object.
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
        Person cloned = (Person) super.clone();
        //Here we are doing a shallow clone of Person
        //So the address reference is copied as is
        //Both original and cloned Person objects will refer to the same Address object
        //To make it a deep clone we need to clone the Address object as well
        //So we call address.clone() to clone the Address object            
        cloned.address = (Address) address.clone(); // deep clone reference too
        return cloned;
    }
}

public class DeepExample {

    public static void main(String[] args) throws CloneNotSupportedException {
        Address addr = new Address("New York");
        Person p1 = new Person("Alice", addr);
        Person p2 = (Person) p1.clone();

        // Change address of original
        p1.address.city = "London";

        System.out.println("Original: " + p1.address.city); // London
        System.out.println("Clone: " + p2.address.city);    // New York (independent)
    }
}
