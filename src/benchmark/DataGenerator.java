package benchmark;

import java.util.Random;

public class DataGenerator {
    
    private static final Random random = new Random();
    
    public static int[] generateRandom(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n * 10);
        }
        return arr;
    }
    
    public static int[] generateSorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }
    
    public static int[] generateReverseSorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }
    
    public static int[] copyArray(int[] arr) {
        return arr.clone();
    }
}
