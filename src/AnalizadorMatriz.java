package src;

public class AnalizadorMatriz {

    private Double[][] pm25; // Null representa ausencia real de dato
    private String[] estaciones;
    private static final int HORAS = 24;

    public AnalizadorMatriz(String[] estaciones) {
        this.estaciones = estaciones;
        this.pm25 = new Double[estaciones.length][HORAS];
    }

    private int buscarIndiceEstacion(String idEstacion) {
        for (int i = 0; i < estaciones.length; i++) {
            if (estaciones[i].equalsIgnoreCase(idEstacion)) {
                return i;
            }
        }
        return -1;
    }

    public void cargarLectura(LecturaSensor lectura) {
        if (lectura == null) return;
        int idxEstacion = buscarIndiceEstacion(lectura.getIdEstacion());
        if (idxEstacion != -1) {
            int hora = lectura.getHora();
            if (hora >= 0 && hora < HORAS) {
                pm25[idxEstacion][hora] = lectura.getPm25();
            }
        }
    }

    public double promedioDeHora(int hora) {
        if (hora < 0 || hora >= HORAS) return 0.0;
        double suma = 0;
        int conteo = 0;
        for (int e = 0; e < estaciones.length; e++) {
            if (pm25[e][hora] != null) {
                suma += pm25[e][hora];
                conteo++;
            }
        }
        return conteo == 0 ? 0.0 : suma / conteo;
    }

    public double promedioDeEstacion(int idxEstacion) {
        if (idxEstacion < 0 || idxEstacion >= estaciones.length) return 0.0;
        double suma = 0;
        int conteo = 0;
        for (int h = 0; h < HORAS; h++) {
            if (pm25[idxEstacion][h] != null) {
                suma += pm25[idxEstacion][h];
                conteo++;
            }
        }
        return conteo == 0 ? 0.0 : suma / conteo;
    }

    public int horaMasContaminada() {
        int mejorHora = -1;
        double maxPromedio = -1.0;
        for (int h = 0; h < HORAS; h++) {
            double prom = promedioDeHora(h);
            if (prom > maxPromedio) {
                maxPromedio = prom;
                mejorHora = h;
            }
        }
        return mejorHora;
    }
}
