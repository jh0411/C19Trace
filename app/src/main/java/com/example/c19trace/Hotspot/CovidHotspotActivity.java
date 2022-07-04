package com.example.c19trace.Hotspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.example.c19trace.Home.HomeFragment;
import com.example.c19trace.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.annotations.NotNull;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CovidHotspotActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    EditText location;
    ImageButton search;
    ZoomControls zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_hotspot);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        location = findViewById(R.id.et_Location);
        search = findViewById(R.id.ib_search);
        zoom = findViewById(R.id.zoomcontrol_map);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_location = location.getText().toString();
                Geocoder geocoder=new Geocoder(CovidHotspotActivity.this);
                try {
                    List<Address> addressList=geocoder.getFromLocationName(user_location,10);
                    LatLng latLng=new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker on " + user_location));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.ACCESS_FINE_LOCATION},100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    googleMap.setMyLocationEnabled(true);
            }
            else
                Toast.makeText(getApplicationContext(),"User denied location permission",Toast.LENGTH_SHORT).show();
        }
    }
}