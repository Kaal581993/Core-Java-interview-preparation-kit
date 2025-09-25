// LeakyCronJob.java
import java.util.*;
import java.util.concurrent.*;

public class LeakyCronJob {

    private static final Map<String, byte[]> CACHE = new HashMap<>();
    //We're Declaring a HashMap to store the cache data

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(6);
    // We're creating a ScheduledExecutorService with 2 threads using Executors.newScheduledThreadPool()
    // This will allow our program to run multiple instances of the job concurrently
    // Decoding ExecutorService.newScheduledThreadPool() is used here to maintain a fixed number of threads,
    // in () I can set the number of threads to 2 or more, but this is not a recommended approach
    // allowing the program to run multiple instances of the job concurrently without the risk of starvation.
    // If I had to use the Static number of Threads, I would use Executors.newSingleThreadScheduledExecutor() instead
    // If I had to used the Dynamic number of Threads, I would use Executors.newFixedThreadPool() instead
    // This will prevent the cache from growing indefinitely if the program runs for a long time
    // The second argument is the initial delay, the third argument is the delay between each execution,
    // and the fourth argument is the time unit
    private static final ThreadLocal<StringBuilder> TL = ThreadLocal.withInitial(StringBuilder::new);
    // We're creating a ThreadLocal to ensure that the StringBuilder is not shared among multiple threads
    // This way, each thread will have its own instance of StringBuilder
    // This will prevent the cache from growing indefinitely if the program runs for a long time
    // The second argument is the initial delay, the third argument is the delay between each execution,
    // and the fourth argument is the time unit

    public static void main(String[] args) {
// This is a simple example of a LeakyCronJob
        // In a real-world scenario, this would be a more complex job,
        // that would interact with external systems, databases, or other resources
        // and would need to handle errors, exceptions, and resource cleanup

        // Schedule the task to run every minute
        // We're using scheduleAtFixedRate() here to maintain a fixed number of threads

        Runnable task = () -> {
            //We're declaring the task to run every minute using Lambda expressions
            // We're using a StringBuilder to store and print the data to the console
            // This will prevent the cache from growing indefinitely if the program runs for a long time
            // The StringBuilder is thread-safe and can be used by multiple threads concurrently
            StringBuilder sb = TL.get();
            for (int i = 0; i < 10000; i++) {
                // Let's have the iterative  explanation to this and see how this do not clears the cache and 
                // have the memory Leaky
                

                byte[] data = new byte[1024 * 50]; // 489KB
                // Simulating a resource-intensive task (like reading data from a database)
                // We're using a UUID to generate a unique key for each cache entry
                // We're also simulating the reading data from a database by creating a byte array of 489KB
                // This is just a placeholder and the actual implementation would be more complex
                // This will prevent the cache from growing indefinitely if the program runs for a long time
                // The UUID is used as a key to store the data in the cache
                // The data is also appended to the StringBuilder to print it to the console
                CACHE.put(UUID.randomUUID().toString(), data);
                // CACHE.put() is not a thread-safe operation, so it's necessary to synchronize it
                // synchronized (CACHE) {
                //     sb.append("Cache size: ").append(CACHE.size()).append("\n");
                // }
                //If we're syncronized, it would prevent the cache from growing indefinitely if the program runs 
                //for a long time
                // let's have 2 time iteration to see how this affects the cache and memory Leaks
                // We're using a StringBuilder to store and print the data to the console
                // This will prevent the cache from growing indefinitely if the program runs for a long time
                // The StringBuilder is thread-safe and can be used by multiple threads concurrently
                for(int j=0;j<2;j++) {
                     sb.append("Cache size: ").append(CACHE.size()).append("\n");
                     sb.append("Data /n"+data);
                     // We're appending the data to the StringBuilder to print it to the console
                     // This will prevent the cache from growing indefinitely if the program runs for a long time
                     // The StringBuilder is thread-safe and can be used by multiple threads concurrently
                     sb.append("x /n");
                     //sb.append will add the x in the string.
                 }

                System.out.println("String Builder /n"+sb.toString()+"/n Data /n"+data);
                     sb.append("x/n");


                 }

            
        };

        System.out.println("task:"+task.toString() + " scheduled");

        SCHEDULER.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }
}
