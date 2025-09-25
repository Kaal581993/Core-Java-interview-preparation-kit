// FixedCronJob.java
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class FixedCronJob2 {
    // Bounded cache to avoid unbounded growth, using a synchronized LinkedHashMap as an LRU cache.
    private static final int MAX_CACHE_SIZE = 10_000;
    private static final Map<String, byte[]> CACHE = Collections.synchronizedMap(new LinkedHashMap<String, byte[]>(MAX_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    });

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
            StringBuilder sb = TL.get();
            try {
                for (int i = 0; i < 100; i++) {
                    byte[] data = generateData();
                    CACHE.put(UUID.randomUUID().toString(), data);
                }
            } catch (Throwable t) {
                // log and recover — avoid swallowing errors silently
                t.printStackTrace();
            } finally {
                // 2 important things:
                sb.setLength(0); // reuse or clear buffer
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