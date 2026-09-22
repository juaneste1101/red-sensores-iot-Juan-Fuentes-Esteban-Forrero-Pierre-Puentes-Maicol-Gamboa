# Decisiones de diseño — Semana 3

## 1. Punto de entrada
El proyecto mantiene un único punto de entrada: `IngestaSensores.main()`. `BancoDePruebas` es una clase auxiliar y no contiene `main`.

## 2. Búsqueda por timestamp
Se utilizan dos estrategias:
- Búsqueda lineal: no requiere ordenamiento.
- Búsqueda binaria: requiere que el arreglo esté ordenado por timestamp. Los datos sintéticos de `GeneradorDatos` se generan en orden cronológico, por lo que se cumple la precondición.

## 3. Búsqueda por PM2.5
No se asume que los datos estén ordenados por PM2.5. La búsqueda binaria por PM2.5 se conserva como experimento para demostrar el efecto de una precondición incumplida (20 aciertos en lineal vs 0 en binaria).

## 4. Comparación de String
Los identificadores de estación y timestamps se comparan mediante `equals()` y `compareTo()`, no mediante `==`, para comparar contenido en memoria.

## 5. Medición
La comparación principal entre algoritmos utiliza el número exacto de comparaciones. El tiempo en milisegundos se conserva como evidencia experimental.