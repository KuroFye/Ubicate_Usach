package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import modelos.Recomendacion;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class VerTagsSugeridos extends ListFragment{


    private BroadcastReceiver br = null;
    private final String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/recomiendaalgo/";
    private ArrayList<Recomendacion> actorsList;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public VerTagsSugeridos() {
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
        return inflater.inflate(R.layout.ver_tags_sugeridos, container, false);
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
        String item = "Tag: "+actorsList.get(position).getNombrePub()+"\n";
        item = item + "Valoracion: " + actorsList.get(position).getValoracionPub()+ "\n";
        int id_tag = actorsList.get(position).getPubId();
        Fragment valorarTag = new ValorarTag();
        Bundle arguments = new Bundle();
        arguments.putString("item", item);
        arguments.putInt("id_tag", id_tag);
        valorarTag.setArguments(arguments);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, valorarTag);
        transaction.addToBackStack("verTagTag");
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
                actorsList = jh.getTagsSugeridos(intent.getStringExtra("data"));
                //actorsList = jh.getTagsSugeridos(6);
                String[] actorsNames = new String[actorsList.size()];
                for (int i=0; i<actorsList.size();i++ ){
                    actorsNames[i]= " Tag: " + actorsList.get(i).getNombrePub() + " Valoracion: " + actorsList.get(i).getValoracionPub();
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
