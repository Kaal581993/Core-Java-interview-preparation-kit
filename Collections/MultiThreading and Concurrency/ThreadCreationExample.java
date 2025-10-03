/**
 * ============================================================
 * ThreadCreationExample.java
 * Purpose: Show 3 ways to create and start threads in Java.
 * ============================================================
 */
public class ThreadCreationExample {

    // Main method to demonstrate thread creation   
    public static void main(String[] args) {
        // 1. Extending Thread class
        Thread t1 = new Thread() {
            //Thread class is found in package from java.lang package
            @Override
            public synchronized void run() {
                System.out.println("Thread via extending Thread class");
            }
            //Drawbacks of using Threads class are as follows:
            // 1. Java does not support multiple inheritance, so if a class extends Thread, it cannot extend any other class.
            // 2. It is less flexible compared to implementing Runnable interface, as it ties the thread behavior directly to the class itself.

        };

        // 2. Using Runnable
        Runnable task = () -> System.out.println("Thread via Runnable");
        // Synchronize the Runnable task using a synchronized block
        Runnable syncTask = () -> {
            synchronized (ThreadCreationExample.class) {
                
            System.out.println("Thread via synchronized Runnable");
            }
        };
        Thread tSync = new Thread(syncTask);
        tSync.start();        
        // Here we are using lambda expression to implement the run method of Runnable interface
        //Rules for using lambda expression are as follows:
        // 1. A lambda expression can be used only to implement a functional interface, which is an interface with a single abstract method.
        // 2. The syntax of a lambda expression is (parameters) -> expression or (parameters) -> { statements; }.
        // 3. If the body of the lambda expression contains a single statement, the curly braces and return keyword can be omitted.
        // 4. Lambda expressions can capture variables from the enclosing scope, but they must be effectively final (not modified after being assigned).
        // 5. Lambda expressions can be assigned to variables, passed as arguments, or returned from methods.
        // Can we use Lambda expression just like arrow funcctions in React/ Typescript?
        // No, Java's lambda expressions are not exactly the same as arrow functions in JavaScript/TypeScript, but they serve a similar
        // purpose in providing a concise way to represent functional interfaces.
        // Both allow for more compact code when working with functions or methods that can be treated as first-class citizens.
        Thread t2 = new Thread(task);
  Thread t3 = new Thread(() -> System.out.println("Thread via inline Runnable"));
        // Runnable is a functional interface found in java.lang package
        // Key points about Runnable:
        // 1. Runnable is a functional interface with a single abstract method run().
        // 2. It is used to define a task that can be executed by a thread.
        // 3. Runnable can be implemented using a lambda expression or an anonymous class.
        // 4. Runnable does not return a result or throw checked exceptions.
        // 5. Runnable is often used with Thread class to create and start threads.     

        // 3. Using Callable with ExecutorService
        java.util.concurrent.Callable<String> callTask = () -> "Thread via Callable";
        // Callable is a functional interface that can return a result and throw checked exceptions.
        // It is part of the java.util.concurrent package and is used with ExecutorService to manage
        // threads and tasks.
        // Key points about Callable:
        // 1. Callable's call() method can return a value, unlike Runnable's run() method which returns void.
        // 2. Callable can throw checked exceptions, allowing for better error handling in concurrent tasks.
        // 3. Callable is typically used with ExecutorService to submit tasks and retrieve results via Future objects.
        // 4. Callable is a generic interface, allowing you to specify the return type of the call() method.
        // 5. Callable is often used in scenarios where you need to perform a task that produces a result or requires exception handling.
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
        // ExecutorService is part of the java.util.concurrent package and provides a higher-level replacement for managing threads.
        // Key points about ExecutorService:
        // 1. ExecutorService manages a pool of threads, allowing for efficient reuse of threads.
        // 2. It provides methods to submit tasks (Runnable or Callable) for execution.
        // 3. ExecutorService can be configured with different types of thread pools (fixed,cached, single-threaded, etc.).
        // 4. It provides methods for graceful shutdown of the thread pool.     
        // 5. ExecutorService helps in managing concurrency and simplifies thread management in Java applications.


        try {
            System.out.println(executor.submit(callTask).get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        executor.shutdown();

        // Here the threads are not synchronized, so the output order may vary.
        // In order to synchronize threads, we can use synchronized blocks or methods,
        // Following changes are to be made in above example to synchronize the threads:
        // public synchronized void run() { ... } in Thread class
        //  (someObject) { synchronized... } in Runnable and Callable implementations
        // Note: Synchronization is important when multiple threads access shared resources to prevent data inconsistency.
        // However, overuse of synchronization can lead to performance issues and deadlocks.
        // Always ensure to use synchronization judiciously and only when necessary.

    }
}
