import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {
        // Create ArrayList of Strings
        List<String> arrayList = new ArrayList<>();

        // Add elements
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("C++");  
        int index = 0;
            for (String value : arrayList) {
                    System.out.println("After adjustment Display List index " + index + " Array Index Value " + value);
                    index++;
            }
        System.out.println("\n");

        arrayList.add(1, "Go");  // Insert at index 1
        //Here List index will be auto adjusted

         index = 0;
            for (String value : arrayList) {
                    System.out.println("After adjustment Display List index " + index + " Array Index Value " + value);
                    index++;
            }

        System.out.println("\n");



        // Example using for-each loop to achieve the same output
        index = 0;
        for (String value : arrayList) {
            System.out.println("After adjustment Display List index " + index + " Array Index Value " + value);
            index++;
        }

        System.out.println("\n");
        // Access elements
        System.out.println("First element: " + arrayList.get(0));

        // Modify element
        arrayList.set(2, "Rust");

        // Iterate
        for(String lang : arrayList){
            System.out.println(lang);
        }

        // Remove
        arrayList.remove("C++");
                index = 0;
        for (String value : arrayList) {
            System.out.println("After removal Display List index " + index + " Array Index Value " + value);
            index++;
        }
        arrayList.remove(0);

                index = 0;
        for (String value : arrayList) {
            System.out.println("After removal at index 0 Display List index " + index + " Array Index Value " + value);
            index++;
        }

        System.out.println("Is the Array list Empty?:"+arrayList.isEmpty());

        System.out.println("Is the Array list have C++: "+arrayList.contains("C++"));
        System.out.println("Is the Array list have Rust: "+arrayList.contains("Rust"));
        System.out.println("Is the index of Go: "+arrayList.indexOf("Go"));
        System.out.println("Is the last index of Rust: "+arrayList.lastIndexOf("Rust"));

        arrayList.clear();
        
        System.out.println("Final ArrayList: " + arrayList);
    }
}
