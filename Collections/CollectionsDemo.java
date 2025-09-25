import java.util.*;
import java.util.concurrent.*;

// ==============================================
// Project: Demonstrating Real-time use cases of
// ArrayList, LinkedList, Vector, and Stack
// ==============================================
public class CollectionsDemo {
    public static void main(String[] args) {
        
        // =======================================
        // 1. ArrayList → Cache for Search Results
        // =======================================
        System.out.println("=== ArrayList Demo (Cache System) ===");
        ArrayList<String> searchCache = new ArrayList<>();
        
        // Adding search results
        searchCache.add("Java LinkedList tutorial");
        searchCache.add("Difference between ArrayList and Vector");
        searchCache.add("Data Structures in Java");
        
        // Fast random access
        System.out.println("Cached result[1]: " + searchCache.get(1));
        System.out.println("All cached results: " + searchCache);
        
        // =======================================
        // 2. LinkedList → Playlist Management
        // =======================================
        System.out.println("\n=== LinkedList Demo (Playlist) ===");
        LinkedList<String> playlist = new LinkedList<>();
        
        // Adding songs dynamically
        playlist.add("Song A");
        playlist.add("Song B");
        playlist.add("Song C");
        playlist.addFirst("Intro Track"); // Add at beginning
        playlist.addLast("Closing Track"); // Add at end
        
        System.out.println("Current Playlist: " + playlist);
        
        // Removing currently playing song
        String nowPlaying = playlist.removeFirst();
        System.out.println("Now Playing: " + nowPlaying);
        System.out.println("Updated Playlist: " + playlist);
        
        // =======================================
        // 3. Vector → Multi-threaded Safe Access
        // =======================================
        System.out.println("\n=== Vector Demo (Thread-Safe Operations) ===");
        Vector<Integer> sharedVector = new Vector<>();
        
        // Simulate multiple threads writing to vector
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        Runnable writerTask = () -> {
            for (int i = 0; i < 3; i++) {
                sharedVector.add(i);
                System.out.println(Thread.currentThread().getName() + " added: " + i);
            }
        };
        
        executor.submit(writerTask);
        executor.submit(writerTask);
        executor.submit(writerTask);
        executor.shutdown();
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Final contents of Vector: " + sharedVector);
        
        // =======================================
        // 4. Stack → Undo/Redo Feature
        // =======================================
        System.out.println("\n=== Stack Demo (Undo/Redo) ===");
        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();
        
        // User actions
        undoStack.push("Type 'Hello'");
        undoStack.push("Type 'Hello World'");
        undoStack.push("Delete 'World'");
        
        System.out.println("Undo Stack: " + undoStack);
        
        // Perform undo
        String lastAction = undoStack.pop();
        redoStack.push(lastAction);
        System.out.println("Undo Action: " + lastAction);
        System.out.println("After Undo → Undo Stack: " + undoStack + ", Redo Stack: " + redoStack);
        
        // Perform redo
        String redoAction = redoStack.pop();
        undoStack.push(redoAction);
        System.out.println("Redo Action: " + redoAction);
        System.out.println("After Redo → Undo Stack: " + undoStack + ", Redo Stack: " + redoStack);
    }
}
