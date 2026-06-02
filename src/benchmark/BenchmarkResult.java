package benchmark;

public class BenchmarkResult {
    
    private final String algorithm;
    private final int size;
    private final String dataType;
    private final double averageTimeNs;
    private final int height;
    
    public BenchmarkResult(String algorithm, int size, String dataType, double averageTimeNs, int height) {
        this.algorithm = algorithm;
        this.size = size;
        this.dataType = dataType;
        this.averageTimeNs = averageTimeNs;
        this.height = height;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public int getSize() {
        return size;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public double getAverageTimeNs() {
        return averageTimeNs;
    }
    
    public double getAverageTimeMs() {
        return averageTimeNs / 1_000_000.0;
    }
    
    public double getAverageTimeS() {
        return averageTimeNs / 1_000_000_000.0;
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean isStackOverflow() {
        return averageTimeNs == -1;
    }
    
    @Override
    public String toString() {
        if (isStackOverflow()) {
            return String.format("%s | n=%,d | %s | Tiempo: N/A (Stack Overflow) | Altura: %d", 
                algorithm, size, dataType, height);
        }
        return String.format("%s | n=%,d | %s | Tiempo: %.4f ms | Altura: %d", 
            algorithm, size, dataType, getAverageTimeMs(), height);
    }
}
