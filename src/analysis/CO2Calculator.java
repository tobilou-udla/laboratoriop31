package analysis;

public class CO2Calculator {
    
    private static final double CPU_POWER_WATTS = 65.0;
    private static final double EMISSION_FACTOR_GRAM_KJ = 0.5;
    private static final long TOTAL_OPERATIONS = 1_000_000_000L;
    
    public static double calculateEnergyConsumption(double timeSeconds) {
        double energyKJ = CPU_POWER_WATTS * timeSeconds / 1000.0;
        return energyKJ;
    }
    
    public static double calculateCO2Grams(double energyKJ) {
        return energyKJ * EMISSION_FACTOR_GRAM_KJ;
    }
    
    public static double calculateAnnualCO2Savings(double bubbleSortTimeMs, double quickSortTimeMs) {
        double bubbleSortTimeS = bubbleSortTimeMs / 1000.0;
        double quickSortTimeS = quickSortTimeMs / 1000.0;
        
        double bubbleEnergy = calculateEnergyConsumption(bubbleSortTimeS);
        double quickEnergy = calculateEnergyConsumption(quickSortTimeS);
        
        double bubbleCO2 = calculateCO2Grams(bubbleEnergy);
        double quickCO2 = calculateCO2Grams(quickEnergy);
        
        double savingsPerOperation = bubbleCO2 - quickCO2;
        double annualSavingsGrams = savingsPerOperation * TOTAL_OPERATIONS;
        
        return annualSavingsGrams / 1000.0;
    }
    
    public static String generateCO2Report(double bubbleSortTimeMs, double quickSortRandomTimeMs, 
                                          double mergeSortTimeMs) {
        StringBuilder report = new StringBuilder();
        
        double savingsQuickRandom = calculateAnnualCO2Savings(bubbleSortTimeMs, quickSortRandomTimeMs);
        double savingsMerge = calculateAnnualCO2Savings(bubbleSortTimeMs, mergeSortTimeMs);
        
        report.append("# Análisis de Impacto Ambiental (CO₂)\n\n");
        
        report.append("## 1. Datos del Escenario\n\n");
        report.append("| Parámetro | Valor |\n");
        report.append("|-----------|-------|\n");
        report.append("| Operaciones diarias | 10^9 (1,000,000,000) |\n");
        report.append("| Consumo CPU | 65W (0.065 kW) |\n");
        report.append("| Factor de emisión | 0.5 g CO₂ por kJ |\n");
        report.append("| Equivalencia | 1 kJ = 1 kW × 1 s |\n\n");
        
        report.append("## 2. Fórmulas de Validación\n\n");
        report.append("```\n");
        report.append("Energía (kJ) = Potencia (kW) × Tiempo (s)\n");
        report.append("Energía (kJ) = 0.065 kW × T(s)\n");
        report.append("CO₂ (g) = Energía (kJ) × 0.5 g/kJ\n");
        report.append("Ahorro Anual (kg) = (CO₂_Bubble - CO₂_Optimo) × 10^9 / 1000\n");
        report.append("```\n\n");
        
        report.append("## 3. Cálculo por Algoritmo\n\n");
        
        double[] times = {bubbleSortTimeMs, quickSortRandomTimeMs, mergeSortTimeMs};
        String[] algorithms = {"Bubble Sort (O(n²))", "Quick Sort Random (O(n log n))", "Merge Sort (O(n log n))"};
        
        report.append("| Algoritmo | Tiempo (ms) | Tiempo (s) | Energía (kJ) | CO₂ (g) | Ahorro (kg/año) |\n");
        report.append("|-----------|------------|-----------|-------------|--------|----------------|\n");
        
        for (int i = 0; i < algorithms.length; i++) {
            double timeS = times[i] / 1000.0;
            double energy = calculateEnergyConsumption(timeS);
            double co2 = calculateCO2Grams(energy);
            double annualSavings = calculateAnnualCO2Savings(bubbleSortTimeMs, times[i]);
            
            report.append(String.format("| %s | %.2f | %.6f | %.6f | %.6f | %.2f |\n",
                algorithms[i], times[i], timeS, energy, co2, annualSavings));
        }
        
        report.append("\n## 4. Validación Paso a Paso (Quick Sort Random)\n\n");
        validateAndReport(report, bubbleSortTimeMs, quickSortRandomTimeMs, "Quick Sort (Random)");
        report.append("\n## 5. Validación Paso a Paso (Merge Sort)\n\n");
        validateAndReport(report, bubbleSortTimeMs, mergeSortTimeMs, "Merge Sort");
        
        report.append("## 6. Proyecciones Temporales\n\n");
        report.append("| Período | Ahorro Quick Sort | Ahorro Merge Sort |\n");
        report.append("|---------|-------------------|-------------------|\n");
        report.append(String.format("| Por hora | %.2f kg | %.2f kg |\n", 
            savingsQuickRandom / (365 * 24), savingsMerge / (365 * 24)));
        report.append(String.format("| Por día | %.2f kg | %.2f kg |\n", 
            savingsQuickRandom / 365, savingsMerge / 365));
        report.append(String.format("| Por mes | %.2f kg | %.2f kg |\n", 
            savingsQuickRandom / 12, savingsMerge / 12));
        report.append(String.format("| Por año | **%.2f kg** | **%.2f kg** |\n", 
            savingsQuickRandom, savingsMerge));
        report.append(String.format("| En 10 años | %.2f toneladas | %.2f toneladas |\n\n",
            savingsQuickRandom * 10 / 1000, savingsMerge * 10 / 1000));
        
        report.append("## 7. Equivalentes del Mundo Real\n\n");
        report.append(realWorldEquivalents(savingsQuickRandom));
        
        report.append("## 8. Análisis de Sensibilidad\n\n");
        report.append("¿Qué sucede bajo distintos factores de emisión?\n\n");
        report.append("| Factor (g CO₂/kJ) | Ahorro Quick Sort (kg/año) | Ahorro Merge Sort (kg/año) |\n");
        report.append("|--------------------|----------------------------|---------------------------|\n");
        
        double[] emissionFactors = {0.3, 0.5, 0.7, 0.9};
        for (double ef : emissionFactors) {
            double qsSavings = calculateSavingsWithFactor(bubbleSortTimeMs, quickSortRandomTimeMs, ef);
            double msSavings = calculateSavingsWithFactor(bubbleSortTimeMs, mergeSortTimeMs, ef);
            report.append(String.format("| %.1f | %.2f | %.2f |\n", ef, qsSavings, msSavings));
        }
        
        report.append(String.format("\n> Aún con el factor más bajo (0.3 g/kJ), el ahorro con Quick Sort es **%.2f kg/año**.\n\n",
            calculateSavingsWithFactor(bubbleSortTimeMs, quickSortRandomTimeMs, 0.3)));
        
        report.append("## 9. Reflexión Ética\n\n");
        report.append("**Pregunta:** ¿Es ético ignorar la complejidad algorítmica sabiendo que h puede reducirse de n a log₂(n)?\n\n");
        report.append("**Respuesta fundamentada:**\n\n");
        report.append("No es ético ignorar la complejidad algorítmica por tres razones:\n\n");
        report.append("1. **Responsabilidad profesional:** Como ingenieros, nuestro código impacta infraestructuras ");
        report.append("críticas. Elegir O(n²) sobre O(n log n) cuando existen alternativas es negligencia técnica.\n\n");
        report.append("2. **Impacto ambiental cuantificable:** Los datos muestran que migrar de Bubble Sort a Quick Sort ");
        report.append(String.format("ahorra **%.2f kg de CO₂ al año** en un solo centro de datos. ", savingsQuickRandom));
        report.append(String.format("Esto equivale a **%.0f árboles** absorbiendo CO₂ durante 10 años, o **%,.0f km** ", 
            treesPerYear(savingsQuickRandom), carKmPerYear(savingsQuickRandom)));
        report.append("no recorridos por un automóvil.\n\n");
        report.append("3. **Dimensión social:** La ineficiencia algorítmica incrementa el consumo energético, ");
        report.append("encarece servicios digitales y contribuye al calentamiento global, afectando ");
        report.append("desproporcionadamente a comunidades vulnerables.\n\n");
        report.append(String.format("**Conclusión:** La diferencia entre h=n y h=log₂(n) no es solo una cuestión de rendimiento, "));
        report.append(String.format("sino una decisión con consecuencias ambientales de **%.2f kg de CO₂ anuales**. ", savingsQuickRandom));
        report.append("Optimizar es un imperativo ético.");
        
        return report.toString();
    }
    
    private static void validateAndReport(StringBuilder report, double bubbleMs, double algoMs, String name) {
        double timeS = algoMs / 1000.0;
        double energy = calculateEnergyConsumption(timeS);
        double co2PerOp = calculateCO2Grams(energy);
        
        double bubbleTimeS = bubbleMs / 1000.0;
        double bubbleEnergy = calculateEnergyConsumption(bubbleTimeS);
        double bubbleCO2 = calculateCO2Grams(bubbleEnergy);
        
        double savingsPerOp = bubbleCO2 - co2PerOp;
        double annualKg = savingsPerOp * TOTAL_OPERATIONS / 1000.0;
        
        report.append(String.format("```\n"));
        report.append(String.format("Tiempo %s = %.6f s\n", name, timeS));
        report.append(String.format("Energía = 0.065 kW × %.6f s = %.6f kJ\n", timeS, energy));
        report.append(String.format("CO₂ = %.6f kJ × 0.5 g/kJ = %.6f g\n", energy, co2PerOp));
        report.append(String.format("Diferencia vs Bubble = %.6f - %.6f = %.6f g/op\n", bubbleCO2, co2PerOp, savingsPerOp));
        report.append(String.format("Ahorro anual = %.6f g × 10^9 / 1000 = %.2f kg\n", savingsPerOp, annualKg));
        report.append("```\n\n");
    }
    
    private static double calculateSavingsWithFactor(double bubbleMs, double algoMs, double factor) {
        double bubbleS = bubbleMs / 1000.0;
        double algoS = algoMs / 1000.0;
        double bubbleEnergy = calculateEnergyConsumption(bubbleS);
        double algoEnergy = calculateEnergyConsumption(algoS);
        double diffCO2 = (bubbleEnergy - algoEnergy) * factor;
        return diffCO2 * TOTAL_OPERATIONS / 1000.0;
    }
    
    private static String realWorldEquivalents(double savingsKg) {
        double trees = treesPerYear(savingsKg);
        double carKm = carKmPerYear(savingsKg);
        double households = savingsKg / 2700.0;
        double smartphones = savingsKg / 8.3;
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| Equivalente | Cantidad |\n"));
        sb.append(String.format("|-------------|----------|\n"));
        sb.append(String.format("| Árboles absorbiendo (10 años) | %.0f árboles |\n", trees));
        sb.append(String.format("| Km recorridos por un auto | %,.0f km |\n", carKm));
        sb.append(String.format("| Hogares (electricidad anual) | %.1f hogares |\n", households));
        sb.append(String.format("| Smartphones cargados | %,.0f cargas |\n\n", smartphones));
        return sb.toString();
    }
    
    private static double treesPerYear(double savingsKg) {
        double tons10yr = savingsKg * 10 / 1000.0;
        return tons10yr * 1000 / 21.77;
    }
    
    private static double carKmPerYear(double savingsKg) {
        return savingsKg / 0.12;
    }
}
