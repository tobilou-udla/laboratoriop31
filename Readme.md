
# Guía de Laboratorio: Las Olimpiadas de la Ordenación (Bubble, Merge y Quick Sort)

## 1. INTRODUCCIÓN: "LAS OLIMPIADAS DE LA EFICIENCIA"

Idearemos un concurso en el que no buscamos solamente la funcionalidad; exigimos viabilidad termodinámica del software y excelencia asintótica. Como futuros ingenieros, su responsabilidad trasciende la escritura de líneas de código; deben dominar los paradigmas de división y conquista para garantizar la sostenibilidad de las infraestructuras críticas del país.

Su misión es clara: adjudicarse la **Medalla de la Eficiencia Energética (RDA3)**. Para ello, deberán optimizar los algoritmos de ordenación que sustentan los grandes servidores de datos, reduciendo drásticamente el consumo de recursos. Recuerden que la degradación asintótica no es solo un error técnico, sino una falta de ética profesional frente a la crisis climática global.

---

## 2. OBJETIVOS DE APRENDIZAJE

- **RDA2 (Técnico):** Implementar, medir y contrastar la eficiencia de los algoritmos Bubble, Merge y Quick Sort, vinculando empíricamente los tiempos de ejecución con la notación Big O.
- **RDA3 (Ético y Sostenible):** Cuantificar el impacto ambiental de la complejidad algorítmica, traduciendo ciclos de CPU ahorrados en reducción de huella de carbono (CO₂).

---

## 3. FUNDAMENTACIÓN TEÓRICA Y RIGOR MATEMÁTICO

La ordenación es el pilar de la eficiencia en la recuperación de información. Como postulan Luis Joyanes y Luis Aguas, *"la falta de ordenación en una estructura hace injustificable una arquitectura compleja, prefiriéndose una lista simple"*.

En este laboratorio, establecemos que la utilidad de los **Árboles Binarios de Búsqueda (ABB)** reside precisamente en mejorar la complejidad de las búsquedas de **O(n) a O(h)**.

### La Conexión con el Modelo O(h)

En esta práctica, deben comprender que el proceso de partición de **Quick Sort** emula la construcción de un ABB. La eficiencia depende de la altura (h) del proceso recursivo:

- **Algoritmos Cuadráticos (O(n²))**: Como Bubble Sort, donde cada elemento se compara con todos los demás de forma redundante.
- **Algoritmos Logarítmicos (O(n log n))**: Merge y Quick Sort dividen sistemáticamente el espacio de búsqueda.

Si el árbol de recursión está equilibrado, la altura **h** es mínima, garantizando la eficiencia óptima.

---

## 4. INSTRUCCIONES DE IMPLEMENTACIÓN TÉCNICA

Deberán adaptar la arquitectura de interfaces de la asignatura para incluir una métrica de **"altura recursiva"**, permitiendo verificar la degradación del algoritmo.

---

## 5. METODOLOGÍA DEL EXPERIMENTO PRÁCTICO

Para obtener resultados con rigor científico, no basta con una ejecución única. Se requiere eliminar el ruido del sistema operativo:

- **Protocolo:** Ejecutar cada algoritmo **10 veces** para cada tamaño de `n` y calcular la media aritmética.
- **Entorno:** IntelliJ IDEA utilizando `System.nanoTime()`.
- **Estados de Datos:**
  - (A) Aleatorios  
  - (B) Ya ordenados  
  - (C) Orden inverso  

### Tabla de resultados

| Algoritmo               | n=10,000 | n=100,000 | n=1,000,000 | Altura (h) final |
|------------------------|----------|----------|-------------|------------------|
| **Bubble Sort**        |          |          |             | N/A              |
| **Merge Sort**         |          |          |             |                  |
| **Quick Sort (Random)**|          |          |             |                  |
| **Quick Sort (Ordenado)** |       |          |             |                  |

---

## 6. EL DESAFÍO DEL PIVOTE: DEGENERACIÓN HACIA LA LISTA

Un ingeniero senior debe prever la degeneración estructural. Un ABB puede degenerar en una lista si no hay equilibrio.

**Tarea:**  
Ejecutar Quick Sort con el primer elemento como pivote sobre un arreglo **ya ordenado**.

**Observación:**  
Verificar cómo la altura **h** tiende a **n**.  

Conectar este fenómeno con el dato:
> En el peor de los casos, cuando el "árbol" de recursión es unilateral, la eficiencia se desploma de **O(n log n)** a **O(n²)**.

---

## 7. COMPUTACIÓN VERDE: ANÁLISIS DE IMPACTO AMBIENTAL

**Escenario Técnico:**  
Un centro de datos procesa \(10^9\) operaciones de ordenación.

- **Consumo CPU:** 65W (0.065 kW)  
- **Factor de emisión:** 0.5 g de CO₂ por cada kJ de energía consumida  
- \(1 \, kJ = 1 \, kW \times 1 \, s\)

**Cálculo de Reflexión:**  
Utilizando sus tiempos de ejecución, estime cuántos **kilogramos de CO₂** se ahorrarían en un año si un servidor nacional migra de Bubble Sort a un Quick Sort optimizado.

**Pregunta ética:**  
¿Es ético ignorar la complejidad algorítmica sabiendo que h puede reducirse de **n a log₂(n)**?

---

## 8. RÚBRICA DE EVALUACIÓN

| Criterio            | Excelencia (100%)                                           | Insuficiente (0%)                          | Peso |
|--------------------|-------------------------------------------------------------|--------------------------------------------|------|
| **Precisión Técnica** | Implementación robusta con seguimiento de altura (h).    | Errores de lógica o falta de recursión.    | 40%  |
| **Rigor Experimental** | Tabulación basada en medias de 10 ejecuciones y 3 estados. | Datos inventados o una sola ejecución.   | 30%  |
| **Análisis Ético**   | Cálculo cuantitativo de CO₂ con datos reales.             | Reflexión superficial sin base matemática. | 30%  |

---

## 9. REFERENCIAS BIBLIOGRÁFICAS

- Aguas, L., & Joyanes, L. (2021). *Estructuras de Datos y Algoritmos en Java*.  
- Universidad Carlos III de Madrid. (2024). *Materiales de Estructura de Datos y Algoritmos (EDA)*. Tema 5.  
- Joyanes Aguilar, L. *Fundamentos de Programación*. McGraw-Hill.
``
