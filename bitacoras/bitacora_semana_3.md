
## 1. Objetivos de la Semana

* Implementar y comparar algoritmos de búsqueda lineal O(n) y búsqueda binaria O(log n) sobre el repositorio de lecturas de la red ambiental.
* Medir la eficiencia algorítmica basada en el número exacto de comparaciones.
* Demostrar experimentalmente el impacto de incumplir las precondiciones de la búsqueda binaria (ordenamiento).
* Adoptar el flujo de trabajo en Git utilizando ramas feature/ y etiquetas de hito (hito-S03).

## 2. Decisiones de Diseño

### 2.1 Punto de Entrada Único

El proyecto mantiene un único punto de entrada: IngestaSensores.main(). BancoDePruebas es una clase auxiliar que contiene los experimentos y no posee método main.

### 2.2 Búsqueda por Timestamp

* Búsqueda Lineal: No requiere ordenamiento previo.
* Búsqueda Binaria: Requiere que el arreglo esté ordenado cronológicamente por timestamp. Los datos sintéticos de GeneradorDatos se generan en orden ascendente, por lo que se cumple esta precondición.

### 2.3 Búsqueda por PM2.5

Los datos no están ordenados por nivel de PM2.5. La búsqueda binaria sobre este campo se conserva intencionalmente como experimento para demostrar qué ocurre cuando se incumple una precondición (20 aciertos en lineal vs 0 en binaria).

### 2.4 Comparación de String

Los identificadores de estación y timestamps se comparan mediante los métodos .equals() y .compareTo(), evitando el uso de == para evaluar el contenido real y no las referencias en memoria.

---

## 3. Traza Manual de Búsqueda Binaria

Antes de corregir la lógica del bucle while (inicio <= fin), se realizó la traza del algoritmo para buscar el valor 3 dentro del arreglo ordenado [0, 1, 2, 3] (tamaño N=4):

| Paso | inicio | fin | medio | datos[medio] | Comparación (compareTo) | Acción realizada |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 0 | 3 | 1 | 1 | 1 < 3 (cmp < 0) | inicio = medio + 1 (= 2) |
| 2 | 2 | 3 | 2 | 2 | 2 < 3 (cmp < 0) | inicio = medio + 1 (= 3) |
| 3 | 3 | 3 | 3 | 3 | 3 == 3 (cmp == 0) | Elemento encontrado en posición 3 |

> Conclusión de la traza: La actualización estricta con +1 y -1 al ajustar los límites inicio y fin garantiza que el espacio de búsqueda se reduzca en cada iteración, evitando ciclos infinitos.

---

## 4. Resultados Experimentales (Banco de Pruebas)

Se ejecutaron los experimentos en la plataforma con la semilla predeterminada (20262L), obteniendo las siguientes mediciones en la consola:

### Experimento 1 y 3: Búsqueda por Timestamp (Peor Caso: último elemento)

| Tamaño (N) | Comp. Lineal | Comp. Binaria | Tiempo Lineal (ms) | Eficiencia Relativa |
| --- | --- | --- | --- | --- |
| 1.000 | 1.000 | 10 | 1,048 ms | 100.0x más eficiente |
| 100.000 | 100.000 | 17 | 9,080 ms | 5.882,4x más eficiente |
| 1.000.000 | 1.000.000 | 20 | 46,575 ms | 50.000,0x más eficiente |

### Caso de Elemento Inexistente (timestamp "9999999999" sobre N=100.000)

* Búsqueda Lineal: Retornó -1 tras 100.000 comparaciones.
* Búsqueda Binaria: Retornó -1 tras 17 comparaciones.

### Experimento 4: Incumplimiento de Precondición (Búsqueda sobre PM2.5)

Se buscaron 20 valores de PM2.5 que efectivamente existen en el arreglo sintético:

* Encontrados por Búsqueda Lineal: 20 / 20
* Encontrados por Búsqueda Binaria: 0 / 20

---

## 5. Conclusiones del Grupo

1. Importancia de las Precondiciones: La búsqueda binaria sólo es efectiva si los datos están previamente ordenados. Al buscar sobre un campo desordenado como PM2.5, el algoritmo falla en hallar datos existentes.
2. Escalabilidad Algorítmica: Reducir la complejidad de O(n) a O(log n) permite pasar de un millón de operaciones a sólo 20 en arreglos de gran tamaño.
3. Mapeo de Referencias en Java: La comparación de cadenas debe realizarse siempre por contenido mediante .equals(), evitando fallos sutiles en búsquedas.

```

```