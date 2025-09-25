import java.lang.reflect.*;

// Step 1: Define a sample class
class Employee {
    private String name;
    private int age;

    // Constructor
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Public method
    public void displayInfo() {
        System.out.println("Employee: " + name + ", Age: " + age);
    }

    // Private method
    private void secretMethod() {
        System.out.println("This is a secret method!");
    }
}

// Step 2: Reflection demo
public class ReflectionDemo {
    public static void main(String[] args) throws Exception {

        // 1️⃣ Get Class object at runtime
        Class<?> empClass = Class.forName("Employee");

        // 2️⃣ Get all constructors
        Constructor<?>[] constructors = empClass.getConstructors();
        for (Constructor<?> c : constructors) {
            System.out.println("Constructor: " + c);
        }

        // 3️⃣ Create an object dynamically
        Constructor<?> constructor = empClass.getConstructor(String.class, int.class);
        Object empObj = constructor.newInstance("Alice", 30);

        // 4️⃣ Get all methods
        Method[] methods = empClass.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println("Method: " + m.getName());
        }

        // 5️⃣ Invoke a public method
        Method displayMethod = empClass.getMethod("displayInfo");
        displayMethod.invoke(empObj);

        // 6️⃣ Access a private method
        Method secret = empClass.getDeclaredMethod("secretMethod");
        secret.setAccessible(true); // bypass private access
        secret.invoke(empObj);

        // 7️⃣ Access private field
        Field nameField = empClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(empObj, "Bob"); // modify private field

        // Re-run method to see updated field
        displayMethod.invoke(empObj);
    }
}
