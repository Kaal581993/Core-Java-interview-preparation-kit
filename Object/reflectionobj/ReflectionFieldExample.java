import java.lang.reflect.*;

class Car {
    private String model = "Tesla";
    public int year = 2025;
}

public class ReflectionFieldExample {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Car.class;
        Object car = clazz.getDeclaredConstructor().newInstance();

        // List fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            System.out.println("Field: " + f.getName() + " | Type: " + f.getType());
        }

        // Access private field
        Field modelField = clazz.getDeclaredField("model");
        modelField.setAccessible(true);
        System.out.println("Original Model: " + modelField.get(car));

        // Modify field value
        modelField.set(car, "BMW");
        System.out.println("Modified Model: " + modelField.get(car));
    }
}



// 🔎 Key Insight

// Serialization libraries (Jackson, Gson) use this to map JSON fields to Java fields without getters/setters.

// 📌 Interview Q: Difference between getFields() and getDeclaredFields()?
// 👉 getFields() → only public fields (including inherited).
// 👉 getDeclaredFields() → all fields declared in the class (even private).

