import sorting.BubbleSort;
import sorting.MergeSort;
import sorting.QuickSortFirst;
import sorting.QuickSortRandom;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    static Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("=== Olimpiadas de la Ordenacion ===");
        System.out.println("Comparando algoritmos de ordenamiento\n");

        int[] sizes = {10000, 100000, 1000000};
        ArrayList<String> results = new ArrayList<>();

        for (int n : sizes) {
            System.out.println("--- Probando con n = " + n + " ---");

            // generar los 3 tipos de arreglos
            int[] aleatorio = generarAleatorio(n);
            int[] ordenado = generarOrdenado(n);
            int[] inverso = generarInverso(n);

            // Bubble Sort
            results.add(probarBubbleSort(aleatorio.clone(), "Aleatorio", n));
            results.add(probarBubbleSort(ordenado.clone(), "Ordenado", n));
            results.add(probarBubbleSort(inverso.clone(), "Inverso", n));

            // Merge Sort
            results.add(probarMergeSort(aleatorio.clone(), "Aleatorio", n));
            results.add(probarMergeSort(ordenado.clone(), "Ordenado", n));
            results.add(probarMergeSort(inverso.clone(), "Inverso", n));

            // Quick Sort Random
            results.add(probarQuickRandom(aleatorio.clone(), "Aleatorio", n));
            results.add(probarQuickRandom(ordenado.clone(), "Ordenado", n));
            results.add(probarQuickRandom(inverso.clone(), "Inverso", n));

            // Quick Sort First
            results.add(probarQuickFirst(aleatorio.clone(), "Aleatorio", n));
            results.add(probarQuickFirst(ordenado.clone(), "Ordenado", n));
            results.add(probarQuickFirst(inverso.clone(), "Inverso", n));
        }

        // mostrar resultados
        System.out.println("\n=== RESULTADOS ===\n");
        for (String r : results) {
            System.out.println(r);
        }

        // calcular CO2
        String co2 = calcularCO2(results);
        System.out.println("\n" + co2);

        // guardar en archivo
        guardarResultados(results, co2);
        System.out.println("\nResultados guardados en results/benchmark_results.md");
    }

    // genera un arreglo con valores aleatorios
    static int[] generarAleatorio(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(n * 10);
        }
        return arr;
    }

    // genera un arreglo ya ordenado
    static int[] generarOrdenado(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    // genera un arreglo en orden inverso
    static int[] generarInverso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    // prueba bubble sort y retorna el resultado como string
    static String probarBubbleSort(int[] arr, String tipo, int n) {
        int repeticiones = 10;
        long total = 0;

        for (int i = 0; i < repeticiones; i++) {
            int[] copia = arr.clone();
            long inicio = System.nanoTime();
            BubbleSort.sort(copia);
            long fin = System.nanoTime();
            total += (fin - inicio);
        }

        double promedioMs = (total / (double) repeticiones) / 1_000_000.0;
        String linea = String.format("Bubble Sort | n=%d | %s | %.4f ms", n, tipo, promedioMs);
        System.out.println(linea);
        return linea;
    }

    // prueba merge sort y retorna el resultado como string
    static String probarMergeSort(int[] arr, String tipo, int n) {
        int repeticiones = 10;
        long total = 0;
        int altura = 0;

        for (int i = 0; i < repeticiones; i++) {
            int[] copia = arr.clone();
            long inicio = System.nanoTime();
            MergeSort.sort(copia);
            long fin = System.nanoTime();
            total += (fin - inicio);
            altura = MergeSort.getHeight();
        }

        double promedioMs = (total / (double) repeticiones) / 1_000_000.0;
        String linea = String.format("Merge Sort | n=%d | %s | %.4f ms | altura=%d", n, tipo, promedioMs, altura);
        System.out.println(linea);
        return linea;
    }

    // prueba quick sort con pivote aleatorio
    static String probarQuickRandom(int[] arr, String tipo, int n) {
        int repeticiones = 10;
        long total = 0;
        int altura = 0;

        for (int i = 0; i < repeticiones; i++) {
            int[] copia = arr.clone();
            long inicio = System.nanoTime();
            QuickSortRandom.sort(copia);
            long fin = System.nanoTime();
            total += (fin - inicio);
            altura = QuickSortRandom.getHeight();
        }

        double promedioMs = (total / (double) repeticiones) / 1_000_000.0;
        String linea = String.format("Quick Sort (Random) | n=%d | %s | %.4f ms | altura=%d", n, tipo, promedioMs, altura);
        System.out.println(linea);
        return linea;
    }

    // prueba quick sort con primer elemento como pivote
    static String probarQuickFirst(int[] arr, String tipo, int n) {
        int repeticiones = 10;
        long total = 0;
        int altura = 0;
        boolean stackOverflow = false;

        for (int i = 0; i < repeticiones; i++) {
            try {
                int[] copia = arr.clone();
                long inicio = System.nanoTime();
                QuickSortFirst.sort(copia);
                long fin = System.nanoTime();
                total += (fin - inicio);
                altura = QuickSortFirst.getHeight();
            } catch (StackOverflowError e) {
                stackOverflow = true;
                System.out.println("  Stack Overflow en Quick Sort (First) con " + tipo + " n=" + n);
                break;
            }
        }

        String linea;
        if (stackOverflow) {
            linea = String.format("Quick Sort (First) | n=%d | %s | STACK OVERFLOW | altura=%d", n, tipo, n);
        } else {
            double promedioMs = (total / (double) repeticiones) / 1_000_000.0;
            linea = String.format("Quick Sort (First) | n=%d | %s | %.4f ms | altura=%d", n, tipo, promedioMs, altura);
        }
        System.out.println(linea);
        return linea;
    }

    // calcula el impacto ambiental en CO2
    static String calcularCO2(ArrayList<String> results) {
        double potenciaCPU = 65.0; // watts
        double factorEmision = 0.5; // gramos CO2 por kJ
        long operaciones = 1_000_000_000L;

        // buscar los tiempos para n=1000000 aleatorio
        double tiempoBubble = buscarTiempo(results, "Bubble Sort", 1000000, "Aleatorio");
        double tiempoQuickRandom = buscarTiempo(results, "Quick Sort (Random)", 1000000, "Aleatorio");
        double tiempoMerge = buscarTiempo(results, "Merge Sort", 1000000, "Aleatorio");

        // calcular energia y CO2 para cada uno
        double tiempoBubbleS = tiempoBubble / 1000.0;
        double tiempoQuickS = tiempoQuickRandom / 1000.0;
        double tiempoMergeS = tiempoMerge / 1000.0;

        double energiaBubble = potenciaCPU * tiempoBubbleS / 1000.0;
        double energiaQuick = potenciaCPU * tiempoQuickS / 1000.0;
        double energiaMerge = potenciaCPU * tiempoMergeS / 1000.0;

        double co2Bubble = energiaBubble * factorEmision;
        double co2Quick = energiaQuick * factorEmision;
        double co2Merge = energiaMerge * factorEmision;

        // ahorro anual
        double ahorroQuick = (co2Bubble - co2Quick) * operaciones / 1000.0;
        double ahorroMerge = (co2Bubble - co2Merge) * operaciones / 1000.0;

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Analisis de CO2 ===\n\n");
        sb.append(String.format("Bubble Sort: %.6f g CO2 por operacion\n", co2Bubble));
        sb.append(String.format("Quick Sort (Random): %.6f g CO2 por operacion\n", co2Quick));
        sb.append(String.format("Merge Sort: %.6f g CO2 por operacion\n\n", co2Merge));
        sb.append(String.format("Ahorro anual usando Quick Sort: %.2f kg CO2\n", ahorroQuick));
        sb.append(String.format("Ahorro anual usando Merge Sort: %.2f kg CO2\n", ahorroMerge));
        sb.append(String.format("\nEquivale a %.0f arboles absorbiendo CO2 por 10 anios\n", ahorroQuick * 10 / 1000.0 / 21.77 * 1000));

        return sb.toString();
    }

    // busca el tiempo de un resultado en la lista
    static double buscarTiempo(ArrayList<String> results, String algoritmo, int n, String tipo) {
        for (String r : results) {
            if (r.contains(algoritmo) && r.contains("n=" + n) && r.contains(tipo) && !r.contains("STACK")) {
                // extraer el numero del tiempo
                String[] partes = r.split("\\|");
                for (String p : partes) {
                    p = p.trim();
                    if (p.contains("ms")) {
                        String num = p.replace("ms", "").trim();
                        return Double.parseDouble(num);
                    }
                }
            }
        }
        return 0;
    }

    // guarda los resultados en un archivo markdown
    static void guardarResultados(ArrayList<String> results, String co2) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("results/benchmark_results.md"));
            writer.println("# Olimpiadas de la Ordenacion - Resultados\n");
            writer.println("## Tabla de Resultados\n");
            writer.println("| Algoritmo | n | Tipo | Tiempo (ms) | Altura |");
            writer.println("|-----------|---|------|-------------|--------|");

            for (String r : results) {
                String[] partes = r.split("\\|");
                writer.print("|");
                for (String p : partes) {
                    writer.print(" " + p.trim() + " |");
                }
                writer.println();
            }

            writer.println("\n---\n");
            writer.println(co2);
            writer.println("\n---\n");
            writer.println("## Degeneracion del Quick Sort\n");
            writer.println("Cuando Quick Sort (First) se ejecuta con datos ya ordenados,");
            writer.println("la altura de recursion tiende a n, causando Stack Overflow.");
            writer.println("Esto es parecido a como un ABB degenera en lista enlazada.");

            writer.close();
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
