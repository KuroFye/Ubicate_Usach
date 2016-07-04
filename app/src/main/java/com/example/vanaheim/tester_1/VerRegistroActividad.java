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
import android.widget.TextView;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import modelos.Valoracion;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class VerRegistroActividad extends ListFragment{

    private BroadcastReceiver br = null;
    private ArrayList<Valoracion> actorsList;
    private ArrayList<Lugar> listaLugares;
    private int ID_ACTIVE_USER=1;
    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public VerRegistroActividad() {
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
        return inflater.inflate(R.layout.ver_registro_actividad, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        JsonHandler jh = new JsonHandler();
        listaLugares = jh.getPublicaciones(bundle.getString("request"));
        //listaLugares = jh.getPublicaciones(10);
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
                actorsList = jh.getValoraciones(intent.getStringExtra("data"), ID_ACTIVE_USER, 0);
                //actorsList = jh.getValoraciones(6);
                inner_join_on_pubid();
                String[] registro_actividad = new String[actorsList.size()];
                for (int i=0; i<actorsList.size();i++ ){
                    registro_actividad[i]= " Valoraste la publicacion " + actorsList.get(i).getNombrePublicacion() + " con un " + actorsList.get(i).getRating() + " y comentaste " + actorsList.get(i).getTexto();
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
