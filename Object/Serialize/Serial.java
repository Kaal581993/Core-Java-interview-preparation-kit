import java.io.*;
import java.nio.file.FileSystemException;

public class Serial {
    
    public static void main(String[] args) throws IOException {
        try {
            Student student = new Student("John Doe", 20, "S12345", "123 Main St");
        student.setAddress("123 Main St, NY");
        student.displayInfo();
        student.displayName();
         student.displayAge();

        try (FileOutputStream fos = new FileOutputStream("ob.txt");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Serialization
            oos.writeObject(student);
            System.out.println("✅ Object Serialized and transferred to ob.txt");
        }
        } catch (FileSystemException e) {
            System.out.println("❌ Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
