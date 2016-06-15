package com.example.vanaheim.tester_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.util.ArrayList;

import Controladores.HttpGet;
import modelos.Lugar;
import utilidades.JsonHandler;
import utilidades.SystemUtilities;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BroadcastReceiver br = null;
    private final String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/publicaciones/";
    private ArrayList<Lugar> actorsList;
    public MapsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location locale = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        final LatLng localizacion = new LatLng(locale.getLatitude(), locale.getLongitude());

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng coordenada) {

                if (coordenada != localizacion){
                    mMap.addMarker(new MarkerOptions().position(coordenada).title("Nueva localizacion"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15));
                }
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacion, 15));
                Circle circulo = null;

                if (circulo == null){
                    circulo = mMap.addCircle(new CircleOptions()
                            .center(localizacion)
                            .radius(40)
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(50, 255, 0, 0)));
                    circulo.setVisible(true);
                    return true;
                }

                else{
                    if (circulo.isVisible()) {
                        circulo.setVisible(false);
                        return false;
                    }
                    else {
                        circulo.setVisible(true);
                        return true;
                    }
                }

            }
        });

        IntentFilter intentFilter = new IntentFilter("httpData");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                actorsList = jh.getPublicaciones(intent.getStringExtra("data"));
                String[] actorsNames = new String[actorsList.size()];
                System.out.println("LATITUD :" + actorsList.get(0).getLatPub());
                System.out.println("LATITUD :" + actorsList.get(1).getLatPub());
                LatLng latlong = new LatLng(actorsList.get(0).getLatPub().doubleValue(), actorsList.get(0).getLonPub().doubleValue());
                mMap.addMarker(new MarkerOptions()
                        .position(latlong)
                        .title(actorsList.get(0).getNombrePub()));

                LatLng latlong2 = new LatLng(actorsList.get(1).getLatPub().doubleValue(), actorsList.get(1).getLonPub().doubleValue());
                mMap.addMarker(new MarkerOptions()
                        .position(latlong2)
                        .title(actorsList.get(1).getNombrePub()));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong2, 15));
                /*for (int i=0; i<actorsList.size();i++ ){

                    LatLng latlong = new LatLng(actorsList.get(i).getLatPub(), actorsList.get(i).getLonPub());
                    mMap.addMarker(new MarkerOptions()
                            .position(latlong)
                            .title(actorsList.get(i).getNombrePub()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
                }*/
            }
        };
        this.registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(this.getApplicationContext());
        if (su.isNetworkAvailable()) {
            new HttpGet(this.getApplicationContext()).execute(URL_GET);
        }


        //LatLng usach = new LatLng(,);
        //mMap.addMarker(new MarkerOptions().position(usach).title("Usachita :3"));

        //LatLng usach2 = new LatLng(,);
        //mMap.addMarker(new MarkerOptions().position(usach2).title("Info"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacion, 15));
    }

}
