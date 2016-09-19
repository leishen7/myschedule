package com.nucome.app.crm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nucome.app.weekview.AddEventActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchServiceActivity extends AppCompatActivity {
    private String TAG = SearchServiceActivity.class.getSimpleName();
    private String URL_INFO = "http://www.wuzhenweb.com:8089/json?operation=getservicelist";
    private String startTime;
    private String endTime;

    private Spinner serviceCategorySpin;
    private EditText serviceEditText;
    private Button serviceButton;

    protected SwipeRefreshLayout swipeRefreshLayout;
    protected SwipeServiceListAdapter adapter;
    protected ListView listView;
    protected List<ServiceInfo> serviceList=new ArrayList<ServiceInfo>();
    protected List<ServiceInfo> allServiceList=new ArrayList<ServiceInfo>();
    Set<String> categorySet=new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);

        final Intent intent = getIntent();
        if (intent != null ) {

            startTime=intent.getStringExtra(getString(R.string.EVENT_START_TIME));

            endTime=intent.getStringExtra(getString(R.string.EVENT_END_TIME));

        }


        serviceCategorySpin = (Spinner) findViewById(R.id.servicecategorySpin);

      //  serviceEditText = (EditText) findViewById(R.id.serviceEditText);
        listView = (ListView) findViewById(R.id.serviceListView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        adapter = new SwipeServiceListAdapter(this, serviceList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                ServiceInfo service = (ServiceInfo) parent.getItemAtPosition(position);
                intent.putExtra(getString(R.string.EVENT_NAME),service.getServiceName());
                intent.putExtra(getString(R.string.EVENT_LOCATION),service.getAddress());
                intent.putExtra(getString(R.string.EVENT_DESCRIPTION),service.getDescription());
                intent.putExtra(getString(R.string.EVENT_START_TIME),startTime);
                intent.putExtra(getString(R.string.EVENT_END_TIME),endTime);

                intent.putExtra(getString(R.string.SERVICE_ID),service.getServiceId());
                intent.putExtra(getString(R.string.SERVICE_PROVIDER),service.getProviderName());
                startActivity(intent);

            }
        });


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchServices(URL_INFO);
            }
        });

        serviceCategorySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), serviceCategorySpin.getSelectedItem().toString()+"selected",Toast.LENGTH_LONG).show();
                serviceList.clear();
                for(ServiceInfo serv:allServiceList){
                    if(serv.getCategory().equals(serviceCategorySpin.getSelectedItem().toString())){
                        serviceList.add(serv);
                    }
                }
                adapter.notifyDataSetChanged();
            }



            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


//        serviceEditText.setSelected(true);



    }


    private void fetchServices(String url) {
        swipeRefreshLayout.setRefreshing(true);
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
             //   saveToCache(response.toString(),fileName);
                parseResponse(response.toString(),serviceList);
                allServiceList.clear();
                allServiceList.addAll(serviceList);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        TradeApplication.getInstance().addToRequestQuest(req);
    }


    protected void parseResponse(String responseString,List<ServiceInfo> newsList){
        if (responseString.length() > 0) {
            try {
                JSONObject newsObjs = new JSONObject(responseString);
                JSONArray rates = newsObjs.getJSONArray("content");
                categorySet.clear();

                for (int i = 0; i < rates.length(); i++) {
                    ServiceInfo service = new ServiceInfo();
                    JSONObject newsObj = rates.getJSONObject(i);
                    service.setServiceId(newsObj.getString("serviceId"));
                    service.setServiceName(newsObj.getString("serviceName"));

                    service.setProviderName(newsObj.getString("providerName"));
                    service.setDescription(newsObj.getString("description"));
                    service.setCategory(newsObj.getString("category"));
                    categorySet.add(newsObj.getString("category"));
                    service.setAddress(newsObj.getString("address"));
                    if(!newsObj.getString("latitude").equals("null"))
                        service.setLatitude(newsObj.getDouble("latitude"));
                    if(!newsObj.getString("longitude").equals("null"))
                        service.setLongitude(newsObj.getDouble("longitude"));
                    newsList.add(service);
                }
                ArrayAdapter<String> itemsadapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, categorySet.toArray(new String[categorySet.size()]));
                serviceCategorySpin.setAdapter(itemsadapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
            }

        }
    }
}
