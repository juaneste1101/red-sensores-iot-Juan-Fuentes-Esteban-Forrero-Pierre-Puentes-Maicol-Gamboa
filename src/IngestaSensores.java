package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   IngestaSensores - SEMANA 3 (ÚNICO MAIN DEL PROYECTO)
   ============================================================ */

public class IngestaSensores {

    private static final String ARCHIVO = "data/lecturas_ampliadas.csv";
    private static final int CAMPOS_ESPERADOS = 5;

    private static int descartadasPorFormato = 0;
    private static int descartadasPorRango = 0;

    public static void main(String[] args) throws IOException {

        RepositorioLecturas repositorio = new RepositorioLecturas();
        AnalizadorMatriz analizador = new AnalizadorMatriz();

        cargarArchivo(repositorio, analizador);

        imprimirResumenIngesta(repositorio);
        imprimirPerfilHorario(analizador);

        // Experimentos de la Semana 3
        ejecutarExperimentosSemanaTres();
    }

    private static void ejecutarExperimentosSemanaTres() {
        System.out.println();
        System.out.println("====================================================");
        System.out.println("       SEMANA 3 - BUSQUEDA Y EFICIENCIA");
        System.out.println("====================================================");
        System.out.println();

        BancoDePruebas.experimentoUno();
        BancoDePruebas.experimentoDos();
        BancoDePruebas.experimentoTres();
        BancoDePruebas.experimentoCuatro();
    }

    private static void imprimirResumenIngesta(RepositorioLecturas repositorio) {
        System.out.println();
        System.out.println("=== INGESTA ===");
        System.out.println("Lecturas almacenadas:      " + repositorio.tamano());
        System.out.println("Descartadas por formato:   " + descartadasPorFormato);
        System.out.println("Descartadas por rango:     " + descartadasPorRango);
        System.out.println();
        System.out.println("PM2.5 promedio (repositorio): " + repositorio.promedioPm25());
    }

    private static void imprimirPerfilHorario(AnalizadorMatriz analizador) {
        System.out.println();
        System.out.println("=== PERFIL HORARIO DE LA CIUDAD ===");
        for (int h = 0; h < 24; h++) {
            System.out.printf("Hora %02d -> PM2.5 promedio: %.2f%n", h, analizador.promedioDeHora(h));
        }
    }

    private static void cargarArchivo(RepositorioLecturas repositorio, AnalizadorMatriz analizador) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO))) {
            lector.readLine(); // encabezado
            String linea;

            while ((linea = lector.readLine()) != null) {
                LecturaSensor lectura = construirLectura(linea);

                if (lectura == null) continue;

                if (!lectura.esValida()) {
                    descartadasPorRango++;
                    continue;
                }

                if (!repositorio.agregar(lectura)) {
                    System.err.println("ADVERTENCIA: no se pudo almacenar " + lectura.getIdSensor());
                    continue;
                }

                analizador.registrar(lectura);
            }
        }
    }

    private static LecturaSensor construirLectura(String linea) {
        String[] campos = linea.split(",");

        if (campos.length != CAMPOS_ESPERADOS) {
            descartadasPorFormato++;
            return null;
        }

        try {
            double temperatura = Double.parseDouble(campos[2].trim());
            double humedad = Double.parseDouble(campos[3].trim());
            double pm25 = Double.parseDouble(campos[4].trim());

            return new LecturaSensor(campos[0].trim(), campos[1].trim(), temperatura, humedad, pm25);
        } catch (NumberFormatException e) {
            descartadasPorFormato++;
            return null;
        }
    }
}