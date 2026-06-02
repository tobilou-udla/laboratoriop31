package main;

import benchmark.*;
import analysis.CO2Calculator;
import analysis.ResultsAnalyzer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    
    public static void main(String[] args) {
        System.out.println("=== OLIMPIADAS DE LA ORDENACIÓN ===");
        System.out.println("Iniciando benchmark de algoritmos...\n");
        
        long startTime = System.currentTimeMillis();
        
        List<BenchmarkResult> results = MetricsCollector.runAllBenchmarks(SIZES);
        
        System.out.println("\n--- RESULTADOS ---\n");
        
        for (BenchmarkResult result : results) {
            System.out.println(result);
        }
        
        BenchmarkResult bubbleRandom = findResult(results, "Bubble Sort", 100_000, "Aleatorio");
        BenchmarkResult quickRandom = findResult(results, "Quick Sort (Random)", 100_000, "Aleatorio");
        BenchmarkResult mergeRandom = findResult(results, "Merge Sort", 100_000, "Aleatorio");
        
        String co2Report = CO2Calculator.generateCO2Report(
            bubbleRandom.getAverageTimeMs(),
            quickRandom.getAverageTimeMs(),
            mergeRandom.getAverageTimeMs()
        );
        
        String markdownReport = generateMarkdownReport(results, co2Report);
        
        saveToFile("results/benchmark_results.md", markdownReport);
        
        long endTime = System.currentTimeMillis();
        double totalSeconds = (endTime - startTime) / 1000.0;
        
        System.out.println("\n=== COMPLETADO ===");
        System.out.printf("Tiempo total de ejecución: %.2f segundos%n", totalSeconds);
        System.out.println("Resultados guardados en: results/benchmark_results.md");
    }
    
    private static BenchmarkResult findResult(List<BenchmarkResult> results, 
                                           String algorithm, int size, String dataType) {
        for (BenchmarkResult result : results) {
            if (result.getAlgorithm().equals(algorithm) && 
                result.getSize() == size && 
                result.getDataType().contains(dataType)) {
                return result;
            }
        }
        return null;
    }
    
    private static String generateMarkdownReport(List<BenchmarkResult> results, String co2Report) {
        StringBuilder report = new StringBuilder();
        
        report.append("# Guía de Laboratorio: Las Olimpiadas de la Ordenación\n\n");
        report.append("## Resultados del Benchmark\n\n");
        report.append("**Protocolo:** 10 ejecuciones por configuración, media aritmética\n");
        report.append("**Entorno:** Java con System.nanoTime()\n\n");
        
        report.append("### Tabla de Resultados (Tiempo promedio en ms)\n\n");
        report.append("| Algoritmo | n=10,000 | n=50,000 | n=100,000 | Altura (h) |\n");
        report.append("|-----------|----------|----------|-----------|------------|\n");
        
        String[] algorithms = {"Bubble Sort", "Merge Sort", "Quick Sort (Random)", "Quick Sort (First)"};
        String[] dataTypes = {"Aleatorio", "Ordenado", "Inverso"};
        
        for (String algorithm : algorithms) {
            for (String dataType : dataTypes) {
                report.append(String.format("| %s (%s)", algorithm, dataType));
                
                for (int size : SIZES) {
                    BenchmarkResult result = findResult(results, algorithm, size, dataType);
                    if (result != null) {
                        if (result.isStackOverflow()) {
                            report.append(" | **STACK OVERFLOW**");
                        } else {
                            report.append(String.format(" | %.2f", result.getAverageTimeMs()));
                        }
                    } else {
                        report.append(" | N/A");
                    }
                }
                
                BenchmarkResult lastResult = findResult(results, algorithm, SIZES[SIZES.length - 1], dataType);
                if (lastResult != null) {
                    if (lastResult.isStackOverflow()) {
                        report.append(String.format(" | %d (O(n))", lastResult.getHeight()));
                    } else {
                        report.append(String.format(" | %d", lastResult.getHeight()));
                    }
                } else {
                    report.append(" | N/A");
                }
                
                report.append("\n");
            }
        }
        
        report.append("\n---\n\n");
        report.append(ResultsAnalyzer.generateDetailedAnalysis(results));
        report.append("\n---\n\n");
        report.append(co2Report);
        
        report.append("\n---\n\n");
        report.append("## Observación: Degeneración del Quick Sort\n\n");
        report.append("**Fenómeno:** Cuando Quick Sort (First) se ejecuta sobre un arreglo **ya ordenado**,\n");
        report.append("la altura de recursión tiende a **n**, causando **Stack Overflow**.\n\n");
        report.append("**Explicación:** Con el primer elemento como pivote en un arreglo ordenado,\n");
        report.append("cada partición reduce el arreglo solo en 1 elemento, generando una recursión de profundidad n.\n\n");
        report.append("**Conexión con ABB:** Esto demuestra cómo un ABB degenera en una lista enlazada\n");
        report.append("cuando los elementos se insertan en orden, pasando de O(n log n) a O(n²).\n");
        
        return report.toString();
    }
    
    private static void saveToFile(String filename, String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(content);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
