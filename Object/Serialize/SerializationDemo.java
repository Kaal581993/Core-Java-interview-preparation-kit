
import java.io.*;

// Step 1: Create a class that implements Serializable
class Student implements Serializable {
    private static final long serialVersionUID = 1L; // version control
    
    String name;
    int age;
    transient String password; // won't be serialized
    
    public Student(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        Student s1 = new Student("Alice", 21, "secret123");

        // Step 2: Serialize the object
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            oos.writeObject(s1);
            System.out.println("✅ Student object serialized!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 3: Deserialize the object
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.ser"))) {
            Student s2 = (Student) ois.readObject();
            System.out.println("✅ Student object deserialized!");
            System.out.println("Name: " + s2.name);
            System.out.println("Age: " + s2.age);
            System.out.println("Password: " + s2.password); // will be null due to transient
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
