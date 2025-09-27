// ReflectionDemo.java
// Author: ChatGPT example
// Date: 2025-09-17
// ChangeLog:
//   1.0 - initial demo: inspect fields/methods/constructors, access private members, 
//dynamic proxy, inspector.

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class ReflectionDemo2 {

    // --- Simple runtime annotation (kept at RUNTIME so reflection can read it) ---
    @Retention(RetentionPolicy.RUNTIME)
    //@Retention(RetentionPolicy.RUNTIME) is used to specify that 
    //the annotation should be retained at runtime, allowing it to be 
    //accessed via reflection during the execution of the program.
    //Using this annotation, we can add metadata to our classes, methods, or fields 
    //that can be inspected and utilized at runtime.
    // This is useful for frameworks and libraries that rely on annotations for configuration,
    // such as dependency injection frameworks, serialization libraries, etc.
    

    //@Target(ElementType.TYPE)
    // @Target(ElementType.TYPE) indicates that this annotation can only be applied to
    // types (classes, interfaces, enums).
    // This helps enforce correct usage of the annotation and prevents it from being
    // mistakenly applied to methods, fields, or other program elements.        
    // By restricting the target to types, we ensure that the annotation is used
    // in a meaningful context where it can provide relevant metadata about the class or interface.
    // This is particularly useful for defining entity classes, configuration classes,      
    @Target(ElementType.TYPE)

    public @interface Entity {
// In Java, the keyword @interface is used to define an annotation, not a regular interface.
// An annotation is metadata you can attach to classes, methods, fields, parameters, etc.
// you are not defining a normal interface — you are defining a custom annotation called Entity
        String value();
    }

    // --- A demo class annotated and with private fields/methods ---
    @Entity("person")
    // Applying the @Entity annotation to the Person class
    // This marks the class as an entity with the value "person"
    // which can be used for metadata purposes, such as ORM mapping.
    // The annotation can be accessed at runtime via reflection.
    public static class Person {
        private String name;
        private int age;
        private String secret = "ssn-1234";

        public Person() { this("Unknown", 0); }
        public Person(String name, int age) { this.name = name; this.age = age; }

        public String greet(String who) { return "Hello " + who + ", I'm " + name; }

        private void celebrateBirthday() {
            age++;
            System.out.println("Happy " + age + "th birthday!");
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }

    // --- Interface for dynamic proxy demo ---
    public interface Service {
        void perform(String task);
    }

    public static class ServiceImpl implements Service {
        public void perform(String task) {
            System.out.println("Performing: " + task);
        }
    }

    // --- Entry point: demonstrates reflection operations iteratively ---
    public static void main(String[] args) throws Exception {
        System.out.println("== Reflection demo started ==");
        Class<?> personClass = Person.class; // 1) obtain Class

        System.out.println("Class name: " + personClass.getName());

        // 2) Fields: iterate declared fields (private included)
        System.out.println("-- Fields --");
        Field[] fields = personClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true); // bypass private (use with caution in prod)
            System.out.println("  " + f.getName() + " : " + f.getType().getSimpleName());
        }

        // 3) Constructors: pick constructor and instantiate at runtime
        Constructor<?> ctor = personClass.getConstructor(String.class, int.class);
        Object person = ctor.newInstance("Alice", 30);
        System.out.println("Created instance: " + person);

        // 4) Access private field
        Field secretField = personClass.getDeclaredField("secret");
        secretField.setAccessible(true);
        System.out.println("secret before = " + secretField.get(person));
        secretField.set(person, "ssn-9999");
        System.out.println("secret after  = " + secretField.get(person));

        // 5) Invoke public method
        Method greet = personClass.getMethod("greet", String.class);
        Object greeting = greet.invoke(person, "Bob");
        System.out.println("greet result = " + greeting);

        // 6) Invoke private method
        Method birthday = personClass.getDeclaredMethod("celebrateBirthday");
        birthday.setAccessible(true);
        birthday.invoke(person); // increments age and prints

        // 7) Annotations
        if (personClass.isAnnotationPresent(Entity.class)) {
            Entity ent = personClass.getAnnotation(Entity.class);
            System.out.println("Entity annotation value: " + ent.value());
        }

        // 8) Recursive inspector (demonstrates iteratively walking object graph)
        System.out.println("-- Inspector output --");
        inspectObject(person, Collections.newSetFromMap(new IdentityHashMap<>()), 0);

        // 9) Dynamic Proxy: create runtime proxy that logs before/after each call
        System.out.println("-- Dynamic Proxy demo --");
        Service real = new ServiceImpl();
        Service proxy = (Service) Proxy.newProxyInstance(
            Service.class.getClassLoader(),
            new Class[]{Service.class},
            new InvocationHandler() {
                public Object invoke(Object proxyObj, Method method, Object[] methodArgs) throws Throwable {
                    System.out.println("[Proxy] Before " + method.getName());
                    Object result = method.invoke(real, methodArgs); // forward to real impl
                    System.out.println("[Proxy] After " + method.getName());
                    return result;
                }
            }
        );
        proxy.perform("daily-backup");

        System.out.println("== Reflection demo finished ==");
    }

    // --- Helper inspector: recursively prints fields (avoids cycles using identity set) ---
    private static void inspectObject(Object obj, Set<Object> visited, int depth) throws IllegalAccessException {
        String indent = "  ".repeat(depth);
        if (obj == null) {
            System.out.println(indent + "null");
            return;
        }
        if (visited.contains(obj)) {
            System.out.println(indent + "[circular] " + obj.getClass().getSimpleName());
            return;
        }
        visited.add(obj);
        Class<?> cls = obj.getClass();
        System.out.println(indent + cls.getSimpleName() + " {");
        for (Field f : cls.getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(obj);
            if (isPrimitiveOrWrapperOrString(f.getType())) {
                System.out.println(indent + "  " + f.getName() + " = " + val);
            } else {
                System.out.println(indent + "  " + f.getName() + " ->");
                inspectObject(val, visited, depth + 2);
            }
        }
        System.out.println(indent + "}");
    }

    private static boolean isPrimitiveOrWrapperOrString(Class<?> type) {
        return type.isPrimitive()
            || type == String.class
            || type == Integer.class
            || type == Long.class
            || type == Short.class
            || type == Byte.class
            || type == Boolean.class
            || type == Float.class
            || type == Double.class
            || type == Character.class;
    }
}
