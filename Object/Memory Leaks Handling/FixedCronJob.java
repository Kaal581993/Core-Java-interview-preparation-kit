// FixedCronJob.java

//Let's understand this code step by step
import java.time.Duration;
// Duration class is used to represent a time-based amount of time, such as "34.5 seconds".
import java.util.UUID;
// UUID class is used to generate unique identifiers.
import java.util.concurrent.*;
// We're importing the necessary classes for concurrency and scheduling tasks

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
// Caffeine is a high performance caching library for Java
// We're using Caffeine to create a bounded cache with expiry
//This means that the cache will automatically remove entries that 
//have not been accessed for a certain period of time

public class FixedCronJob {
    // Let us look into the cronjob logic here
    // Bounded cache with expiry — avoids unbounded growth
    private static final Cache<String, byte[]> CACHE = Caffeine.newBuilder()
    // We are creating a cache with a maximum size of 10,000 entries and 
    //an expiry time of 10 minutes after the last access
            .maximumSize(10_000)
            // We're setting the expiry time to 10 minutes after the last access of the entry
            .expireAfterAccess(Duration.ofMinutes(10))
            // We're creating a new cache with the specified configuration
            .build();

    // Scheduled executor for the cron-like job
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2);

    // ThreadLocal for per-thread buffers — must be removed after use
    private static final ThreadLocal<StringBuilder> TL = ThreadLocal.withInitial(StringBuilder::new);

    public static void main(String[] args) {
        // Clean shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SCHEDULER.shutdown();
            try {
                if (!SCHEDULER.awaitTermination(10, TimeUnit.SECONDS)) {
                    SCHEDULER.shutdownNow();
                }
            } catch (InterruptedException ex) {
                SCHEDULER.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }));

        Runnable task = () -> {
            StringBuilder sb = null;
            try {
                sb = TL.get();
                for (int i = 0; i < 100; i++) {
                    byte[] data = generateData();
                    CACHE.put(UUID.randomUUID().toString(), data);
                }
            } catch (Throwable t) {
                // log and recover — avoid swallowing errors silently
                t.printStackTrace();
            } finally {
                // 2 important things:
                if (sb != null) {
                    sb.setLength(0); // reuse or clear buffer
                }
                TL.remove();     // remove ThreadLocal to avoid retention
            }
        };

        // scheduleWithFixedDelay is safer when a task may overrun
        SCHEDULER.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
    }

    private static byte[] generateData() {
        // reuse buffers in production — this example allocates small arrays
        return new byte[1024 * 10];
    }
}
