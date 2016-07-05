package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import modelos.Valoracion;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

/**
 * Created by OscarDesailles on 7/4/2016.
 */
public class VerRecomendaciones extends ListFragment {
    private BroadcastReceiver br = null;
    private ArrayList<Valoracion> actorsList;
    private ArrayList<Lugar> listaLugares;
    private ArrayList<Lugar> listaRecomendaciones;
    private int ID_ACTIVE_USER=1;
    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public VerRecomendaciones() {
    }// ItemList()

    /**
     * Método que se llama una vez que se ha creado la actividad que contiene al fragmento
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }// onActivityCreated(Bundle savedInstanceState)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ver_recomendaciones, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        JsonHandler jh = new JsonHandler();
        listaLugares = jh.getPublicaciones(bundle.getString("request"));
        //listaLugares = jh.getPublicaciones(25);
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Metodo para arreglar deficiencia en las consultas de la base de datos
     */
    public void inner_join_on_pubid(){
        for (int i=0; i<actorsList.size();i++){
            for (int j=0;j<listaLugares.size();j++){
                if(actorsList.get(i).getIdPublicacion() == listaLugares.get(j).getPubId()){
                    actorsList.get(i).setNombrePublicacion(listaLugares.get(j).getNombrePub());
                    actorsList.get(i).setCodigo_publicacion(listaLugares.get(j).getCodigoPub());
                }
            }
        }
    }

    public void ordenar_prioridades(){
        for (int i=0; i<actorsList.size();i++) {
            if(actorsList.get(i).getRating()>=4){
                for (int j=0; j<listaLugares.size();j++){
                    if(listaLugares.get(j).getCodigoPub().equals(actorsList.get(i).getCodigo_publicacion())){
                        listaRecomendaciones.add(listaLugares.get(j));
                    }
                }
            }
        }
    }

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");
        String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/valoraciones/";
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                listaRecomendaciones = new ArrayList<Lugar>();
                actorsList = jh.getValoraciones(intent.getStringExtra("data"), ID_ACTIVE_USER, 3);
                //actorsList = jh.getValoraciones(15, 3);
                inner_join_on_pubid();
                ordenar_prioridades();
                String[] registro_actividad = new String[listaRecomendaciones.size()];
                registro_actividad[0]="Te recomendamos las siguientes publicaciones!";
                for (int i=1; i<listaRecomendaciones.size();i++ ){
                    registro_actividad[i]= "Nombre: " + listaRecomendaciones.get(i).getNombrePub() +" Valoracion: "+listaRecomendaciones.get(i).getValoracionPub();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                        , android.R.layout.simple_list_item_1, registro_actividad);
                setListAdapter(adapter);
            }
        };
        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            new HttpGet(getActivity().getApplicationContext()).execute(URL_GET);
        }
        super.onResume();
    }// onResume()

    /**
     * Método que se ejecuta luego que el fragmento se detiene
     */
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }// onPause()
}
