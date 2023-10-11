import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

/**
 * HashTable is a class that implements a hash table with separate chaining.
 * The hash table uses a linked list to store the elements.
 * The load factor will never be greater than 0.75.
 * 
 */

public class Hashtable {
    LinkedList<String>[] table;
    int tableSize;
    int collisionCounter = 0;
    private final double LOAD_FACTOR = 0.5;
    private static final int INITIAL_TABLE_SIZE = 512;

    public Hashtable(int tableSize) {
        this.tableSize = tableSize;
        table = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = new LinkedList();
        }
    }

    /**
     * Inserts a key into the hash table.
     * Refactor the table if the load factor is greater than 0.75.
     * 
     * @param key the key to insert
     */
    public void insert(String key) {
        int hash = hash(key);
        if (!table[hash].isEmpty()) {
            for (String element : table[hash]) {
                System.out.println("Kollisjon: " + element + " og " + key);
                collisionCounter++;
                }
            }
        

        table[hash].add(key);
        double loadFactor = calculateLoadFactor();
        if (loadFactor > LOAD_FACTOR) {
            resize();
        }

    }

    /**
     * Resizes the hash table to the next prime number.
     * Rehashes all the elements in the table.
     */
    private void resize() {
        LinkedList<String>[] oldTable = table;
        tableSize = findNextPrime(tableSize * 2);

        table = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = new LinkedList();
        }

        for (LinkedList<String> list : oldTable) {
            for (String key : list) {
                int hash = hash(key);
                table[hash].add(key);
            }
        }
    }

    /**
     * Finds the next prime number after the given number.
     * 
     * @param number the number to find the next prime number after
     * @return the next prime number
     */

    private int findNextPrime(int number) {
        while (!isPrime(number)) {
            number++;
        }
        return number;
    }

    /**
     * Checks if a number is a prime number.
     * 
     * @param num the number to check
     * @return true if the number is a prime number, false otherwise
     */
    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
    

    /**
     * Checks if the hash table contains the given key.
     * 
     * @param key the key to check
     * @return true if the key is in the hash table, false otherwise
     */

    public boolean contains(String key) {
        int hash = hash(key);
        return table[hash].contains(key);
    }

    /**
     * Hashes the given key.
     * 
     * @param key the key to hash
     * @return the hash value of the key
     */

    public int hash(String key) {
        int hash = 0;
        int prime = 31;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * prime + key.charAt(i);
        }
        if (hash < 0) {
            hash = (hash == Integer.MIN_VALUE) ? Integer.MAX_VALUE : -hash;
        }
    
        return hash % tableSize;
    }

    /**
     * Calculates the load factor of the hash table.
     * 
     * @return the load factor of the hash table
     */

    private double calculateLoadFactor() {
        int totalElements = 0;

        for (LinkedList<String> list : table) {
            if (!list.isEmpty()) {
                totalElements += list.size();
            }
        }
        return (double) totalElements / tableSize;
    }

    /**
     * Prints the statistics of the hash table.
     */

    public void printStatistics() {
        int totalElements = 0;

        for (LinkedList<String> list : table) {
            if (!list.isEmpty()) {
                totalElements += list.size();
            }
        }
        double loadFactor = (double) totalElements / tableSize;
        System.out.println("Lastfaktor: " + loadFactor);
        System.out.println("Antall kollisjoner: " + collisionCounter);
        System.out.println("Gjennomsnitlig antall kollisjoner per person: " + (double) collisionCounter / totalElements);
    }

    public static void main(String[] args) throws Exception {

        try {
            Hashtable table = new Hashtable(INITIAL_TABLE_SIZE);
            var path = "https://www.idi.ntnu.no/emner/idatt2101/hash/navn.txt";
            var url = new URI(path).toURL();
            URLConnection connection = url.openConnection();

            InputStreamReader input = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(input);

            String line;

            while ((line = buffer.readLine()) != null) {
                table.insert(line);
            }
            buffer.close();

            System.out.println("Vegard Johnsen finnes i tabellen:" + table.contains("Vegard Johnsen"));
            System.out.println("Elias Trana finnes i tabellen: " + table.contains("Elias Tvenning Trana"));


            table.printStatistics();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}