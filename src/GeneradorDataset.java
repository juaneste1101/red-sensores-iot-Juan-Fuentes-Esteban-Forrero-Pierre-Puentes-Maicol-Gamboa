package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneradorDataset {
    public static void main(String[] args) {
        String ruta = "data/lecturas.csv";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            int totalFilas = 0;
            
            // 1. Generar datos válidos para las 9 estaciones y 24 horas
            // (Total ideal: 9 * 24 = 216 lecturas, pero omitiremos las 4 de EST-003)
            String[] estaciones = {"EST-001", "EST-002", "EST-003", "EST-004", "EST-005", "EST-006", "EST-007", "EST-008", "EST-009"};
            
            for (String est : estaciones) {
                for (int h = 0; h < 24; h++) {
                    // Simular que EST-003 no reporta de 09:00 a 12:00 (4 horas vacías)
                    if (est.equals("EST-003") && (h >= 9 && h <= 12)) {
                        continue;
                    }
                    
                    String horaStr = String.format("%02d:00", h);
                    double temp = 15.0 + (h % 5);
                    double hum = 60.0 + (h % 10);
                    
                    // Elevar el PM2.5 de EST-003 para comprobar que es la más contaminada (promedio real ~17.86)
                    double pm25 = est.equals("EST-003") ? 17.86 : 10.0 + (h % 4);
                    
                    writer.printf("%s,2026-09-07 %s,%.1f,%.1f,%.1f\n", est, horaStr, temp, hum, pm25);
                    totalFilas++;
                }
            }
            // Hasta aquí van 201 filas válidas.

            // 2. Inyectar 2 filas corruptas por FORMATO (serán descartadas por el procesador)
            writer.println("EST-005,2026-09-07 08:00,texto_basura,60.0,5.5");
            writer.println("EST-007,2026-09-07 08:00,20.0"); // Fila incompleta
            totalFilas += 2;

            // 3. Inyectar 8 filas fuera de RANGO físico (temperatura < -50 o > 60, PM2.5 < 0)
            for (int i = 1; i <= 8; i++) {
                writer.printf("EST-00%d,2026-09-07 15:00,150.0,60.0,-99.0\n", i);
                totalFilas++;
            }

            System.out.println("Dataset generado exitosamente en 'data/lecturas.csv'. Total filas: " + totalFilas);

        } catch (IOException e) {
            System.err.println("Error al generar el dataset: " + e.getMessage());
        }
    }
}