package sorting;

import java.util.Random;

public class QuickSortRandom {
    
    private static int maxHeight = 0;
    private static Random random = new Random();
    
    public static void sort(int[] arr) {
        maxHeight = 0;
        if (arr.length > 1) {
            quickSort(arr, 0, arr.length - 1, 0);
        }
    }
    
    private static void quickSort(int[] arr, int low, int high, int currentHeight) {
        if (low < high) {
            maxHeight = Math.max(maxHeight, currentHeight);
            
            int pivotIndex = partition(arr, low, high);
            
            quickSort(arr, low, pivotIndex - 1, currentHeight + 1);
            quickSort(arr, pivotIndex + 1, high, currentHeight + 1);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
        
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static int getHeight() {
        return maxHeight;
    }
}
