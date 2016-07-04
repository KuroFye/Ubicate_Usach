package modelos;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class Valoracion {
    private int rating;
    private int idvaloracion;
    private int idUsuario;
    private String Texto;
    private int idPublicacion;

    public Valoracion(int rating, int idvaloracion, int idUsuario, String texto, int idPublicacion) {
        this.rating = rating;
        this.idvaloracion = idvaloracion;
        this.idUsuario = idUsuario;
        Texto = texto;
        this.idPublicacion = idPublicacion;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(int idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
}
