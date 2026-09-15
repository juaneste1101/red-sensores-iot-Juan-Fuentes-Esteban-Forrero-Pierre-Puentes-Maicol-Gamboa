package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorIngesta {

    public void procesarArchivo(String rutaArchivo, RepositorioLecturas repositorio, AnalizadorMatriz analizador) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length < 5) {
                    System.out.println("[DESCARTADA] Fila incompleta: " + linea);
                    continue;
                }

                try {
                    String estacion = partes[0].trim();
                    String fechaHora = partes[1].trim();
                    double temp = Double.parseDouble(partes[2].trim());
                    double hum = Double.parseDouble(partes[3].trim());
                    double pm25 = Double.parseDouble(partes[4].trim());

                    // Validacion de rangos fisicos
                    if (temp < -50 || temp > 60 || hum < 0 || hum > 100 || pm25 < 0) {
                        continue; // Fila fuera de rango
                    }

                    LecturaSensor lectura = new LecturaSensor(estacion, fechaHora, temp, hum, pm25);
                    
                    if (repositorio != null) {
                        repositorio.agregar(lectura);
                    }
                    if (analizador != null) {
                        analizador.cargarLectura(lectura);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("[ERROR DE FORMATO] No se pudieron parsear los números en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}