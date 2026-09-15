# Plataforma de Monitoreo Ambiental Urbano

**Integrantes:**
- Juan Fuentes
- Esteban Forrero
- Pierre Puentes
- Maicol Gamboa

## Descripción del Proyecto
Núcleo de procesamiento, validación y almacenamiento dinámico de datos para la Red de Monitoreo Ambiental. Desarrollado en Java puro sin bases de datos externas, aplicando estructuras de datos modulares (TAD), matrices bidimensionales, control de excepciones y trazabilidad estricta con Git.

## Estructura del Proyecto
- `src/`: Contiene el código fuente del motor de ingesta, estructuras de datos y matrices.
- `data/`: Contiene el dataset de prueba (`lecturas.csv`).
- `bitacoras/`: Almacena las bitácoras grupales de seguimiento de cada hito semanal.

## Ejecución
1. Compilar los archivos fuente desde la raíz:
   ```bash
   javac src/*.java