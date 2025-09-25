

import java.lang.reflect.*;

class Calculator {
    public int add(int a, int b) { return a + b; }
    private int secretMultiply(int a, int b) { return a * b; }
}

public class ReflectionMethodExample {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Calculator.class;
        Object calc = clazz.getDeclaredConstructor().newInstance();

        // List all methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println("Method: " + m.getName() + " | Return: " + m.getReturnType());
        }

        // Invoke public method dynamically
        Method addMethod = clazz.getMethod("add", int.class, int.class);
        System.out.println("Add Result: " + addMethod.invoke(calc, 5, 10));

        // Invoke private method
        Method secret = clazz.getDeclaredMethod("secretMultiply", int.class, int.class);
        secret.setAccessible(true);
        System.out.println("Secret Multiply Result: " + secret.invoke(calc, 3, 4));
    }
}
