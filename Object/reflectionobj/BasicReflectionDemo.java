// BasicReflectionDemo.java


// We're simulating Object Reflection in Java
// Reflection allows us to inspect classes, interfaces, fields, and methods at runtime
// and manipulate their properties dynamically.
// This is useful for frameworks, libraries, and tools that need to work with unknown classes
// at compile time, such as serialization libraries, dependency injection frameworks, etc.
class Car {
    //Let's have iterative explanation of the code
    private String model;
    private int year;
// We defined some vairables here

    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }
    // We defined a constructor here

    public void drive() {
        System.out.println(model + " (" + year + ") is driving...");
    }
    //We defined a class method here
}

public class BasicReflectionDemo {
    public static void main(String[] args) throws Exception {
        // Step 1: Create a Car object normally
        Car car = new Car("Toyota", 2022);

        // Step 2: Get the Class object
        Class<?> carClass = car.getClass();
        //<?> here is a wild card type that 
        //  means "any type". 
        //.getClass() method is used to get the runtime class of an object.   

        // Print class name
        System.out.println("Class Name: " + carClass.getName());

        // Step 3: List all declared fields
        System.out.println("-- Fields --");
        for (var field : carClass.getDeclaredFields()) {
            System.out.println("  " + field.getName() + " : " + field.getType().getSimpleName());
        }

        // Step 4: Call the 'drive' method using reflection
        var driveMethod = carClass.getMethod("drive");
        driveMethod.invoke(car); // Executes car.drive() at runtime
    }
}


// 🔹 Step-by-step breakdown
// Car car = new Car("Toyota", 2022);
// 👉 Normal object creation. Nothing reflective yet.

// Class<?> carClass = car.getClass();
// 👉 Using getClass() to get the blueprint (Class object) of Car.

// Loop through carClass.getDeclaredFields()
// 👉 Gets all fields (model, year) and their types.

// Real-world parallel: like listing all properties of a form.

// Get method drive and invoke it
// 👉 Instead of calling car.drive() directly, we get the method object
// (getMethod("drive")) and call invoke(car).

// Real-world parallel: you don’t know the function name at compile-time, but you still can call it.
// This is powerful for dynamic behavior in frameworks. 



