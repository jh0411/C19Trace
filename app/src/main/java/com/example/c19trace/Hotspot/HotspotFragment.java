package com.example.c19trace.Hotspot;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;
public class HotspotFragment extends Fragment {

    private boolean isPermissionGranted;
    private TextInputEditText searchPlaces;
    private ImageView searchIcon;
    GoogleMap mGoogleMap;
    private FusedLocationProviderClient mLocationClient;
    private int GPS_REQUEST_CODE;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotspot, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        searchPlaces = view.findViewById(R.id.places);
        searchIcon = view.findViewById(R.id.search_location);

        mLocationClient = new FusedLocationProviderClient(getActivity());

        initMap();

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationName = searchPlaces.getText().toString();
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try{
                    List<Address> addressList = geocoder.getFromLocationName(locationName, 3);

                    if(addressList.size() > 0){
                        Address address = addressList.get(0);
                        goToLocation(address.getLatitude(), address.getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

//        searchIcon.setOnClickListener(this::geoLocate);

    }

//    private void geoLocate(View view) {
//
//        String locationName = searchPlaces.getText().toString();
//        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//
//        try{
//            List<Address> addressList = geocoder.getFromLocationName(locationName, 3);
//
//            if(addressList.size() > 0){
//                Address address = addressList.get(0);
//                goToLocation(address.getLatitude(), address.getLongitude());
//                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    private void getCurrentLocation(){
        checkPermission();
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                if(location == null){
                    Intent intent = new Intent(getActivity(), HomeFragment.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(),"Location not Found!",Toast.LENGTH_SHORT).show();
                }
                else{
                    goToLocation(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    private void goToLocation(double latitude, double longtitude){
        LatLng latLng = new LatLng(latitude, longtitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        MarkerOptions options = new MarkerOptions().position(latLng);
        mGoogleMap.addMarker(options);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void initMap(){
        checkPermission();
        if(isPermissionGranted){
            if(isGPSEnabled()){
                SupportMapFragment supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
                supportMapFragment.getMapAsync((OnMapReadyCallback) this);
                getCurrentLocation();
            }
        }
    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnable){
            return true;
        }else{
            androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for C19Trace to work. Please enable GPS.")
                    .setPositiveButton("Yes",((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private void checkPermission(){
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(getActivity(),"Permission Granted", Toast.LENGTH_SHORT).show();
                        isPermissionGranted = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getActivity(),"Permission Denied", Toast.LENGTH_SHORT).show();
                        isPermissionGranted = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }


    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        checkPermission();
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.option_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.sv_searchBar);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//
//        if(searchItem != null){
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if(searchView != null){
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//            queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("OnQueryTextChange", newText);
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("OnQueryTextSubmit", query);
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.sv_searchBar:
//                return false;
//            default:
//                break;
//        }
//        searchView.setOnQueryTextListener(queryTextListener);
//        return super.onOptionsItemSelected(item);
//    }
}