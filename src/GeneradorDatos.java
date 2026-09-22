package src;

import java.util.Random;

/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   GeneradorDatos - SEMANA 3
   ============================================================ */

public class GeneradorDatos {

    private static final int NUM_ESTACIONES = 9;
    private static final long SEMILLA = 20262L;

    public static LecturaSensor[] generar(int n) {
        Random azar = new Random(SEMILLA);
        LecturaSensor[] datos = new LecturaSensor[n];

        for (int i = 0; i < n; i++) {
            String id = String.format("EST-%03d", (i % NUM_ESTACIONES) + 1);
            String timestamp = String.format("%010d", i);

            double temperatura = 11 + azar.nextDouble() * 18;
            double humedad = 55 + azar.nextDouble() * 35;
            double pm25 = 5 + azar.nextDouble() * 55;

            datos[i] = new LecturaSensor(id, timestamp,
                                         redondear(temperatura),
                                         redondear(humedad),
                                         redondear(pm25));
        }
        return datos;
    }

    private static double redondear(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }

    public static String timestampEnPosicion(int posicion) {
        return String.format("%010d", posicion);
    }

    public static String timestampInexistente() {
        return "9999999999";
    }
}