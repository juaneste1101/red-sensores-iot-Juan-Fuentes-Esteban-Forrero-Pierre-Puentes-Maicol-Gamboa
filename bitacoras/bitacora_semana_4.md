## 1. Objetivos de la Semana

* Implementar e instrumentar los algoritmos de ordenamiento simples (Burbuja, Selección, Inserción) y avanzados (MergeSort, HeapSort, QuickSort).
* Evaluar el costo de las comparaciones e intercambios a diferentes escalas de datos (N = 1.000, 10.000, 100.000).
* Identificar y solucionar el fallo de recursión profunda (StackOverflowError) en QuickSort cuando los datos ingresan en orden cronológico.
* Analizar los efectos colaterales de ordenar por variables secundarias (PM2.5) sobre la Búsqueda Binaria por timestamp.

## 2. Experimentos y Resultados Obtenidos

### 2.1 Experimento 1: Algoritmos Simples (10.000 lecturas desordenadas)

* Burbuja: 49.990.814 comparaciones | 24.928.244 intercambios | 6.902 ms
* Selección: 49.995.000 comparaciones | 9.994 intercambios | 4.795 ms
* Inserción: 24.938.233 comparaciones | 9.999 intercambios | 1.635 ms

### 2.2 Experimento 2: Algoritmos Simples con Datos Ordenados

* Burbuja (con corte temprano): 9.999 comparaciones | 0 intercambios | 1 ms
* Selección: 49.995.000 comparaciones | 0 intercambios | 2.729 ms
* Inserción: 9.999 comparaciones | 0 intercambios | 1 ms

### 2.3 Experimento 3: Escalabilidad (Simples vs. Avanzados)

* N = 1.000 desordenados: Inserción (81 ms), MergeSort (2 ms), HeapSort (5 ms).
* N = 10.000 desordenados: Inserción (1.458 ms), MergeSort (20 ms), HeapSort (31 ms).
* N = 100.000 desordenados: Inserción O(n^2) explotó a 2.497.222.762 comparaciones (203.573 ms / ~3.4 min). MergeSort O(n log n) tomó 163 ms y HeapSort 324 ms.

### 2.4 Experimento 4: QuickSort y Estrategia de Pivote

* Caso A (50.000 desordenadas): 1.301.960 comparaciones | 196 ms.
* Caso B (50.000 ordenadas cronológicamente - Pivote Fijo): StackOverflowError por agotamiento de pila.
* Solución Aplicada: Implementación de pivote por Mediana de Tres / Aleatorio, reduciendo el tiempo a menos de 100 ms sin desbordamientos.

### 2.5 Experimento 5: Efecto Colateral y Búsqueda Binaria

* Datos en orden cronológico: Búsqueda Binaria por timestamp encuentra el elemento en la posición 73412 con 16 comparaciones.
* Se reordena el arreglo por PM2.5 para generar el ranking de estaciones.
* Se repite la Búsqueda Binaria por timestamp: retorna -1 (falla) al haberse roto la precondición de ordenamiento cronológico. La búsqueda lineal confirmó que el dato existía en la posición 87705.

## 3. Dificultades y Soluciones

* Dificultad: La prueba con 100.000 lecturas en Inserción tardaba más de 3 minutos en completar.
* Solución: Se analizó que no correspondía a un error de software, sino al costo teórico cuadrático O(n^2) al procesar cerca de 2.500 millones de iteraciones en la JVM, evidenciando la necesidad de algoritmos eficientes para grandes volúmenes.

## 4. Conclusiones de Ingeniería

* No existe un algoritmo "mejor" en términos absolutos. La elección depende del volumen y estructura: Inserción es ideal para conjuntos pequeños, mientras que MergeSort y HeapSort garantizan rendimiento en grandes volúmenes.
* Para mantener la precondición de la Búsqueda Binaria en la plataforma, las operaciones de ranking deben realizarse sobre copias en memoria.