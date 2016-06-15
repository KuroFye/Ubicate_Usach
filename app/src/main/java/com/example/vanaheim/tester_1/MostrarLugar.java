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
import android.widget.TextView;

import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

public class MostrarLugar extends  android.support.v4.app.Fragment {

    private BroadcastReceiver br = null;
    private ArrayList<Lugar> actorsList;
    private View contentView = null;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public MostrarLugar() {
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
        View v = inflater.inflate(R.layout.mostrar_lugar, container, false);
        contentView = v;
        return v;
    }

    /**
     * Método que se llama una vez que se ha restaurado el estado del fragmento
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }// onViewStateRestored(Bundle savedInstanceState)

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        Bundle bundle = getArguments();
        String URL_GET = bundle.getString("item");
        IntentFilter intentFilter = new IntentFilter("httpData");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                actorsList = jh.getPublicacionLugar(intent.getStringExtra("data"));
                String item = "Nombre: "+actorsList.get(0).getNombrePub()+"\n";
                item = item + "Descripcion: " + actorsList.get(0).getDescripcionPub()+"\n";
                TextView tv = (TextView)contentView.findViewById(R.id.text_datos_qr);
                tv.setText(item);
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
