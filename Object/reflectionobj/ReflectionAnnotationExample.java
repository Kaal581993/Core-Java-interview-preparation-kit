import java.lang.annotation.*;
import java.lang.reflect.*;

// Define custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestCase {
    String value();
}

// Sample class
class TestSuite {
    @TestCase("Check addition")
    public void testAdd() { System.out.println("Addition test executed"); }

    @TestCase("Check subtraction")
    public void testSubtract() { System.out.println("Subtraction test executed"); }
}

// Reflection to read annotations
public class ReflectionAnnotationExample {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = TestSuite.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(TestCase.class)) {
                TestCase t = m.getAnnotation(TestCase.class);
                System.out.println("Running: " + t.value());
                m.invoke(obj); // execute test method dynamically
            }
        }
    }
}
