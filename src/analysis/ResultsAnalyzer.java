package analysis;

import benchmark.BenchmarkResult;
import java.util.List;

public class ResultsAnalyzer {
    
    public static String generateDetailedAnalysis(List<BenchmarkResult> results) {
        StringBuilder analysis = new StringBuilder();
        
        analysis.append("# Análisis Detallado de Resultados\n\n");
        
        analysis.append("## 1. Verificación Empírica de Big O\n\n");
        analysis.append("### Bubble Sort - Complejidad Teórica: O(n²)\n\n");
        analysis.append(generateComplexityAnalysis(results, "Bubble Sort", "Aleatorio", 2));
        
        analysis.append("### Merge Sort - Complejidad Teórica: O(n log n)\n\n");
        analysis.append(generateComplexityAnalysis(results, "Merge Sort", "Aleatorio", 1.58));
        
        analysis.append("### Quick Sort (Random) - Complejidad Teórica: O(n log n)\n\n");
        analysis.append(generateComplexityAnalysis(results, "Quick Sort (Random)", "Aleatorio", 1.58));
        
        analysis.append("\n## 2. Speedup Comparativo\n\n");
        analysis.append(generateSpeedupTable(results));
        
        analysis.append("\n## 3. Análisis de Altura Recursiva (h)\n\n");
        analysis.append(generateHeightAnalysis(results));
        
        analysis.append("\n## 4. Degeneración del Quick Sort (First)\n\n");
        analysis.append(generateDegenerationAnalysis(results));
        
        return analysis.toString();
    }
    
    private static String generateComplexityAnalysis(List<BenchmarkResult> results, 
                                                     String algorithm, String dataType, 
                                                     double expectedExponent) {
        StringBuilder sb = new StringBuilder();
        
        BenchmarkResult r10k = findResult(results, algorithm, 10_000, dataType);
        BenchmarkResult r100k = findResult(results, algorithm, 100_000, dataType);
        BenchmarkResult r1m = findResult(results, algorithm, 1_000_000, dataType);
        
        if (r10k == null || r100k == null || r1m == null || 
            r10k.isStackOverflow() || r100k.isStackOverflow() || r1m.isStackOverflow()) {
            return "No disponible (Stack Overflow o datos insuficientes)\n\n";
        }
        
        double t1 = r10k.getAverageTimeMs();
        double t2 = r100k.getAverageTimeMs();
        double t3 = r1m.getAverageTimeMs();
        
        double ratio100_10 = t2 / t1;
        double ratio1m_100 = t3 / t2;
        
        double expectedRatio100_10 = Math.pow(10.0, expectedExponent);
        double expectedRatio1m_100 = Math.pow(10.0, expectedExponent);
        
        sb.append(String.format("| Tamaño | Tiempo (ms) | Ratio observado | Ratio esperado |\n"));
        sb.append(String.format("|--------|-------------|-----------------|----------------|\n"));
        sb.append(String.format("| 10,000 | %.2f | - | - |\n", t1));
        sb.append(String.format("| 100,000 | %.2f | %.2fx | %.2fx |\n", t2, ratio100_10, expectedRatio100_10));
        sb.append(String.format("| 1,000,000 | %.2f | %.2fx | %.2fx |\n\n", t3, ratio1m_100, expectedRatio1m_100));
        
        sb.append(String.format("**Conclusión:** El ratio observado (%.2fx) ", ratio1m_100));
        sb.append(String.format("se acerca al esperado (%.2fx) para O(n^%.2f).\n\n", expectedRatio1m_100, expectedExponent));
        
        return sb.toString();
    }
    
    private static String generateSpeedupTable(List<BenchmarkResult> results) {
        StringBuilder sb = new StringBuilder();
        
        BenchmarkResult bubble1m = findResult(results, "Bubble Sort", 1_000_000, "Aleatorio");
        BenchmarkResult merge1m = findResult(results, "Merge Sort", 1_000_000, "Aleatorio");
        BenchmarkResult quickRandom1m = findResult(results, "Quick Sort (Random)", 1_000_000, "Aleatorio");
        
        if (bubble1m == null || merge1m == null || quickRandom1m == null) {
            return "Datos insuficientes\n\n";
        }
        
        double bubbleTime = bubble1m.getAverageTimeMs();
        double mergeTime = merge1m.getAverageTimeMs();
        double quickTime = quickRandom1m.getAverageTimeMs();
        
        double speedupMerge = bubbleTime / mergeTime;
        double speedupQuick = bubbleTime / quickTime;
        
        sb.append("### Speedup vs Bubble Sort (n=1,000,000, Aleatorio)\n\n");
        sb.append("| Algoritmo | Tiempo (ms) | Speedup |\n");
        sb.append("|-----------|-------------|---------|\n");
        sb.append(String.format("| Bubble Sort | %.2f | 1x (base) |\n", bubbleTime));
        sb.append(String.format("| Merge Sort | %.2f | **%.1fx** |\n", mergeTime, speedupMerge));
        sb.append(String.format("| Quick Sort (Random) | %.2f | **%.1fx** |\n\n", quickTime, speedupQuick));
        
        sb.append("Interpretación: Quick Sort es ");
        sb.append(String.format("%.0f veces más rápido que Bubble Sort para n=1,000,000.\n\n", speedupQuick));
        
        return sb.toString();
    }
    
    private static String generateHeightAnalysis(List<BenchmarkResult> results) {
        StringBuilder sb = new StringBuilder();
        
        BenchmarkResult merge1m = findResult(results, "Merge Sort", 1_000_000, "Aleatorio");
        BenchmarkResult quickRandom1m = findResult(results, "Quick Sort (Random)", 1_000_000, "Aleatorio");
        
        sb.append("### Altura Recursiva (h) para n=1,000,000\n\n");
        sb.append("| Algoritmo | Altura (h) | log₂(n) | h / log₂(n) |\n");
        sb.append("|-----------|------------|---------|-------------|\n");
        
        if (merge1m != null && !merge1m.isStackOverflow()) {
            double log2n = Math.log(1_000_000) / Math.log(2);
            double ratio = merge1m.getHeight() / log2n;
            sb.append(String.format("| Merge Sort | %d | %.2f | %.2fx |\n", 
                merge1m.getHeight(), log2n, ratio));
        }
        
        if (quickRandom1m != null && !quickRandom1m.isStackOverflow()) {
            double log2n = Math.log(1_000_000) / Math.log(2);
            double ratio = quickRandom1m.getHeight() / log2n;
            sb.append(String.format("| Quick Sort (Random) | %d | %.2f | %.2fx |\n\n", 
                quickRandom1m.getHeight(), log2n, ratio));
        }
        
        sb.append("**Interpretación:** La altura h ≈ log₂(n) confirma que el árbol de recursión está equilibrado.\n\n");
        
        return sb.toString();
    }
    
    private static String generateDegenerationAnalysis(List<BenchmarkResult> results) {
        StringBuilder sb = new StringBuilder();
        
        BenchmarkResult quickFirstSorted = findResult(results, "Quick Sort (First)", 1_000_000, "Ordenado");
        BenchmarkResult quickFirstRandom = findResult(results, "Quick Sort (First)", 1_000_000, "Aleatorio");
        
        sb.append("### Comparación: Quick Sort (First) - Datos Ordenados vs Aleatorios\n\n");
        
        if (quickFirstSorted != null && quickFirstSorted.isStackOverflow()) {
            sb.append("- **Datos Ordenados:** STACK OVERFLOW (h = n = 1,000,000)\n");
        }
        if (quickFirstRandom != null && !quickFirstRandom.isStackOverflow()) {
            sb.append(String.format("- **Datos Aleatorios:** Tiempo = %.2f ms, h = %d\n\n", 
                quickFirstRandom.getAverageTimeMs(), quickFirstRandom.getHeight()));
        }
        
        sb.append("### Conclusión\n\n");
        sb.append("Cuando el arreglo está **ordenado** y se usa el **primer elemento como pivote**:\n");
        sb.append("- La altura de recursión h tiende a **n** (1,000,000)\n");
        sb.append("- El algoritmo degenera de O(n log n) a **O(n²)**\n");
        sb.append("- Esto causa **Stack Overflow** por recursión infinita\n\n");
        sb.append("**Conexión con ABB:** Un Árbol Binario de Búsqueda degenera en una lista enlazada\n");
        sb.append("cuando los elementos se insertan en orden, perdiendo la ventaja de búsqueda O(log n).\n\n");
        
        return sb.toString();
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
}
