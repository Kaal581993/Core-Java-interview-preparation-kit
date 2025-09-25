public class ReflectionClassExample {
    public static void main(String[] args) throws Exception {
        // Load class dynamically (string name instead of compile-time reference)
        Class<?> clazz = Class.forName("java.util.ArrayList");

        // Print class name and modifiers
        System.out.println("Class: " + clazz.getName());
        System.out.println("Is Interface? " + clazz.isInterface());
        System.out.println("Superclass: " + clazz.getSuperclass().getName());

        // Print all implemented interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> i : interfaces) {
            System.out.println("Implements: " + i.getName());
        }
    }
}
