package sorting;

// Quick Sort con primer elemento como pivote
public class QuickSortFirst {

    private static int alturaMax = 0;

    public static void sort(int[] arr) {
        alturaMax = 0;
        if (arr.length > 1) {
            quickSort(arr, 0, arr.length - 1, 0);
        }
    }

    // ordena por recursividad
    private static void quickSort(int[] arr, int bajo, int alto, int altura) {
        if (bajo < alto) {
            if (altura > alturaMax) {
                alturaMax = altura;
            }

            // particionar y obtener indice del pivote
            int pivote = particion(arr, bajo, alto);

            // ordenar las dos mitades
            quickSort(arr, bajo, pivote - 1, altura + 1);
            quickSort(arr, pivote + 1, alto, altura + 1);
        }
    }

    // divide el arreglo usando el primer elemento como pivote
    private static int particion(int[] arr, int bajo, int alto) {
        int pivote = arr[bajo];
        int i = bajo + 1;
        int j = alto;

        while (i <= j) {
            while (i <= j && arr[i] <= pivote) {
                i++;
            }
            while (i <= j && arr[j] > pivote) {
                j--;
            }
            if (i < j) {
                intercambiar(arr, i, j);
            }
        }

        intercambiar(arr, bajo, j);
        return j;
    }

    // intercambia dos elementos
    private static void intercambiar(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int getHeight() {
        return alturaMax;
    }
}
