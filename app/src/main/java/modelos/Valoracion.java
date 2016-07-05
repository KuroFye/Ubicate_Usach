package modelos;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class Valoracion {
    private int rating;
    private int idvaloracion;
    private int idUsuario;
    private String Texto, nombrePublicacion, fecha, codigo_publicacion;
    private int idPublicacion;

    public Valoracion(int rating, int idvaloracion, int idUsuario, String texto, int idPublicacion, String fecha) {
        this.rating = rating;
        this.idvaloracion = idvaloracion;
        this.idUsuario = idUsuario;
        Texto = texto;
        this.idPublicacion = idPublicacion;
        this.nombrePublicacion = "no especificado";
        this.fecha = fecha;
    }

    public String getCodigo_publicacion() {
        return codigo_publicacion;
    }

    public void setCodigo_publicacion(String codigo_publicacion) {
        this.codigo_publicacion = codigo_publicacion;
    }

    public int getRating() {
        return rating;
    }

    public String getNombrePublicacion() {
        return nombrePublicacion;
    }

    public void setNombrePublicacion(String nombrePublicacion) {
        this.nombrePublicacion = nombrePublicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
