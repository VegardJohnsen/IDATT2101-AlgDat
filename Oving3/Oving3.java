import java.util.Random;

/**
 * Class responsible for sorting arrays with the quicksort algorithm.
 * The algorithms are written with the inspirtation of the book "Algoritmer og datastrukturer"
 * by Helge Hafting and Mildrid Ljosland, chapter 3 "Sortering".
 */
public class Oving3 {
  private static Random random = new Random();
  private static int[] unsortedNumbers = new int[1000000];
  private static int[] sortedNumbers = new int[1000000];
  private static int[] constantNumbers = new int[1000000];
  private static int optimalThreshold = 0;



  /**
   * Method for testing the quicksort algorithm.
   *
   * @param array (int[]) array
   * @return timeTaken (long) time taken to sort the array
   */
  public static long testQuicksort(int[] array) {
    int[] copiedArray = array.clone();
    int sumArrayBefore = sumOfArray(copiedArray);   

    // Measures the time
    long startTime = System.currentTimeMillis();
    quicksort(copiedArray, 0, copiedArray.length - 1);    
    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;


    int sumArrayAfter = sumOfArray(copiedArray);    
    if (!isSorted(copiedArray)) {
      System.out.println("Array not sorted");
    }
    if (sumArrayBefore - sumArrayAfter != 0) {
      System.out.println("Not equal sum");
    }
    return timeTaken;  // Return the time taken
  }

    /**
     * Method for testing the quicksort algorithm with bubble.
     *
     * @param array (int[]) array
     * @return timeTaken (long) time taken to sort the array
     */
  public static long testQuickSortWithNThreshold(int[] array, int threshold) {
    int[] copiedArray = array.clone();
    int sumArrayBefore = sumOfArray(copiedArray);

    long startTime = System.currentTimeMillis();
    quicksortWithBubble(copiedArray, 0, copiedArray.length - 1, threshold);
    long endTime = System.currentTimeMillis();
    long timeTaken = endTime - startTime;

    System.out.println("------------------------");
    System.out.printf("Threshold: %d, Time: %d ms\n", threshold, timeTaken);
    int sumArrayAfter = sumOfArray(copiedArray);
    if (!isSorted(copiedArray)) {
      System.out.println("Array not sorted");
    }
    if (sumArrayBefore - sumArrayAfter != 0) {
      System.out.println("Not equal sum");
    }
    return timeTaken;
  }

  /**
   * Method for sorting the arrays with the quicksort algorithm.
   *
   * @param array (int[]) array
   * @param low (int) start index
   * @param high (int) end index
   */
  public static void quicksort(int []array, int low, int high) {
    if (high - low > 2) {
      int delepos = split(array, low, high);
      quicksort(array, low, delepos - 1);
      quicksort(array, delepos + 1, high);
    } else {
      median3sort(array, low, high);
    }
  }

  /**
   * Method for sorting the arrays with the quicksort algorithm.
   *
   * @param array (int[]) array
   * @param low (int) start index
   * @param high (int) end index
   */
  public static void quicksortWithBubble(int []array, int low, int high, int threshold) {
    if (high - low > threshold) {
      int delepos = split(array, low, high);
      quicksortWithBubble(array, low, delepos - 1, threshold);
      quicksortWithBubble(array, delepos + 1, high, threshold);
    } else {
      bobbleSort(array, low, high);
    }
  }

  /**
   * Private help method for the boblesort algorithm.
   * Swaps the integers in the array if the integer with index j
   * is bigger than the integer with index j + 1.
   *
   * @param array (int[]) array
   */
  private static void bobbleSort(int []array, int low, int high) { //Kjøretid: Theta(n^2)
    for (int i = high; i > low; --i) {
      for (int j = low; j < i; ++j) {
        if (array[j] > array[j + 1]) {
            swap(array, j, j+1);
        }
      }
    }
  }

  /**
   * Private help method for splitting the array into two part arrays,
   * and placing the integers with an index bigger or smaller than the index of the pivot.
   *
   * @param array (int[]) array
   * @param low (int) start index
   * @param high  (int) end index
   * @return iv (int) index of the pivot
   */
  private static int split(int[]array, int low, int high) {
    int iv;
    int ih;
    int m = median3sort(array, low, high);
    int dv = array[m];
    swap(array, m, high - 1);
    for (iv = low, ih = high - 1;;) {
      while (array[++iv] < dv);
      while (array[--ih] > dv);
      if (iv >= ih) {
        break;
      }
      swap(array, iv, ih);
    }
    swap(array, iv, high - 1);
    return iv;
  }

  /**
   * Private help method for finding the median of the array and sorting the three values.
   *
   * @param array (int[]) array
   * @param low (int) start index
   * @param high  (int) end index
   * @return m (int) median
   */
 private static int median3sort(int[] array, int low, int high) {
    int m = (low + high) / 2;
    if (array[low] > array[m]) {
      swap(array, low, m);
    }
    if (array[m] > array[high]) {
      swap(array, m, high);
      if (array[low] > array[m]) {
        swap(array, low, m);
      }
    }
    return m;
  }

  /**
   * Private help method for switching the values of the array.
   *
   * @param array (int[]) array
   * @param element1 (int) index of one of the integers
   * @param element2 (int) index of the other integer
   */
  private static void swap(int[] array, int element1, int element2) {
    int k = array[element2];
    array[element2] = array[element1];
    array[element1] = k;
  }

  /**
   * Private help method for filling an array with random numbers.
   * Differentiating between unsorted lists with and without duplicates.
   *
   * @param array (int[])
   */
  private static void fillArrayWithRandomNumbers(int[] array, String type) {
    if (type == "unsorted") {
      for (int i = 0; i < array.length; i++) {
        array[i] = random.nextInt(1000000);

      }
    } else if (type == "sorted") {
      for (int i = 0; i < array.length; i++) {
        array[i] = i;
      }
    } else if (type == "duplicates") {
      for (int i = 0; i < array.length; i++) {
        array[i] = random.nextInt(1000000);
        if (i % 2 == 0) {
          array[i] = 42;
        }
      }
    }
  }

  /**
   * Private help method for calculating the sum of an array.
   *
   * @param array (int[])
   * @return sum (int) sum of the array
   */
  private static int sumOfArray(int[] array) {
    int sum = 0;
      for (int j : array) {
          sum += j;
      }
    return sum;
  }

    /**
     * Private help method for checking if the array is sorted.
     *
     * @param array (int[])
     * @return true if the array is sorted, false if not
     */
  private static boolean isSorted(int[] array) {
    for (int i = 1; i < array.length - 1; i++) {
      if (array[i] < array[i - 1]) {
        return false;
      }
    }
    return true;
  }
  public static void main(String[] args) {
    int MAX_THRESHOLD = 100;
    long optimalTime = Long.MAX_VALUE;

    // Fill the arrays with random numbers
    fillArrayWithRandomNumbers(unsortedNumbers, "unsorted");


    // Testing pure quicksort
    long quicksortTime = testQuicksort(unsortedNumbers);


    // Testing quicksort with bubble and finding the optimal threshold
    for (int i = 2; i <= MAX_THRESHOLD; i++) {
      System.out.println();
      long currentTime = testQuickSortWithNThreshold(unsortedNumbers, i);
      if (currentTime < optimalTime) {
        optimalTime = currentTime;
        optimalThreshold = i;
      }
    }
    System.out.println("\n------------------------");
    System.out.println("Optimal threshold: " + optimalThreshold);
    System.out.println("Time: " + optimalTime+"ms \n");
    int percent = (int) ((quicksortTime - optimalTime) / (double) quicksortTime * 100);
    System.out.println("Pure quicksort time: " + quicksortTime + "ms");
    System.out.println("Quicksort with bubble is " + percent + "% faster than pure quicksort");


    // Testing quicksort with bubble on sorted array
    fillArrayWithRandomNumbers(sortedNumbers, "sorted");
    long timeSortedList = testQuicksort(constantNumbers);
    System.out.println("Sorted list time: " + timeSortedList + "ms");

    // Testing quicksort with bubble on array with duplicates
    fillArrayWithRandomNumbers(constantNumbers, "duplicates");
    long timeDuplicateList = testQuicksort(constantNumbers);
    System.out.println("Duplicate list time: " + timeDuplicateList + "ms");
  }
}


