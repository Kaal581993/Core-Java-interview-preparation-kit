import java.lang.reflect.*;

class Student {
    private String name;
    public Student() {}
    public Student(String name) { this.name = name; }
}

public class ReflectionConstructorExample {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Student");

        // List all constructors
        Constructor<?>[] cons = clazz.getDeclaredConstructors();
        for (Constructor<?> c : cons) {
            System.out.println("Constructor: " + c);
        }

        // Create object dynamically using constructor
        Constructor<?> paramCons = clazz.getConstructor(String.class);
        Object studentObj = paramCons.newInstance("Alice");
        System.out.println("Created object: " + studentObj);
    }
}
