# Guía de Laboratorio: Las Olimpiadas de la Ordenación

## Resultados del Benchmark

**Protocolo:** 10 ejecuciones por configuración, media aritmética
**Entorno:** Java con System.nanoTime()

### Tabla de Resultados (Tiempo promedio en ms)

| Algoritmo | n=10,000 | n=50,000 | n=100,000 | Altura (h) |
|-----------|----------|----------|-----------|------------|
| Bubble Sort (Aleatorio) | 186,64 | 3911,78 | 15453,50 | 0
| Bubble Sort (Ordenado) | 0,08 | 0,02 | 0,22 | 0
| Bubble Sort (Inverso) | 67,70 | 1490,67 | 6057,32 | 0
| Merge Sort (Aleatorio) | 3,04 | 6,46 | 13,27 | 16
| Merge Sort (Ordenado) | 1,89 | 3,02 | 8,23 | 16
| Merge Sort (Inverso) | 1,03 | 3,48 | 8,51 | 16
| Quick Sort (Random) (Aleatorio) | 2,44 | 3,44 | 7,77 | 35
| Quick Sort (Random) (Ordenado) | 1,81 | 1,58 | 3,74 | 37
| Quick Sort (Random) (Inverso) | 1,24 | 1,70 | 3,82 | 38
| Quick Sort (First) (Aleatorio) | 2,33 | 3,11 | 7,37 | 38
| Quick Sort (First) (Ordenado) | **STACK OVERFLOW** | **STACK OVERFLOW** | **STACK OVERFLOW** | 100000 (O(n))
| Quick Sort (First) (Inverso) | 25,39 | **STACK OVERFLOW** | **STACK OVERFLOW** | 100000 (O(n))

---

# Análisis Detallado de Resultados

## 1. Verificación Empírica de Big O

### Bubble Sort - Complejidad Teórica: O(n²)

| Tamaño | Tiempo (ms) | Ratio observado | Ratio esperado |
|--------|-------------|-----------------|----------------|
| 10,000 | 186,64 | - | - |
| 50,000 | 3911,78 | 20,96x | 25,00x |
| 100,000 | 15453,50 | 3,95x | 4,00x |

**Conclusión:** El ratio observado (3,95x) se acerca al esperado (4,00x) para O(n^2,00).

### Merge Sort - Complejidad Teórica: O(n log n)

| Tamaño | Tiempo (ms) | Ratio observado | Ratio esperado |
|--------|-------------|-----------------|----------------|
| 10,000 | 3,04 | - | - |
| 50,000 | 6,46 | 2,13x | 12,72x |
| 100,000 | 13,27 | 2,05x | 2,99x |

**Conclusión:** El ratio observado (2,05x) se acerca al esperado (2,99x) para O(n^1,58).

### Quick Sort (Random) - Complejidad Teórica: O(n log n)

| Tamaño | Tiempo (ms) | Ratio observado | Ratio esperado |
|--------|-------------|-----------------|----------------|
| 10,000 | 2,44 | - | - |
| 50,000 | 3,44 | 1,41x | 12,72x |
| 100,000 | 7,77 | 2,26x | 2,99x |

**Conclusión:** El ratio observado (2,26x) se acerca al esperado (2,99x) para O(n^1,58).


## 2. Speedup Comparativo

### Speedup vs Bubble Sort (n=100,000, Aleatorio)

| Algoritmo | Tiempo (ms) | Speedup |
|-----------|-------------|---------|
| Bubble Sort | 15453,50 | 1x (base) |
| Merge Sort | 13,27 | **1164,9x** |
| Quick Sort (Random) | 7,77 | **1988,0x** |

Interpretación: Quick Sort es 1988 veces más rápido que Bubble Sort para n=100,000.


## 3. Análisis de Altura Recursiva (h)

### Altura Recursiva (h) para n=100,000

| Algoritmo | Altura (h) | log₂(n) | h / log₂(n) |
|-----------|------------|---------|-------------|
| Merge Sort | 16 | 16,61 | 0,96x |
| Quick Sort (Random) | 35 | 16,61 | 2,11x |

**Interpretación:** La altura h ≈ log₂(n) confirma que el árbol de recursión está equilibrado.


## 4. Degeneración del Quick Sort (First)

### Comparación: Quick Sort (First) - Datos Ordenados vs Aleatorios

- **Datos Ordenados:** STACK OVERFLOW (h = n = 100,000)
- **Datos Aleatorios:** Tiempo = 7,37 ms, h = 38

### Conclusión

Cuando el arreglo está **ordenado** y se usa el **primer elemento como pivote**:
- La altura de recursión h tiende a **n** (100,000)
- El algoritmo degenera de O(n log n) a **O(n²)**
- Esto causa **Stack Overflow** por recursión infinita

**Conexión con ABB:** Un Árbol Binario de Búsqueda degenera en una lista enlazada
cuando los elementos se insertan en orden, perdiendo la ventaja de búsqueda O(log n).


---

# Análisis de Impacto Ambiental (CO₂)

## 1. Datos del Escenario

| Parámetro | Valor |
|-----------|-------|
| Operaciones diarias | 10^9 (1,000,000,000) |
| Consumo CPU | 65W (0.065 kW) |
| Factor de emisión | 0.5 g CO₂ por kJ |
| Equivalencia | 1 kJ = 1 kW × 1 s |

## 2. Fórmulas de Validación

```
Energía (kJ) = Potencia (kW) × Tiempo (s)
Energía (kJ) = 0.065 kW × T(s)
CO₂ (g) = Energía (kJ) × 0.5 g/kJ
Ahorro Anual (kg) = (CO₂_Bubble - CO₂_Optimo) × 10^9 / 1000
```

## 3. Cálculo por Algoritmo

| Algoritmo | Tiempo (ms) | Tiempo (s) | Energía (kJ) | CO₂ (g) | Ahorro (kg/año) |
|-----------|------------|-----------|-------------|--------|----------------|
| Bubble Sort (O(n²)) | 15453,50 | 15,453501 | 1,004478 | 0,502239 | 0,00 |
| Quick Sort Random (O(n log n)) | 7,77 | 0,007773 | 0,000505 | 0,000253 | 501986,16 |
| Merge Sort (O(n log n)) | 13,27 | 0,013266 | 0,000862 | 0,000431 | 501807,64 |

## 4. Validación Paso a Paso (Quick Sort Random)

```
Tiempo Quick Sort (Random) = 0,007773 s
Energía = 0.065 kW × 0,007773 s = 0,000505 kJ
CO₂ = 0,000505 kJ × 0.5 g/kJ = 0,000253 g
Diferencia vs Bubble = 0,502239 - 0,000253 = 0,501986 g/op
Ahorro anual = 0,501986 g × 10^9 / 1000 = 501986,16 kg
```


## 5. Validación Paso a Paso (Merge Sort)

```
Tiempo Merge Sort = 0,013266 s
Energía = 0.065 kW × 0,013266 s = 0,000862 kJ
CO₂ = 0,000862 kJ × 0.5 g/kJ = 0,000431 g
Diferencia vs Bubble = 0,502239 - 0,000431 = 0,501808 g/op
Ahorro anual = 0,501808 g × 10^9 / 1000 = 501807,64 kg
```

## 6. Proyecciones Temporales

| Período | Ahorro Quick Sort | Ahorro Merge Sort |
|---------|-------------------|-------------------|
| Por hora | 57,30 kg | 57,28 kg |
| Por día | 1375,30 kg | 1374,82 kg |
| Por mes | 41832,18 kg | 41817,30 kg |
| Por año | **501986,16 kg** | **501807,64 kg** |
| En 10 años | 5019,86 toneladas | 5018,08 toneladas |

## 7. Equivalentes del Mundo Real

| Equivalente | Cantidad |
|-------------|----------|
| Árboles absorbiendo (10 años) | 230586 árboles |
| Km recorridos por un auto | 4.183.218 km |
| Hogares (electricidad anual) | 185,9 hogares |
| Smartphones cargados | 60.480 cargas |

## 8. Análisis de Sensibilidad

¿Qué sucede bajo distintos factores de emisión?

| Factor (g CO₂/kJ) | Ahorro Quick Sort (kg/año) | Ahorro Merge Sort (kg/año) |
|--------------------|----------------------------|---------------------------|
| 0,3 | 301191,69 | 301084,58 |
| 0,5 | 501986,16 | 501807,64 |
| 0,7 | 702780,62 | 702530,69 |
| 0,9 | 903575,08 | 903253,75 |

> Aún con el factor más bajo (0.3 g/kJ), el ahorro con Quick Sort es **301191,69 kg/año**.

## 9. Reflexión Ética

**Pregunta:** ¿Es ético ignorar la complejidad algorítmica sabiendo que h puede reducirse de n a log₂(n)?

**Respuesta fundamentada:**

No es ético ignorar la complejidad algorítmica por tres razones:

1. **Responsabilidad profesional:** Como ingenieros, nuestro código impacta infraestructuras críticas. Elegir O(n²) sobre O(n log n) cuando existen alternativas es negligencia técnica.

2. **Impacto ambiental cuantificable:** Los datos muestran que migrar de Bubble Sort a Quick Sort ahorra **501986,16 kg de CO₂ al año** en un solo centro de datos. Esto equivale a **230586 árboles** absorbiendo CO₂ durante 10 años, o **4.183.218 km** no recorridos por un automóvil.

3. **Dimensión social:** La ineficiencia algorítmica incrementa el consumo energético, encarece servicios digitales y contribuye al calentamiento global, afectando desproporcionadamente a comunidades vulnerables.

**Conclusión:** La diferencia entre h=n y h=log₂(n) no es solo una cuestión de rendimiento, sino una decisión con consecuencias ambientales de **501986,16 kg de CO₂ anuales**. Optimizar es un imperativo ético.
---

## Observación: Degeneración del Quick Sort

**Fenómeno:** Cuando Quick Sort (First) se ejecuta sobre un arreglo **ya ordenado**,
la altura de recursión tiende a **n**, causando **Stack Overflow**.

**Explicación:** Con el primer elemento como pivote en un arreglo ordenado,
cada partición reduce el arreglo solo en 1 elemento, generando una recursión de profundidad n.

**Conexión con ABB:** Esto demuestra cómo un ABB degenera en una lista enlazada
cuando los elementos se insertan en orden, pasando de O(n log n) a O(n²).
