package src;

public class Ordenador {

    private static long comparaciones = 0;
    private static long intercambios = 0;

    public static long getComparaciones() { return comparaciones; }
    public static long getIntercambios()  { return intercambios; }
    public static void reiniciarContadores() { comparaciones = 0; intercambios = 0; }

    // =========================================================
    //  UTILIDADES
    // =========================================================

    private static void intercambiar(LecturaSensor[] datos, int i, int j) {
        LecturaSensor temporal = datos[i];
        datos[i] = datos[j];
        datos[j] = temporal;
        intercambios++;
    }

    /** Compara dos lecturas por su timestamp. */
    private static int comparar(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return a.getTimestamp().compareTo(b.getTimestamp());
    }

    /** Compara dos lecturas por su valor de PM2.5. */
    private static int compararPorPm25(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return Double.compare(a.getPm25(), b.getPm25());
    }

    // =========================================================
    //  2.7  ALGORITMOS SIMPLES
    // =========================================================

    /**
     * TODO 1: Ordenamiento burbuja con bandera de corte temprano.
     */
    public static void burbuja(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(datos[j], datos[j + 1]) > 0) {
                    intercambiar(datos, j, j + 1);
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) {
                break;
            }
        }
    }

    /**
     * Ordenamiento por selección.
     */
    public static void seleccion(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < n; j++) {
                if (comparar(datos[j], datos[menor]) < 0) {
                    menor = j;
                }
            }
            if (menor != i) {
                intercambiar(datos, i, menor);
            }
        }
    }

    /**
     * Ordenamiento por inserción.
     */
    public static void insercion(LecturaSensor[] datos) {
        reiniciarContadores();
        for (int i = 1; i < datos.length; i++) {
            LecturaSensor actual = datos[i];
            int j = i - 1;
            while (j >= 0 && comparar(datos[j], actual) > 0) {
                datos[j + 1] = datos[j];
                j--;
            }
            datos[j + 1] = actual;
            intercambios++;
        }
    }

    // =========================================================
    //  2.8  ALGORITMOS AVANZADOS
    // =========================================================

    /**
     * MergeSort.
     */
    public static void mergeSort(LecturaSensor[] datos) {
        reiniciarContadores();
        LecturaSensor[] auxiliar = new LecturaSensor[datos.length];
        mergeSortRecursivo(datos, auxiliar, 0, datos.length - 1);
    }

    private static void mergeSortRecursivo(LecturaSensor[] datos, LecturaSensor[] aux,
                                           int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = inicio + (fin - inicio) / 2;
        mergeSortRecursivo(datos, aux, inicio, medio);
        mergeSortRecursivo(datos, aux, medio + 1, fin);
        fusionar(datos, aux, inicio, medio, fin);
    }

    private static void fusionar(LecturaSensor[] datos, LecturaSensor[] aux,
                                 int inicio, int medio, int fin) {
        for (int i = inicio; i <= fin; i++) aux[i] = datos[i];
        int izq = inicio, der = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (izq > medio) {
                datos[k] = aux[der++];
            } else if (der > fin) {
                datos[k] = aux[izq++];
            } else if (comparar(aux[der], aux[izq]) < 0) {
                datos[k] = aux[der++];
            } else {
                datos[k] = aux[izq++];
            }
            intercambios++;
        }
    }

    /**
     * TODO 2: QuickSort optimizado usando MEDIANA DE TRES para el pivote.
     */
    public static void quickSortPivotePrimero(LecturaSensor[] datos) {
        reiniciarContadores();
        quickRecursivo(datos, 0, datos.length - 1);
    }

    private static void quickRecursivo(LecturaSensor[] datos, int inicio, int fin) {
        if (inicio >= fin) return;
        int posicionPivote = particionar(datos, inicio, fin);
        quickRecursivo(datos, inicio, posicionPivote - 1);
        quickRecursivo(datos, posicionPivote + 1, fin);
    }

    private static int particionar(LecturaSensor[] datos, int inicio, int fin) {
        // Mediana de tres: inicio, medio, fin
        int medio = inicio + (fin - inicio) / 2;
        
        if (comparar(datos[medio], datos[inicio]) < 0) {
            intercambiar(datos, inicio, medio);
        }
        if (comparar(datos[fin], datos[inicio]) < 0) {
            intercambiar(datos, inicio, fin);
        }
        if (comparar(datos[medio], datos[fin]) < 0) {
            intercambiar(datos, medio, fin);
        }

        LecturaSensor pivote = datos[inicio];
        int limite = inicio;
        for (int i = inicio + 1; i <= fin; i++) {
            if (comparar(datos[i], pivote) < 0) {
                limite++;
                intercambiar(datos, limite, i);
            }
        }
        intercambiar(datos, inicio, limite);
        return limite;
    }

    /**
     * HeapSort.
     */
    public static void heapSort(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = n / 2 - 1; i >= 0; i--) hundir(datos, n, i);
        for (int i = n - 1; i > 0; i--) {
            intercambiar(datos, 0, i);
            hundir(datos, i, 0);
        }
    }

    private static void hundir(LecturaSensor[] datos, int tamano, int raiz) {
        int mayor = raiz;
        int izq = 2 * raiz + 1;
        int der = 2 * raiz + 2;
        if (izq < tamano && comparar(datos[izq], datos[mayor]) > 0) mayor = izq;
        if (der < tamano && comparar(datos[der], datos[mayor]) > 0) mayor = der;
        if (mayor != raiz) {
            intercambiar(datos, raiz, mayor);
            hundir(datos, tamano, mayor);
        }
    }

    // =========================================================
    //  ORDENAR POR OTRO CRITERIO
    // =========================================================

    /**
     * TODO 3: Ordena por PM2.5 optimizado con MergeSort O(n log n).
     */
    public static void ordenarPorPm25(LecturaSensor[] datos) {
        reiniciarContadores();
        LecturaSensor[] auxiliar = new LecturaSensor[datos.length];
        mergeSortPm25Recursivo(datos, auxiliar, 0, datos.length - 1);
    }

    private static void mergeSortPm25Recursivo(LecturaSensor[] datos, LecturaSensor[] aux, int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = inicio + (fin - inicio) / 2;
        mergeSortPm25Recursivo(datos, aux, inicio, medio);
        mergeSortPm25Recursivo(datos, aux, medio + 1, fin);
        fusionarPm25(datos, aux, inicio, medio, fin);
    }

    private static void fusionarPm25(LecturaSensor[] datos, LecturaSensor[] aux, int inicio, int medio, int fin) {
        for (int i = inicio; i <= fin; i++) aux[i] = datos[i];
        int izq = inicio, der = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (izq > medio) {
                datos[k] = aux[der++];
            } else if (der > fin) {
                datos[k] = aux[izq++];
            } else if (compararPorPm25(aux[der], aux[izq]) < 0) {
                datos[k] = aux[der++];
            } else {
                datos[k] = aux[izq++];
            }
            intercambios++;
        }
    }

    /** Verifica si un arreglo está ordenado por timestamp. */
    public static boolean estaOrdenadoPorTimestamp(LecturaSensor[] datos) {
        for (int i = 1; i < datos.length; i++) {
            if (datos[i - 1].getTimestamp().compareTo(datos[i].getTimestamp()) > 0) {
                return false;
            }
        }
        return true;
    }
}