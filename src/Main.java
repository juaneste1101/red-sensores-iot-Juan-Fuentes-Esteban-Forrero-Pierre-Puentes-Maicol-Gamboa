package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE MONITOREO AMBIENTAL (SEMANA 02) ===");
        
        // Cambiamos el nombre del archivo aquí:
        String ruta = "data/lecturas_ampliadas.csv";
        String[] listaEstaciones = {"EST-001", "EST-002", "EST-003", "EST-004", "EST-005", "EST-006", "EST-007", "EST-008", "EST-009"};
        
        RepositorioLecturas repositorio = new RepositorioLecturas(10);
        AnalizadorMatriz analizador = new AnalizadorMatriz(listaEstaciones);

        ProcesadorIngesta procesador = new ProcesadorIngesta();
        procesador.procesarArchivo(ruta, repositorio, analizador);

        System.out.println("\n--- ESTADO DEL REPOSITORIO DINÁMICO (TAD) ---");
        System.out.println("Lecturas almacenadas: " + repositorio.tamano());
        System.out.println("Capacidad final del arreglo: " + repositorio.getCapacidadActual());
        System.out.println("Redimensionamientos realizados: " + repositorio.getRedimensionamientos());
        System.out.println("Copias de elementos realizadas: " + repositorio.getCopiasRealizadas());

        System.out.println("\n--- RESULTADOS ANÁLISIS MATRIZ (SIN CERO FANTASMA) ---");
        System.out.printf("Promedio EST-003 (sin ceros fantasma): %.2f\n", analizador.promedioDeEstacion(2));
        System.out.println("Hora más contaminada de la ciudad: " + analizador.horaMasContaminada() + ":00 hrs");
    }
}