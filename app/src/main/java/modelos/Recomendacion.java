package modelos;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class Recomendacion {
    int cantidad;
    String nombrePub;
    int pubId;
    int sumaPub;
    int valoracionPub;

    public Recomendacion(int cantidad, String nombrePub, int pubId, int sumaPub, int valoracionPub) {
        this.cantidad = cantidad;
        this.nombrePub = nombrePub;
        this.pubId = pubId;
        this.sumaPub = sumaPub;
        this.valoracionPub = valoracionPub;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombrePub() {
        return nombrePub;
    }

    public void setNombrePub(String nombrePub) {
        this.nombrePub = nombrePub;
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    public int getSumaPub() {
        return sumaPub;
    }

    public void setSumaPub(int sumaPub) {
        this.sumaPub = sumaPub;
    }

    public int getValoracionPub() {
        return valoracionPub;
    }

    public void setValoracionPub(int valoracionPub) {
        this.valoracionPub = valoracionPub;
    }
}
