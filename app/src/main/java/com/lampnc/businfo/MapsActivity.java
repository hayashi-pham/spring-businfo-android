package com.lampnc.businfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private String BUSINFO_HOST;
    private String BUSINFO_PORT;

    private static final long INTERVAL = 1000 * 100;
    private static final long FATEST_INTERVAL = 1000 * 50;
    private static final int MY_ACCESS_COARSE_LOCATION = 1;
    private static final int MY_ACCESS_FINE_LOCATION = 2;

    private GoogleMap mMap;
    private boolean mRequestingLocationUpdates;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private int selectedStopId;
    private FragmentActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_maps);
        context = getActivity();

        BUSINFO_HOST = getString(R.string.businfo_host);
        BUSINFO_PORT = getString(R.string.businfo_port);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // mapFragment.getMapAsync(this);

        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // startLocationUpdates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Please check permission", Toast.LENGTH_LONG).show();
            mCurrentLocation = new Location("default");
            mCurrentLocation.setLongitude(getFloat(R.dimen.default_location_lng));
            mCurrentLocation.setLatitude(getFloat(R.dimen.default_location_lat));
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    return;
                } else {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_ACCESS_COARSE_LOCATION);
                }
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_ACCESS_FINE_LOCATION);
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        // new StopRequestTask().execute();
    }

    private float getFloat(int id) {
        TypedValue outValue = new TypedValue();
        getResources().getValue(id, outValue, true);
        return outValue.getFloat();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.clear();

        mCurrentLocation = new Location("default");
        mCurrentLocation.setLongitude(getFloat(R.dimen.default_location_lng));
        mCurrentLocation.setLatitude(getFloat(R.dimen.default_location_lat));
        new StopRequestTask().execute();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        new StopRequestTask().execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_ACCESS_FINE_LOCATION:
            case MY_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
                return;
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        BusStopDto dt = (BusStopDto) marker.getTag();
        if (dt != null) {
            selectedStopId = dt.getStopId();
            new PredictRequestTask().execute();
        }
        return false;
    }

    private class StopRequestTask extends AsyncTask<Void, Void, BusStopDto[]> {
        @Override
        protected BusStopDto[] doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/stops/");
                sb.append(mCurrentLocation.getLongitude());
                sb.append("/");
                sb.append(mCurrentLocation.getLatitude());
                sb.append("/");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<BusStopDto[]> responseEntity = restTemplate.getForEntity(sb.toString(), BusStopDto[].class);
                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(BusStopDto[] stops) {
            double lat = mCurrentLocation.getLatitude();
            double lng = mCurrentLocation.getLongitude();
            mMap.clear();
            LatLng c = new LatLng(lat, lng);
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(10, 106))
                    .radius(100)
                    .strokeColor(Color.parseColor("#0D47A1"))
                    .fillColor(Color.parseColor("#802196F3")));
            circle.setCenter(c);
            mMap.addMarker(new MarkerOptions().position(c).title("Vị trí hiện tại").icon(BitmapDescriptorFactory.fromResource(R.mipmap.current_location)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 18.0f));
            if (stops == null) return;
            for (BusStopDto s : stops) {
                LatLng l = new LatLng(s.getLatitude(), s.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(l).title(s.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.bus_stop_marker)));
                marker.setTag(s);
            }
        }
    }

    private class PredictRequestTask extends AsyncTask<Void, Void, PredictionDto[]> {
        @Override
        protected PredictionDto[] doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/predict?");
                sb.append("s=");
                sb.append(selectedStopId);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<PredictionDto[]> responseEntity = restTemplate.getForEntity(sb.toString(), PredictionDto[].class);
                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(PredictionDto[] pds) {
            if (pds == null) return;
            PredictionDto p = pds[0];
            VehicleDto v = p.getVehicles().get(0);
            for (PredictionDto pd : pds) {
                for (VehicleDto vh : pd.getVehicles()) {
                    if (vh.getTimeLeft() < v.getTimeLeft()) {
                        v = vh;
                        p = pd;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Tuyến số: " + p.getRouteNo() + "\n");
            sb.append("Thời gian: " + Math.round(v.getTimeLeft() / 60) + " phút" + "\n");
            new AlertDialog.Builder(context).setTitle("Xe gần nhất").setMessage(sb.toString()).setNeutralButton("OK", null).show();
        }

    }

}
