package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by OscarDesailles on 7/3/2016.
 */
public class ValorarTag extends android.support.v4.app.Fragment{

    private BroadcastReceiver br = null;
    private View contentView = null;
    private int id_tag;

    public int getIdTag(){
        return this.id_tag;
    }

    public ValorarTag(){

    }

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
        View v = inflater.inflate(R.layout.valorar_tag, container, false);
        contentView = v;
        return v;
    }

    /**
     * Método que se llama una vez que se ha restaurado el estado del fragmento
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        TextView tv = (TextView)contentView.findViewById(R.id.text_datos_lugar);
        tv.append(bundle.getString("item"));
        id_tag = bundle.getInt("id_tag");
        super.onViewStateRestored(savedInstanceState);
    }// onViewStateRestored(Bundle savedInstanceState)


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
