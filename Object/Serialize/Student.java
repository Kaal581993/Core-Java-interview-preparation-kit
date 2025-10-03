
import java.io.Serializable;

public class Student implements Serializable{
    private String name;
    private int age;
    private String id;
    private String address;

    public Student(String name, int age, String id, String address) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.address = "";

    }

    public Student() {
    }

    public String getName() {
        return name;
    }


    
    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void displayName() {
        System.out.println("Student Name: " + name);
    }

    public void displayAge() {
        System.out.println("Student Age: " + age);
    }

    public void displayInfo() {
        System.out.println("Student Information:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("ID: " + id);
        System.out.println("Address: " + address);
    }
}
