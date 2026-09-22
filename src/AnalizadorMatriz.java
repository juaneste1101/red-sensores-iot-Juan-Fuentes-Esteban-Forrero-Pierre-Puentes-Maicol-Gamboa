package src;

/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   AnalizadorMatriz - SEMANA 3
   ============================================================ */

public class AnalizadorMatriz {

    private static final int NUM_ESTACIONES = 9;
    private static final int NUM_HORAS = 24;

    private double[][] pm25PorEstacionHora;
    private boolean[][] hayDato;

    public AnalizadorMatriz() {
        this.pm25PorEstacionHora = new double[NUM_ESTACIONES][NUM_HORAS];
        this.hayDato = new boolean[NUM_ESTACIONES][NUM_HORAS];
    }

    private int indiceDeEstacion(String idSensor) {
        String numero = idSensor.substring(4);
        return Integer.parseInt(numero) - 1;
    }

    public void registrar(LecturaSensor lectura) {
        int fila = indiceDeEstacion(lectura.getIdSensor());
        int columna = lectura.getHora();
        pm25PorEstacionHora[fila][columna] = lectura.getPm25();
        hayDato[fila][columna] = true;
    }

    public double promedioDeHora(int hora) {
        double suma = 0;
        int estacionesQueReportaron = 0;
        for (int fila = 0; fila < NUM_ESTACIONES; fila++) {
            if (hayDato[fila][hora]) {
                suma += pm25PorEstacionHora[fila][hora];
                estacionesQueReportaron++;
            }
        }
        if (estacionesQueReportaron == 0) return 0;
        return suma / estacionesQueReportaron;
    }

    public double promedioDeEstacion(int fila) {
        if (fila < 0 || fila >= NUM_ESTACIONES) {
            throw new IndexOutOfBoundsException("Estación inválida: " + fila);
        }
        double suma = 0;
        int horasQueReportaron = 0;
        for (int h = 0; h < NUM_HORAS; h++) {
            if (hayDato[fila][h]) {
                suma += pm25PorEstacionHora[fila][h];
                horasQueReportaron++;
            }
        }
        if (horasQueReportaron == 0) return 0;
        return suma / horasQueReportaron;
    }

    public int horaMasContaminada() {
        int mejorHora = -1;
        double mejorPromedio = -1;
        for (int h = 0; h < NUM_HORAS; h++) {
            double promedio = promedioDeHora(h);
            if (promedio > mejorPromedio) {
                mejorPromedio = promedio;
                mejorHora = h;
            }
        }
        return mejorHora;
    }
}