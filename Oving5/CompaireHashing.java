import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/**
 * @Authors: Elias Trana and Vegard Johnsen
 */

/**
 * Hashing is an abstract class that implements a hash table.
 * 
 */
abstract class Hashing {
    protected int[] table;
    protected int m;
    protected int collisions;

    /**
     * Constructs a hash table with the specified size.
     * 
     * @param size the size of the hash table
     */
    public Hashing(int size) {
        this.m = size;
        this.table = new int[size];
        for (int i = 0; i < size; i++) {
            table[i] = -1;
        }
        this.collisions = 0;
    }

    /**
     * Inserts the given key into the hash table.
     * 
     * @param key the key to insert
     */

    public abstract void insert(int key);

    /**
     * Returns the number of collisions that have occurred during insertion.
     * 
     * @return the number of collisions
     */
    public int getCollisions() {
        return collisions;
    }
}

/**
 * LinearProbingHashTable implements a hash table using linear probing.
 */

class LinearProbingHashTable extends Hashing {
    private int count;
    public LinearProbingHashTable(int size) {
        super(size);
        count = 0;
    }

    /**
     * Inserts the given key into the hash table.
     */
    public void insert(int key) {
        if (count >= m) {
            throw new RuntimeException("Hash table is full");
        }

        int index = h1(key);
        while (table[index] != -1) {
            index = (index + 1) % m;
            collisions++;
        }
        table[index] = key;
        count++;
    }

    /**
     * Hashes the given key.
     * 
     * @param key the key to hash
     * @return the hash value
     */
    private int h1(int key) {
        return Math.floorMod(key, m);   

    }
}

/**
 * DoubleHashingHashTable implements a hash table using double hashing.
 */

class DoubleHashingHashTable extends Hashing {
    private int count;
    public DoubleHashingHashTable(int size) {
        super(size);
        count = 0;
    }

    /**
     * Inserts the given key into the hash table.
     */

    public void insert(int key) {
        if (count >= m) {
            throw new RuntimeException("Hash table is full");
        }

        int index = h1(key);
        int i = 1;
        while (table[index] != -1) {
            index = Math.floorMod(index + i * h2(key), m);
            i++;
            collisions++;
        }
        table[index] = key;
        count++;
    }

    /**
     * Hashes the given key.
     * 
     * @param key the key to hash
     * @return the hash value
     */

    private int h1(int key) {
        return Math.floorMod(key, m); 
    }

    /**
     * The second hash function used for double hashing.
     * 
     * @param key the key to hash
     * @return the hash value
     */

    private int h2(int key) {
        int hash = key % (m - 1);
        if (hash <= 0) {
            hash += (m - 1);
        }
        return 1 + hash;
    }

}

/**
 * HashTableTest is a class for testing the hash table.
 */

public class CompaireHashing {
public static void main(String[] args) {
    // Size of the hash table.
    int m = 10000003;

    Set<Integer> uniqueNumbers = new HashSet<>();
    Random random = new Random();
    while(uniqueNumbers.size() < m) {
        uniqueNumbers.add(random.nextInt());
    }
    
    int[] percentages = {50, 60, 70, 80, 90, 95, 99, 100};
    for (int p : percentages) {
        int numbersToInsert = (p * uniqueNumbers.size()) / 100;

        Hashing linear = new LinearProbingHashTable(m);
       
        long startTime = System.nanoTime();
        int i = 0;
        for (int number : uniqueNumbers) {
            if (i++ >= numbersToInsert) {
                break;
            }
            linear.insert(number);
        }
        long endTime = System.nanoTime();
        System.out.println("Linear probing " + p + "%: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("Collisions: " + linear.getCollisions());
    }
    for (int p : percentages) {
        int numbersToInsert = (p * uniqueNumbers.size()) / 100;

        Hashing doubleHashing = new DoubleHashingHashTable(m);

        long startTime = System.nanoTime();
        int i = 0;
        for (int number : uniqueNumbers) {
            if (i++ >= numbersToInsert) {
                break;
            }
            doubleHashing.insert(number);
        }
        long endTime = System.nanoTime();
        System.out.println("Double hashing " + p + "%: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("Collisions: " + doubleHashing.getCollisions());
    }
}

}

