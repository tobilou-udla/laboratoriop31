package sorting;

// Bubble Sort - O(n^2)
public class BubbleSort {

    public static void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            // comparar pares de elementos
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // intercambiar
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // si no hubo intercambios, ya esta ordenado
            if (!swapped) {
                break;
            }
        }
    }

    public static int getHeight() {
        return 0;
    }
}
