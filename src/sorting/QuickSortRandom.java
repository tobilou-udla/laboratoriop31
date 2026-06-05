package sorting;

import java.util.Random;

// Quick Sort con pivote aleatorio - O(n log n) promedio
public class QuickSortRandom {

    private static int alturaMax = 0;
    static Random rand = new Random();

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

            int pivote = particion(arr, bajo, alto);

            // ordenar las dos mitades
            quickSort(arr, bajo, pivote - 1, altura + 1);
            quickSort(arr, pivote + 1, alto, altura + 1);
        }
    }

    // divide el arreglo usando un pivote aleatorio
    private static int particion(int[] arr, int bajo, int alto) {
        // elegir pivote al azar y moverlo al final
        int indiceRandom = bajo + rand.nextInt(alto - bajo + 1);
        intercambiar(arr, indiceRandom, alto);

        int pivote = arr[alto];
        int i = bajo - 1;

        for (int j = bajo; j < alto; j++) {
            if (arr[j] <= pivote) {
                i++;
                intercambiar(arr, i, j);
            }
        }

        intercambiar(arr, i + 1, alto);
        return i + 1;
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
