import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserial {
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        try {
            
            FileInputStream fis = new FileInputStream("ob.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Student student = (Student) ois.readObject();
            System.out.println("✅ Object Deserialized from ob.txt");
            student.displayInfo();
            student.displayName();
            student.displayAge();
            ois.close();
            fis.close();
            
        } catch (IOException ioex){
            ioex.printStackTrace();
        }
    }
}
