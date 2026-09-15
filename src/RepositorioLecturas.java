package src;

public class RepositorioLecturas {

    private LecturaSensor[] lecturas;
    private int cantidad;
    private int copiasRealizadas;
    private int redimensionamientos;

    public RepositorioLecturas(int capacidadInicial) {
        this.lecturas = new LecturaSensor[capacidadInicial];
        this.cantidad = 0;
        this.copiasRealizadas = 0;
        this.redimensionamientos = 0;
    }

    private void redimensionar() {
        int nuevaCapacidad = lecturas.length * 2;
        LecturaSensor[] nuevo = new LecturaSensor[nuevaCapacidad];
        
        for (int i = 0; i < cantidad; i++) {
            nuevo[i] = lecturas[i];
            copiasRealizadas++;
        }
        
        lecturas = nuevo;
        redimensionamientos++;
    }

    public boolean agregar(LecturaSensor lectura) {
        if (lectura == null) return false;
        if (cantidad >= lecturas.length) {
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

    public LecturaSensor buscarPorEstacion(String idEstacion) {
        if (idEstacion == null) return null;
        for (int i = 0; i < cantidad; i++) {
            if (lecturas[i].getIdEstacion().equalsIgnoreCase(idEstacion)) {
                return lecturas[i];
            }
        }
        return null;
    }

    public boolean actualizar(int posicion, LecturaSensor nueva) {
        if (posicion < 0 || posicion >= cantidad || nueva == null) {
            return false;
        }
        lecturas[posicion] = nueva;
        return true;
    }

    public boolean eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return false;
        }
        for (int i = posicion; i < cantidad - 1; i++) {
            lecturas[i] = lecturas[i + 1];
        }
        lecturas[cantidad - 1] = null;
        cantidad--;
        return true;
    }

    public int tamano() {
        return cantidad;
    }

    public int getCapacidadActual() {
        return lecturas.length;
    }

    public int getCopiasRealizadas() {
        return copiasRealizadas;
    }

    public int getRedimensionamientos() {
        return redimensionamientos;
    }
}