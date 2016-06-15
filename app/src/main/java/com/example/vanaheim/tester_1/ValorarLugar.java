package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

public class ValorarLugar extends ListFragment {

    private BroadcastReceiver br = null;
    private final String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/publicaciones/";
    private ArrayList<Lugar> actorsList;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public ValorarLugar() {
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
        return inflater.inflate(R.layout.valorar_lugar, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Método que escucha las pulsaciones en los items de la lista
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = "Nombre: "+actorsList.get(position).getNombrePub()+"\n";
        item = item + "Codigo: " + actorsList.get(position).getCodigoPub()+ "\n";
        item = item + "Descripcion: " + actorsList.get(position).getDescripcionPub()+"\n";
        Fragment comentarLugar = new ComentarLugar();
        Bundle arguments = new Bundle();
        arguments.putString("item", item);
        int id_publicacion = actorsList.get(position).getPubId();
        arguments.putInt("id", id_publicacion);
        comentarLugar.setArguments(arguments);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, comentarLugar);
        transaction.addToBackStack(null);
        transaction.commit();
    }// onListItemClick(ListView l, View v, int position, long id)

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                actorsList = jh.getPublicaciones(intent.getStringExtra("data"));
                String[] actorsNames = new String[actorsList.size()];
                for (int i=0; i<actorsList.size();i++ ){
                    actorsNames[i]= " " + actorsList.get(i).getNombrePub();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                        , android.R.layout.simple_list_item_1, actorsNames);
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
