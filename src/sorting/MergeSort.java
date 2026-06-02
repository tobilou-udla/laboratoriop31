package sorting;

public class MergeSort {
    
    private static int maxHeight = 0;
    
    public static void sort(int[] arr) {
        maxHeight = 0;
        if (arr.length > 1) {
            mergeSort(arr, 0, arr.length - 1, 0);
        }
    }
    
    private static void mergeSort(int[] arr, int left, int right, int currentHeight) {
        if (left < right) {
            maxHeight = Math.max(maxHeight, currentHeight);
            
            int mid = left + (right - left) / 2;
            
            mergeSort(arr, left, mid, currentHeight + 1);
            mergeSort(arr, mid + 1, right, currentHeight + 1);
            
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    
    public static int getHeight() {
        return maxHeight;
    }
}
