package utilidades;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import modelos.Usuario;
import modelos.Lugar;

/**
 * Created by bpastene on 20-05-16.
 */
public class JsonHandler{
    /**
     * Método que recibe un JSONArray en forma de String y devuelve un String[] con los actores
     */
    public Usuario[] getActors(String actors) {
        try {
            JSONArray ja = new JSONArray(actors);
            Usuario[] result = new Usuario[ja.length()];
            Usuario actor;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.getJSONObject(i);
                actor = new Usuario(row.getString("user"), row.getString("pass"),row.getString("mail"));
                result[i] = actor;
            }
            return result;
        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }// getActors(String actors)


    public JSONArray getJsonActor(Usuario actor) {
        try {
            JSONArray ja = new JSONArray().put(actor);
            return ja;
        } catch (Exception e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }// getActors(String actors)

    /*
     Metodo que recibe un JSONarray en forma de String y devuelve un String[] con los lugares
      */

                public ArrayList getPublicaciones(String publicacion) {
                try {
                        // se pasa el string gigante a un objeto JSON
                        JSONArray ja = new JSONArray(publicacion);
                        // se crea el arraylist que se va a devolver al final, parte vacio
                        ArrayList<Lugar> result = new ArrayList<Lugar>();
                        //se setean las variables auxiliares que se van a ocupar para crear los objetos Lugar
                        String nombrePub, codigoPub, descripcionPub, latitud, longitud;
                        int tipoPub, valoracionPub, pubId;
                        //el for va a recorrer todos los lugares del JSON, osea, todas las lineas devueltas por la consulta
                        for (int i = 0; i < ja.length(); i++){
                                //obtiene la linea (la fila en la DB)
                                 JSONObject row = ja.getJSONObject(i);
                                //busca el valor asociado a nombrePub y lo transforma a string
                            if (row.getInt("tipoPublicacionPub") != 2){
                                    nombrePub = row.getString("nombrePub");
                                    //lo mismo, pero lo transforma a int
                                    tipoPub = row.getInt("tipoPublicacionPub");
                                    codigoPub = row.getString("codigoPub");
                                    descripcionPub = row.getString("descripcionPub");
                                    latitud = row.getString("latPub");
                                    longitud = row.getString("lonPub");
                                    pubId = row.getInt("pubId");
                                    //aqui pasa la valoracion, si existiera, pero como no estoy seguro del nombre, le voy a dar un
                                    valoracionPub = row.getInt("valoracionPub");
                                    //crea el objeto y lo agrega al arraylist
                                    result.add(new Lugar(codigoPub, descripcionPub, nombrePub, tipoPub, valoracionPub, latitud, longitud, pubId));
                            }
                        }
                        //lo devuelve
                        return result;
                    } catch (JSONException e) {
                        Log.e("ERROR", this.getClass().toString() + " " + e.toString());
                    }
                return null;
            }// getActors(String actors)


    public ArrayList getPublicacionLugar(String publicacion) { try
    { // se pasa el string gigante a un objeto JSON
         JSONObject ja = new JSONObject(publicacion);
        // se crea el arraylist que se va a devolver al final, parte vacio
        ArrayList<Lugar> result = new ArrayList<Lugar>(); //se setean las variables auxiliares que se van a ocupar para crear los objetos Lugar
        String nombrePub, codigoPub, descripcionPub, latitud, longitud;
        int tipoPub, valoracionPub, pubId;
        //el for va a recorrer todos los lugares del JSON, osea, todas las lineas devueltas por la consulta
        // obtiene la linea (la fila en la DB)
        // busca el valor asociado a nombrePub y lo transforma a string
        nombrePub = ja.getString("nombrePub"); //lo mismo, pero lo transforma a int
        tipoPub = ja.getInt("tipoPublicacionPub");
        codigoPub = ja.getString("codigoPub");
        descripcionPub = ja.getString("descripcionPub");
        latitud = ja.getString("latPub");
        longitud = ja.getString("lonPub");
        pubId = ja.getInt("pubId"); //aqui pasa la valoracion, si existiera, pero como no estoy seguro del nombre, le voy a dar un
        valoracionPub = ja.getInt("valoracionPub");
        //crea el objeto y lo agrega al arraylist
        result.add(new Lugar(codigoPub, descripcionPub, nombrePub, tipoPub, valoracionPub, latitud, longitud, pubId)); //lo devuelve
         return result; } catch (JSONException e) { Log.e("ERROR", this.getClass().toString() + " " + e.toString()); } return null; }// getActors(String actors
}