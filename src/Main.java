package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE MONITOREO AMBIENTAL (SEMANA 02) ===");
        
        String[] listaEstaciones = {"EST-001", "EST-002", "EST-003", "EST-004", "EST-005", "EST-006", "EST-007", "EST-008", "EST-009"};
        
        // 1. Instanciación del repositorio dinámico y analizador
        RepositorioLecturas repositorio = new RepositorioLecturas(10);
        AnalizadorMatriz analizador = new AnalizadorMatriz(listaEstaciones);

        // 2. Ingesta enrutada explícitamente hacia el nuevo dataset en data/
        ProcesadorIngesta procesador = new ProcesadorIngesta("data/lecturas_ampliadas.csv");
        
        // 3. Ejecución del procesamiento
        System.out.println("Procesando dataset desde: " + procesador.getRutaArchivo());
        procesador.procesarArchivo(repositorio, analizador);

        // 4. Salida de resultados
        System.out.println("\n--- ESTADO DEL REPOSITORIO DINÁMICO (TAD) ---");
        System.out.println("Lecturas almacenadas: " + repositorio.tamano());
        System.out.println("Capacidad final del arreglo: " + repositorio.getCapacidadActual());
        System.out.println("Redimensionamientos realizados: " + repositorio.getRedimensionamientos());
        System.out.println("Copias de elementos realizadas: " + repositorio.getCopiasRealizadas());

        System.out.println("\n--- RESULTADOS ANÁLISIS MATRIZ (SIN CERO FANTASMA) ---");
        System.out.printf("Promedio EST-003 (sin ceros fantasmas): %.2f\n", analizador.promedioDeEstacion(2));
        System.out.println("Hora más contaminada de la ciudad: " + analizador.horaMasContaminada() + ":00 hrs");
    }
}