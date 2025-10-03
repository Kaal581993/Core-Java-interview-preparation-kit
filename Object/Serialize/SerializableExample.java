import java.io.*;

class SerializableStudent implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    int age;
    transient String password; // not serialized

    public SerializableStudent(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }
}

public class SerializableExample {
    public static void main(String[] args) {
        SerializableStudent s1 = new SerializableStudent("Alice", 22, "mypassword");

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            out.writeObject(s1);
            System.out.println("✅ Serialized SerializableStudent object");
        } catch (IOException e) { e.printStackTrace(); }

        // Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.ser"))) {
            SerializableStudent s2 = (SerializableStudent) in.readObject();
            System.out.println("✅ Deserialized SerializableStudent object");
            System.out.println("Name: " + s2.name + ", Age: " + s2.age + ", Password: " + s2.password);
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
    }
}
