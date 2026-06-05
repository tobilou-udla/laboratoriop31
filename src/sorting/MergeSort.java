package sorting;

// Merge Sort - O(n log n)
public class MergeSort {

    private static int alturaMax = 0;

    public static void sort(int[] arr) {
        alturaMax = 0;
        if (arr.length > 1) {
            mergeSort(arr, 0, arr.length - 1, 0);
        }
    }

    // divide el arreglo y ordena cada parte
    private static void mergeSort(int[] arr, int izq, int der, int altura) {
        if (izq < der) {
            if (altura > alturaMax) {
                alturaMax = altura;
            }

            int medio = izq + (der - izq) / 2;

            // ordenar mitad izquierda y derecha
            mergeSort(arr, izq, medio, altura + 1);
            mergeSort(arr, medio + 1, der, altura + 1);

            // mezclar las dos mitades ordenadas
            merge(arr, izq, medio, der);
        }
    }

    // mezcla dos subarreglos ordenados
    private static void merge(int[] arr, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        // crear arreglos temporales
        int[] tempIzq = new int[n1];
        int[] tempDer = new int[n2];

        for (int i = 0; i < n1; i++) {
            tempIzq[i] = arr[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            tempDer[j] = arr[medio + 1 + j];
        }

        // mezclar de vuelta en el arreglo original
        int i = 0, j = 0, k = izq;

        while (i < n1 && j < n2) {
            if (tempIzq[i] <= tempDer[j]) {
                arr[k] = tempIzq[i];
                i++;
            } else {
                arr[k] = tempDer[j];
                j++;
            }
            k++;
        }

        // copiar lo que sobra
        while (i < n1) {
            arr[k] = tempIzq[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = tempDer[j];
            j++;
            k++;
        }
    }

    public static int getHeight() {
        return alturaMax;
    }
}
