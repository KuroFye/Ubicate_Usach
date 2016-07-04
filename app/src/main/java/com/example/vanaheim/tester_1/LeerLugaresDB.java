package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

/**
 * Created by OscarDesailles on 7/4/2016.
 */
public class LeerLugaresDB extends android.support.v4.app.Fragment{
    private BroadcastReceiver br = null;
    private final String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/publicaciones/";
    private ArrayList<Lugar> actorsList;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public LeerLugaresDB() {
    }// ItemList()

    /**
     * Método que se llama una vez que se ha creado la actividad que contiene al fragmento
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }// onActivityCreated(Bundle savedInstanceState)


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String lugares = intent.getStringExtra("data");
                Fragment verRegistroActividad = new VerRegistroActividad();
                Bundle arguments = new Bundle();
                arguments.putString("request", lugares);
                verRegistroActividad.setArguments(arguments);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, verRegistroActividad);
                transaction.addToBackStack(null);
                transaction.commit();
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
