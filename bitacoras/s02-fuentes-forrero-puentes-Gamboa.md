# Bitacora grupal - Semana 02

## 1. Datos de la actividad

- **Estudiantes:** Juan Fuentes, Esteban Forrero, Pierre Puentes, Maicol Gamboa
- **Equipo:** Juan-Fuentes-Esteban-Forrero-Pierre-Puentes-Maicol-Gamboa
- **Semana:** 02
- **Fecha del laboratorio:** 2026-03-29
- **Fecha del taller:** 2026-03-29
- **Tema principal:** Tipos Abstractos de Datos (TAD), arreglos dinámicos y matrices bidimensionales
- **Pregunta de la semana:** ¿Cómo almacenar dinámicamente las lecturas filtradas y representarlas por hora sin falsear los promedios por ausencia de datos?

## 2. Prediccion antes de ejecutar

1. **Que creo que va a ocurrir?**
   Creemos que al almacenar más de 10 lecturas en un arreglo de tamaño fijo se generará un desbordamiento o pérdida silenciosa de datos, y que inicializar la matriz con ceros alterará a la baja el promedio de las estaciones que no reportaron todas las horas.
2. **Que parte del programa o del algoritmo puede fallar?**
   El método `agregar()` del repositorio por falta de capacidad y la función `promedioDeEstacion()` al dividir sobre un total fijo de 24 horas.
3. **Como comprobare mi prediccion?**
   Inyectando el dataset completo de 211+ filas y comparando las métricas de redimensionamiento e impresiones de promedios en consola.

## 3. Evidencia del laboratorio

### Resultado observado
El sistema gestionó exitosamente 212 lecturas válidas tras descartar las filas dañadas. El arreglo dinámico creció de capacidad 10 a 320 tras 5 redimensionamientos (realizando 310 copias). La matriz omitió los valores nulos, ajustando el promedio real de EST-003 a 64,20 sin sumar ceros fantasma.

### Diferencia entre la prediccion y el resultado
El resultado validó la hipótesis: el crecimiento exponencial de memoria amortiza el costo de copia y la representación con objetos `Double` previene la distorsión del análisis ambiental.

### Error o comportamiento inesperado
- **Que ocurrio?** Ceros iniciales en la matriz distorsionaban los promedios al contar casillas sin medición.
- **Por que ocurrio?** Los tipos primitivos (`double`) se inicializan por defecto en `0.0`.
- **Como lo corregimos o que falta corregir?** Se reemplazó por la clase envolvente `Double[][]`, donde la ausencia de datos se representa explícitamente mediante `null`.

## 4. Explicacion en lenguaje llano

Un arreglo dinámico funciona como un cuaderno de notas. Si el cuaderno de 10 páginas se llena, en vez de arrancar hojas o detener la escritura, se compra uno del doble de capacidad (20 páginas), se reescriben los apuntes anteriores en el nuevo cuaderno y se continúa trabajando normalmente.

### Ejemplo o analogia
Es como organizar un parqueadero de vehículos: si no hay cupos disponibles, se habilita un lote contiguo con el doble de capacidad y se reubican los carros parqueados previamente para mantener el orden.

## 5. El vacio que encontre

- **Mi duda concreta es:** ¿Qué impacto en la memoria tiene utilizar objetos `Double` frente al tipo primitivo `double` en matrices de gran escala?
- **Lo que ya puedo explicar es:** `Double` permite asignar valores `null` para identificar ausencia de datos.
- **Para resolver la duda consulte:** Documentación oficial de Java sobre autounboxing y consumo de objetos Wrapper.
- **Ahora lo entiendo asi:** Aunque `Double` consume más espacio por ser un objeto, es imprescindible cuando se requiere distinguir conceptualmente entre una medición real de cero y una falta de reporte.

## 6. Trazado de la solucion

Trazado del redimensionamiento en `RepositorioLecturas` al intentar insertar la lectura número 11.

| Paso | Estado de los datos o estructura | Decision o resultado |
|---|---|---|
| 1 | `cantidad = 10`, `capacidad = 10` | Se invoca `agregar()`. Se detecta `cantidad >= capacidad`. |
| 2 | Asignación de memoria | Se crea un `nuevo` arreglo con capacidad duplicada (20). |
| 3 | Copia contigua | Bucle `for` traslada los 10 elementos al nuevo arreglo y suma 10 al contador de copias. |
| 4 | Reasignación de puntero | `lecturas = nuevo`. La capacidad se actualiza a 20. |
| 5 | Inserción | Se guarda la lectura 11 en el índice 10 y `cantidad` se incrementa a 11. |

## 7. Decision de diseño

- **Problema que debiamos resolver:** Almacenar lecturas sin conocer previamente la cantidad exacta y calcular promedios limpios.
- **Estructura, algoritmo o estrategia elegida:** Arreglo dinámico con factor de crecimiento por duplicación (\(x2\)) y matriz `Double[][]`.
- **Alternativa descartada:** Crecimiento de uno en uno (\(+1\)) e inicialización con primitivos `double`.
- **Por que elegimos la primera:** Duplicar la capacidad reduce drásticamente las operaciones de copia a bajo nivel en memoria.
- **Que evidencia respalda la decision:** La consola registró sólo 310 copias de elementos para 212 lecturas almacenadas.

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** `src/RepositorioLecturas.java`, `src/AnalizadorMatriz.java`, `src/ProcesadorIngesta.java`.
- **Cambio realizado:** Encapsulamiento de las operaciones CRUD del TAD, redimensionamiento exponencial y gestión de ausencias en matriz.
- **Como se conecta con la capa anterior:** Recibe las lecturas instanciadas y filtradas por el módulo de ingesta confiable de la Semana 01.
- **Que queda pendiente para la siguiente semana:** Implementación de algoritmos de búsqueda eficiente (secuencial vs. binaria) sobre el repositorio.

## 9. Commits realizados

| Commit | Mensaje | Que demuestra |
|---|---|---|
| `31b63be` | `DOC: actualiza README y repositorio con el cuarto integrante Maicol Gamboa` | Gestión del equipo e infraestructura. |
| `(Añadir hash)` | `FEAT: implementa TAD RepositorioLecturas y AnalizadorMatriz sin ceros fantasma` | Funcionalidad completa de la Semana 02. |

## 10. Reexplicacion final

Encapsular el repositorio en un TAD permite gestionar el crecimiento en memoria de manera transparente. Al multiplicar por dos la capacidad cada vez que el arreglo se satura, logramos una complejidad amortizada de inserción \(O(1)\). Por otro lado, manejar la ausencia de registros con referencias `null` evita distorsiones estadísticas al calcular promedios ambientales.

## 11. Reflexion individual

1. **Lo que ahora puedo hacer y antes no podia:** Construir y analizar las métricas de un arreglo dinámico con redimensionamiento personalizado.
2. **El error o supuesto que mas me enseno:** Asumir que `0.0` representa falta de medición, omitiendo que en física el cero es un dato válido.
3. **La pregunta que llevaria a la proxima clase:** ¿Cómo afecta la fragmentación de memoria cuando se realizan múltiples redimensionamientos en sistemas IoT masivos?
4. **Que parte del trabajo fue realmente mia:** La integración de la clase `AnalizadorMatriz` y la verificación de la compilación en PowerShell.

## Lista de verificacion antes de entregar

- [x] Escribi la prediccion antes de consultar el resultado.
- [x] Inclui evidencia concreta del laboratorio.
- [x] Explique un concepto sin depender de jerga.
- [x] Registre un vacio, una duda o un error real.
- [x] Trace al menos un caso paso a paso.
- [x] Justifique una decision del proyecto y una alternativa descartada.
- [x] Registre mis commits y mi aporte individual.
- [x] Deje claro que queda pendiente.
- [x] Renombre el archivo con el formato requerido.