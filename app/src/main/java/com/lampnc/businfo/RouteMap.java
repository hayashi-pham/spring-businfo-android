package com.lampnc.businfo;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RouteMap.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RouteMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteMap extends Fragment implements OnMapReadyCallback {

    private String BUSINFO_HOST;
    private String BUSINFO_PORT;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    private FragmentActivity context;

    private int selectedPlanId;
    private int selectedRouteId;

    public RouteMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteMap.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteMap newInstance(String param1, String param2) {
        RouteMap fragment = new RouteMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        BUSINFO_HOST = getString(R.string.businfo_host);
        BUSINFO_PORT = getString(R.string.businfo_port);
        selectedRouteId = ((MainActivity) context).getSelectedRouteId();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_route_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.route_map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, BusRoutePlan[]> {
        @Override
        protected BusRoutePlan[] doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/route/");
                sb.append(selectedRouteId);
                sb.append("/plans");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<BusRoutePlan[]> responseEntity = restTemplate.getForEntity(sb.toString(), BusRoutePlan[].class);
                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(BusRoutePlan[] arr) {
            selectedPlanId = 0;
            if (arr == null) return;
            for (BusRoutePlan pd : arr) {
                if (pd.isOutbound()) selectedPlanId = pd.getPlanId();
            }
            if (selectedPlanId != 0) {new StopRequestTask().execute();
                new PointsRequestTask().execute();
            }
        }

    }

    private class PointsRequestTask extends AsyncTask<Void, Void, CoordinateDto> {
        @Override
        protected CoordinateDto doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/route/");
                sb.append(selectedRouteId);
                sb.append("/");
                sb.append(selectedPlanId);
                sb.append("/paths");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                CoordinateDto dt = restTemplate.getForObject(sb.toString(), CoordinateDto.class);
                return dt;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(CoordinateDto dt) {
           // if (dt == null) return;
            PolylineOptions po = new PolylineOptions();
            // ArrayList<LatLng> latlng = new ArrayList<>();
            double[] lat = dt.getLat();
            double[] lng = dt.getLng();
            for (int i = 0; i < lat.length; i++) {
                // latlng.add(new LatLng(lat[i], lng[i]));
                po.add(new LatLng(lat[i], lng[i]));
            }
            // po.addAll(latlng);
            po.width(10);
            po.color(Color.parseColor("#3F51B5"));
            // po.geodesic(true);
            mMap.addPolyline(po);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat[0], lng[0]), 12.0f));
        }

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
                sb.append("/route/");
                sb.append(selectedRouteId);
                sb.append("/");
                sb.append(selectedPlanId);
                sb.append("/stops");
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
            if (stops == null) return;
            for (BusStopDto s : stops) {
                LatLng l = new LatLng(s.getLatitude(), s.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(l).title(s.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.bus_stop_marker)));
                marker.setTag(s);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
