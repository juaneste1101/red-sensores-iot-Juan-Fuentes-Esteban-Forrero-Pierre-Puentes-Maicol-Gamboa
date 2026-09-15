# Plataforma de Monitoreo Ambiental Urbano

**Integrantes:**
- Juan Fuentes
- Esteban Forrero
- Pierre Puentes
- Maicol Gamboa

## Descripción del Proyecto
Núcleo de procesamiento, validación y almacenamiento dinámico de datos para la Red de Monitoreo Ambiental Urbano. Desarrollado en Java puro sin bases de datos externas, aplicando estructuras de datos modulares (TAD), matrices bidimensionales, control de excepciones y trazabilidad estricta con Git.

## Estructura del Proyecto
- `src/`: Código fuente en Java (`ProcesadorIngesta`, `RepositorioLecturas`, `MatrizEstacionHora`, `Main`).
- `data/`: Dataset de pruebas en formato CSV (`lecturas.csv`).
- `bitacoras/`: Documentación y seguimiento de hitos semanales del equipo.

## Ejecución
1. Compilar los archivos fuente desde la raíz:
   ```bash
   cd src
   javac *.java
   cd ..