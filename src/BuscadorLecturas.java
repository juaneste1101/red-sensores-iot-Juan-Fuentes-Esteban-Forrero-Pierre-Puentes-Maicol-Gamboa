package src;

/**
 * PLATAFORMA DE MONITOREO AMBIENTAL URBANO
 * BuscadorLecturas - SEMANA 3
 */
public class BuscadorLecturas {

    private static int comparaciones = 0;

    public static int getComparaciones() {
        return comparaciones;
    }

    public static int busquedaLinealPorTimestamp(LecturaSensor[] datos, String timestamp) {
        comparaciones = 0;
        for (int i = 0; i < datos.length; i++) {
            comparaciones++;
            if (datos[i].getTimestamp().equals(timestamp)) {
                return i;
            }
        }
        return -1;
    }

    public static int busquedaBinariaPorTimestamp(LecturaSensor[] datos, String timestamp) {
        comparaciones = 0;
        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            comparaciones++;

            int cmp = datos[medio].getTimestamp().compareTo(timestamp);

            if (cmp == 0) {
                return medio;
            } else if (cmp < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    public static int busquedaBinariaPorPm25(LecturaSensor[] datos, double pm25) {
        comparaciones = 0;
        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            comparaciones++;

            if (datos[medio].getPm25() == pm25) {
                return medio;
            } else if (datos[medio].getPm25() < pm25) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    public static int buscarPorEstacion(LecturaSensor[] datos, String idSensor) {
        comparaciones = 0;
        for (int i = 0; i < datos.length; i++) {
            comparaciones++;
            if (datos[i].getIdSensor().equals(idSensor)) {
                return i;
            }
        }
        return -1;
    }
}