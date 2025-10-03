/**
 * ============================================================
 * ThreadCreationExample.java - Enhanced Version
 * Purpose: Demonstrate improved thread creation patterns with proper synchronization,
 *          wait/notify mechanisms, error handling, and best practices.
 * ============================================================
 */
public class ThreadCreationExample2 {

    // We are going to demonstrate the Interproccess Ccommunication with threads in this example.

    // Shared monitor object for synchronization - better practice than using class literal
    private static final Object sharedMonitor = new Object();
    // sharedMonitor is an Object created for Java Object class
    // This will help us to synchronize the threads by storing the value of 
    // the object in the heap memory and sharing it between multiple threads.
    



    // Volatile flag to indicate when work is done
    private static volatile boolean isReady = false;

    /**
     * Main method demonstrating improved thread creation patterns
     */
    public static void main(String[] args) {
        demonstrateThreadCreation();
        demonstrateWaitNotifyPattern();
        demonstrateExecutorService();
    }

    /**
     * Demonstrates three ways to create threads with improved patterns
     */
    private static void demonstrateThreadCreation() {
        System.out.println("=== Thread Creation Examples ===");

        // 1. Extending Thread class with proper error handling
        Thread t1 = new DemoThread("Thread-1");
        t1.setDaemon(false); // Ensure main thread waits for completion
        // setDaemon is the function declared in Thread class which is used to set the thread as daemon or user thread.
        // A daemon thread is a low-priority thread that runs in the background to perform tasks
        // such as garbage collection. The main difference between a daemon thread and a user thread
        // is that the JVM will exit when all user threads have finished, even if daemon threads
        // are still running. In contrast, the JVM will not exit if there are any user threads still running.
        // By default, threads are user threads unless explicitly set as daemon using set
        //Daemon(true) method.
        // is the main thread an example of user thread or daemon thread?
        // The main thread in a Java application is an example of a user thread.
        // It is the primary thread that starts when the Java application begins execution.
        // When the main method completes, the main thread terminates.
        // Does the main thread wait for deamon threads to complete before exiting?
        // No, the main thread does not wait for daemon threads to complete before exiting.
        // When the main thread finishes its execution, the JVM will exit regardless of whether
        // there are any daemon threads still running. Daemon threads are designed to run in the
        // background and do not prevent the JVM from shutting down.
        // If you want the main thread to wait for a daemon thread to complete, you would
        // need to convert the daemon thread to a user thread by calling setDaemon(false)
        // before starting it. However, this is not a common practice, as daemon threads are
        // typically used for background tasks that do not require guaranteed completion.

        // Key points about daemon threads:
        // 1. Daemon threads are low-priority threads that run in the background.
        // 2. The JVM exits when all user threads finish, regardless of daemon threads.     
        // 3. Daemon threads are typically used for background tasks like garbage collection.
        // 4. Daemon threads should not be used for tasks that require guaranteed completion,
        // as they may be terminated abruptly when the JVM exits.
        // 5. Daemon threads can be created by calling setDaemon(true) before starting the thread.

        // 2. Using Runnable with improved structure
        Runnable simpleTask = new SimpleRunnableTask("Runnable-Task");
        // here taskName: is the parameter passed to the constructor of SimpleRunnableTask class
        // How SimpleRunnableTask class differes from Runnable interface? is it implementing the Runnable interface?
        // Yes, SimpleRunnableTask is a class that implements the Runnable interface.
        // The Runnable interface defines a single method, run(), which contains the code that
        // will be executed when the thread is started.
        // The SimpleRunnableTask class provides a concrete implementation of the run() method,

        Thread t2 = new Thread(simpleTask, "Thread-2");

        // 3. Using Runnable with synchronization and wait/notify
        Runnable syncTask = new SynchronizedRunnableTask("Sync-Task");
        //SynchronizedRunnableTask is a class that implements the Runnable interface
        // Key points of SynchronizedRunnableTask is as follows:
        // 1. It implements the Runnable interface, allowing it to be executed by a thread
        // 2. It uses a synchronized block to acquire a lock on a shared monitor object,
        // ensuring that only one thread can execute the synchronized code at a time.
        // 3. It demonstrates the wait/notify pattern by calling wait() on the shared
        // monitor object, releasing the lock and allowing other threads to proceed.
        // 4. It includes error handling for InterruptedException to properly manage
        // interruptions during the wait state.
        // 5. It simulates synchronized work by waiting for a specified timeout period.
        // 6. It provides a clear structure for synchronized tasks that require coordination
        // between multiple threads.


        Thread t3 = new Thread(syncTask, "Thread-3");

        // Start all threads
        startThreadsSafely(t1, t2, t3);
        //startThreadsSafely is a method used to start multiple threads safely with error handling
        

        // Wait for threads to complete with timeout
        waitForThreadsCompletion(t1, t2, t3);
    }

    /**
     * Demonstrates proper wait/notify pattern with timeout and error handling
     */
    private static void demonstrateWaitNotifyPattern() {
        System.out.println("\n=== Wait/Notify Pattern Example ===");

        // Producer thread that will notify after work is done
        Thread producer = new Thread(() -> {
            synchronized (sharedMonitor) {
                try {
                    System.out.println("Producer: Starting work...");
                    Thread.sleep(2000); // Simulate work
                    isReady = true;
                    System.out.println("Producer: Work completed, notifying consumers...");
                    sharedMonitor.notifyAll(); // Notify all waiting threads
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Producer: Interrupted during work");
                }
            }
        }, "Producer-Thread");

        // Consumer thread that waits with timeout
        Thread consumer = new Thread(() -> {
            synchronized (sharedMonitor) {
                try {
                    System.out.println("Consumer: Waiting for work completion...");
                    long remainingTimeout = 5000; // 5 seconds timeout
                    long startTime = System.currentTimeMillis();

                    while (!isReady && remainingTimeout > 0) {
                        sharedMonitor.wait(remainingTimeout);

                        // Calculate remaining time after wait
                        long elapsed = System.currentTimeMillis() - startTime;
                        remainingTimeout = 5000 - elapsed;
                    }

                    if (isReady) {
                        System.out.println("Consumer: Work completed successfully!");
                    } else {
                        System.out.println("Consumer: Timeout waiting for work completion");
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Consumer: Interrupted while waiting");
                }
            }
        }, "Consumer-Thread");

        // Start threads
        startThreadsSafely(producer, consumer);
        waitForThreadsCompletion(producer, consumer);
    }

    /**
     * Demonstrates ExecutorService with proper resource management
     */
    private static void demonstrateExecutorService() {
        System.out.println("\n=== ExecutorService Example ===");

        java.util.concurrent.ExecutorService executor = null;

        try {
            executor = java.util.concurrent.Executors.newFixedThreadPool(3);

            // Submit multiple tasks
            java.util.concurrent.Future<String> future1 = executor.submit(new CallableTask("Task-1"));
            java.util.concurrent.Future<String> future2 = executor.submit(new CallableTask("Task-2"));
            java.util.concurrent.Future<String> future3 = executor.submit(new CallableTask("Task-3"));

            // Wait for results with timeout
            String result1 = future1.get(3, java.util.concurrent.TimeUnit.SECONDS);
            String result2 = future2.get(3, java.util.concurrent.TimeUnit.SECONDS);
            String result3 = future3.get(3, java.util.concurrent.TimeUnit.SECONDS);

            System.out.println("Results: " + result1 + ", " + result2 + ", " + result3);

        } catch (java.util.concurrent.TimeoutException e) {
            System.err.println("Task execution timed out");
        } catch (java.util.concurrent.ExecutionException e) {
            System.err.println("Task execution failed: " + e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread was interrupted");
        } finally {
            // Proper resource cleanup
            if (executor != null) {
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.out.println("Forcing executor shutdown...");
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    executor.shutdownNow();
                }
            }
        }
    }

    /**
     * Safely starts multiple threads with error handling
     */
    private static void startThreadsSafely(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.start();
            } catch (IllegalThreadStateException e) {
                System.err.println("Failed to start thread " + thread.getName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Waits for thread completion with timeout and proper error handling
     */
    private static void waitForThreadsCompletion(Thread... threads) {
        for (Thread thread : threads) {
            try {
                if (thread.isAlive()) {
                    thread.join(3000); // 3 second timeout per thread
                    if (thread.isAlive()) {
                        System.out.println("Thread " + thread.getName() + " did not complete within timeout");
                        thread.interrupt(); // Send interrupt signal
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Main thread interrupted while waiting for " + thread.getName());
            }
        }
    }

    // Inner classes for better organization

    /**
     * Improved Thread extension with proper error handling
     */
    private static class DemoThread extends Thread {
        public DemoThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                System.out.println(getName() + ": Starting execution");
                // Simulate some work
                Thread.sleep(1000);
                System.out.println(getName() + ": Completed successfully");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(getName() + ": Was interrupted");
            } catch (Exception e) {
                System.err.println(getName() + ": Unexpected error: " + e.getMessage());
            }
        }
    }

    /**
     * Improved Runnable implementation
     */
    private static class SimpleRunnableTask implements Runnable {
        private final String taskName;

        public SimpleRunnableTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println(taskName + ": Executing runnable task");
        }
    }

    /**
     * Runnable with proper synchronization and wait/notify
     */
    private static class SynchronizedRunnableTask implements Runnable {
        private final String taskName;

        public SynchronizedRunnableTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            synchronized (sharedMonitor) {
                try {
                    System.out.println(taskName + ": Acquired lock, starting synchronized work");
                    // Simulate synchronized work with timeout
                    sharedMonitor.wait(4000); // Wait up to 4 seconds for notification
                    System.out.println(taskName + ": Completed synchronized work");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println(taskName + ": Interrupted during wait");
                }
            }
        }
    }

    /**
     * Callable task with proper error handling
     */
    private static class CallableTask implements java.util.concurrent.Callable<String> {
        private final String taskName;

        public CallableTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(taskName + ": Executing callable task");
            // Simulate work that might throw exceptions
            if ("Task-2".equals(taskName)) {
                throw new RuntimeException("Simulated task failure");
            }
            return taskName + " completed";
        }
    }
}
