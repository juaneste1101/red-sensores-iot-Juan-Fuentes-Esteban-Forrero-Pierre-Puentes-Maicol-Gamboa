package src;

/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   TAD RepositorioLecturas - SEMANA 3
   ============================================================ */

public class RepositorioLecturas {

    private static final int CAPACIDAD_INICIAL = 10;

    private LecturaSensor[] lecturas;
    private int cantidad;

    public RepositorioLecturas() {
        this.lecturas = new LecturaSensor[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }

    public boolean agregar(LecturaSensor lectura) {
        if (cantidad == lecturas.length) {
            redimensionar();
        }
        lecturas[cantidad] = lectura;
        cantidad++;
        return true;
    }

    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return null;
        }
        return lecturas[posicion];
    }

    public int tamano() {
        return cantidad;
    }

    public void eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        for (int i = posicion; i < cantidad - 1; i++) {
            lecturas[i] = lecturas[i + 1];
        }
        lecturas[cantidad - 1] = null;
        cantidad--;
    }

    public LecturaSensor buscarPorEstacion(String idSensor) {
        for (int i = 0; i < cantidad; i++) {
            if (lecturas[i] != null && lecturas[i].getIdSensor().equals(idSensor)) {
                return lecturas[i];
            }
        }
        return null;
    }

    public void actualizar(int posicion, LecturaSensor nueva) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        if (nueva == null) {
            throw new IllegalArgumentException("La lectura no puede ser null");
        }
        lecturas[posicion] = nueva;
    }

    private void redimensionar() {
        LecturaSensor[] nuevo = new LecturaSensor[lecturas.length * 2];
        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = lecturas[i];
        }
        lecturas = nuevo;
    }

    public double promedioPm25() {
        if (cantidad == 0) return 0;
        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            suma += lecturas[i].getPm25();
        }
        return suma / cantidad;
    }
}