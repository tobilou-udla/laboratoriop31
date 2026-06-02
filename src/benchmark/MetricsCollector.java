package benchmark;

import java.util.ArrayList;
import java.util.List;
import sorting.*;

public class MetricsCollector {
    
    private static final int ITERATIONS = 3;
    
    public static BenchmarkResult measureBubbleSort(int[] original, String dataType) {
        long totalTime = 0;
        int height = 0;
        
        for (int i = 0; i < ITERATIONS; i++) {
            int[] arr = DataGenerator.copyArray(original);
            long startTime = System.nanoTime();
            BubbleSort.sort(arr);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        
        return new BenchmarkResult("Bubble Sort", original.length, dataType, 
            (double) totalTime / ITERATIONS, BubbleSort.getHeight());
    }
    
    public static BenchmarkResult measureMergeSort(int[] original, String dataType) {
        long totalTime = 0;
        
        for (int i = 0; i < ITERATIONS; i++) {
            int[] arr = DataGenerator.copyArray(original);
            long startTime = System.nanoTime();
            MergeSort.sort(arr);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        
        return new BenchmarkResult("Merge Sort", original.length, dataType, 
            (double) totalTime / ITERATIONS, MergeSort.getHeight());
    }
    
    public static BenchmarkResult measureQuickSortRandom(int[] original, String dataType) {
        long totalTime = 0;
        
        for (int i = 0; i < ITERATIONS; i++) {
            int[] arr = DataGenerator.copyArray(original);
            long startTime = System.nanoTime();
            QuickSortRandom.sort(arr);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        
        return new BenchmarkResult("Quick Sort (Random)", original.length, dataType, 
            (double) totalTime / ITERATIONS, QuickSortRandom.getHeight());
    }
    
    public static BenchmarkResult measureQuickSortFirst(int[] original, String dataType) {
        long totalTime = 0;
        int height = 0;
        boolean stackOverflow = false;
        
        for (int i = 0; i < ITERATIONS; i++) {
            try {
                int[] arr = DataGenerator.copyArray(original);
                long startTime = System.nanoTime();
                QuickSortFirst.sort(arr);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
                height = QuickSortFirst.getHeight();
            } catch (StackOverflowError e) {
                stackOverflow = true;
                System.out.println("  ⚠ Stack Overflow detectado en Quick Sort (First) con " + dataType + " n=" + original.length);
                break;
            }
        }
        
        if (stackOverflow) {
            return new BenchmarkResult("Quick Sort (First)", original.length, dataType + " [STACK OVERFLOW]", 
                -1, original.length);
        }
        
        return new BenchmarkResult("Quick Sort (First)", original.length, dataType, 
            (double) totalTime / ITERATIONS, height);
    }
    
    public static List<BenchmarkResult> runAllBenchmarks(int[] sizes) {
        List<BenchmarkResult> results = new ArrayList<>();
        String[] dataTypes = {"Aleatorio", "Ordenado", "Inverso"};
        
        for (int n : sizes) {
            System.out.println("Probando n = " + n + "...");
            
            int[] randomArr = DataGenerator.generateRandom(n);
            int[] sortedArr = DataGenerator.generateSorted(n);
            int[] reverseArr = DataGenerator.generateReverseSorted(n);
            
            results.add(measureBubbleSort(randomArr, "Aleatorio"));
            results.add(measureBubbleSort(sortedArr, "Ordenado"));
            results.add(measureBubbleSort(reverseArr, "Inverso"));
            
            results.add(measureMergeSort(randomArr, "Aleatorio"));
            results.add(measureMergeSort(sortedArr, "Ordenado"));
            results.add(measureMergeSort(reverseArr, "Inverso"));
            
            results.add(measureQuickSortRandom(randomArr, "Aleatorio"));
            results.add(measureQuickSortRandom(sortedArr, "Ordenado"));
            results.add(measureQuickSortRandom(reverseArr, "Inverso"));
            
            results.add(measureQuickSortFirst(randomArr, "Aleatorio"));
            results.add(measureQuickSortFirst(sortedArr, "Ordenado"));
            results.add(measureQuickSortFirst(reverseArr, "Inverso"));
        }
        
        return results;
    }
}
