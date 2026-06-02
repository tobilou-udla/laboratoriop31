package sorting;

public class QuickSortFirst {
    
    private static int maxHeight = 0;
    
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
        int pivot = arr[low];
        int i = low + 1;
        int j = high;
        
        while (i <= j) {
            while (i <= j && arr[i] <= pivot) {
                i++;
            }
            while (i <= j && arr[j] > pivot) {
                j--;
            }
            if (i < j) {
                swap(arr, i, j);
            }
        }
        
        swap(arr, low, j);
        return j;
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
