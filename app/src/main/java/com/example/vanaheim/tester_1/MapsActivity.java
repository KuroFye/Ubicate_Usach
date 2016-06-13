package com.example.vanaheim.tester_1;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public MapsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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

        LatLng usach = new LatLng(-33.4501044,-70.6858881);
        mMap.addMarker(new MarkerOptions().position(usach).title("Usachita :3"));

        LatLng usach2 = new LatLng(-33.4497262,-70.6877551);
        mMap.addMarker(new MarkerOptions().position(usach2).title("Info"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacion, 15));
    }
}
