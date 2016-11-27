package com.lampnc.businfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RouteList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteList extends Fragment {

    private String BUSINFO_HOST;
    private String BUSINFO_PORT;

    private View view;
    private ListView listView;
    private FragmentActivity context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int selectedRouteId;


    public RouteList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteList.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteList newInstance(String param1, String param2) {
        RouteList fragment = new RouteList();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        new HttpRequestTask().execute();
    }

    public void displayRouteInfo(long routeId) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_route_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_item);
        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRouteId = (int) parent.getItemIdAtPosition(position);
                new DetailRequestTask().execute();
            }
        };
        listView.setOnItemClickListener(itemClick);
        return view;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, BusRouteMem[]> {
        @Override
        protected BusRouteMem[] doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/routes");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<BusRouteMem[]> responseEntity = restTemplate.getForEntity(sb.toString(), BusRouteMem[].class);
                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(BusRouteMem[] arr) {
            ArrayList<Long> ids = new ArrayList<>();
            ArrayList<String> nums = new ArrayList<>();
            ArrayList<String> items = new ArrayList<>();
            if (arr == null) return;
            for (BusRouteMem pd : arr) {
                ids.add((long) pd.getRouteId());
                nums.add(pd.getRouteNo());
                items.add(pd.getRouteName());
            }
            ArrayAdapter<String> adapter = new ListViewAdapter(context, R.layout.item_listview, items, nums, ids);
            listView.setAdapter(adapter);
        }

    }

    private class DetailRequestTask extends AsyncTask<Void, Void, BusRoute> {
        @Override
        protected BusRoute doInBackground(Void... params) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(BUSINFO_HOST);
                sb.append(":");
                sb.append(BUSINFO_PORT);
                sb.append("/route/");
                sb.append(selectedRouteId);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                BusRoute rt = restTemplate.getForObject(sb.toString(), BusRoute.class);
                return rt;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        private String getBusType(int busType) {
            switch (busType) {
                case 1:
                    return "Phổ thông - Không trợ giá";
                case 2:
                    return "Phổ thông - Có trợ giá";
                case 3:
                    return "Công nhân - Có trợ giá";
                default:
                    return "Khác";
            }
        }

        private String getStatus(int status) {
            switch (status) {
                case 1:
                    return "Đang khai thác";
                case 2:
                    return "Tạm ngưng khai thác";
                case 3:
                    return "Đã hủy";
                default:
                    return "Chưa khai thác";
            }
        }

        @Override
        protected void onPostExecute(BusRoute rt) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tên tuyến: " + rt.getRouteName() + "\n\n");
            sb.append("Đi " + rt.getOutBoundName() + ": " + rt.getOutBoundDescription() + "\n");
            sb.append("Đi " + rt.getInBoundName() + ": " + rt.getOutBoundDescription() + "\n\n");
            sb.append("Đơn vị đảm nhận:\n");
            BusOrg[] orgs = rt.getOrgs();
            for (BusOrg org : orgs) {
                sb.append(" - " + org.getOrgName() + ", ĐT: " + org.getOrgPhone() + "\n");
            }
            sb.append("\n");
            sb.append("Loại hình hoạt động: " + getBusType(rt.getBusType()) + "\n");
            sb.append("Cự ly: " + rt.getDistance() / 1000 + " km\n");
            sb.append("Loại xe: " + rt.getNumOfSeats() + "\n");
            sb.append("Thời gian hoạt động: " + rt.getOperationTime() + "\n\n");
            sb.append("Số chuyến: " + rt.getTotalTrip().replace("[TPD]", "chuyến/ngày") + "\n");
            sb.append("Thời gian chuyến: " + rt.getTimeOfTrip() + " phút\n");
            sb.append("Giãn cách chuyến: " + rt.getHeadway() + " phút\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Tuyến số " + rt.getRouteNo())
                    .setMessage(sb.toString())
                    .setNeutralButton("OK", null)
                    .setPositiveButton("Xem hành trình", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((MainActivity)context).setSelectedRouteId(selectedRouteId);
                            ((MainActivity)context).setFragment(RouteMap.class);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

}
