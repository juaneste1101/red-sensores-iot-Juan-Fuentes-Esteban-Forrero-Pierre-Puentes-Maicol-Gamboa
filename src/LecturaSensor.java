package src;

public class LecturaSensor {
    private String idEstacion;
    private String fechaHora;
    private double temperatura;
    private double humedad;
    private double pm25;

    public LecturaSensor(String idEstacion, String fechaHora, double temperatura, double humedad, double pm25) {
        this.idEstacion = idEstacion;
        this.fechaHora = fechaHora;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.pm25 = pm25;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getPm25() {
        return pm25;
    }

    // Extrae la hora (0-23) del formato "YYYY-MM-DD HH:MM"
    public int getHora() {
        try {
            String[] partes = fechaHora.split(" ");
            if (partes.length > 1) {
                String[] horaMinuto = partes[1].split(":");
                return Integer.parseInt(horaMinuto[0]);
            }
        } catch (Exception e) {
            // Si la fecha viene solo como hora
            try {
                String[] horaMinuto = fechaHora.split(":");
                return Integer.parseInt(horaMinuto[0]);
            } catch (Exception ignored) {}
        }
        return 0;
    }
}