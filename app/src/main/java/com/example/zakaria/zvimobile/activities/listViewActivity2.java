package com.example.zakaria.zvimobile.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zakaria.zvimobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class listViewActivity2 extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ListView myListView;
    ArrayList<String> component;
    String urlComplement = "";
    JSONObject jsonObject;
    JSONArray jsonArray = new JSONArray();
    ImageButton plusButton;
    ImageButton deleteButton;
    TextView title;
    Button okButton,webInfo;
    EditText valDsName;

    ArrayList<String> names = new ArrayList<>();

   public void createDatastore(View view){

       okButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               String url = "http://192.168.201.21:8081/addDs";
               RequestQueue queue = Volley.newRequestQueue(listViewActivity2.this);
               StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                       Toast.makeText(getApplicationContext(), "request sent! ", Toast.LENGTH_LONG).show();

                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(), "target unreachable ! ", Toast.LENGTH_LONG).show();
                       Log.i("error ", "" + error);
                   }
               }) {
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {

                       String valds = valDsName.getText().toString();
                       Map<String, String> map = new HashMap<String, String>();

                       map.put("dst_name", valds);

                       return map;

                   }
               };
               request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                       DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

               queue.add(request);

               valDsName.setAlpha(0);
               okButton.setAlpha(0);
               okButton.setEnabled(false);


           }
       });

   }


    public void addVirtualMachine(View view){

         switch(title.getText().toString()) {
             case "VIRTUAL MACHINES":
                 Intent intent = new Intent(getApplicationContext(), createVmActivity.class);
                 intent.putExtra("title", title.getText().toString());
                 startActivity(intent);
                 break;

             case "DATASTORES":
                 okButton.setAlpha(1);
                 okButton.setEnabled(true);
                 valDsName.setAlpha(1);
                 createDatastore(view);
                 break;

         }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);


        okButton = findViewById(R.id.okButton);
        valDsName = findViewById(R.id.dsValEditText);
        webInfo = findViewById(R.id.webInfo);

        webInfo.setAlpha(0);
        webInfo.setEnabled(false);
        okButton.setEnabled(false);
        valDsName.setAlpha(0);
        okButton.setAlpha(0);

        title = (TextView) findViewById(R.id.titleComponentTextView);

        plusButton = (ImageButton) findViewById(R.id.plusButton);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton);

        myListView = (ListView) findViewById(R.id.targetListView);

        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        component = new ArrayList<>();

        Bundle extras = getIntent().getExtras();

        final int value = extras.getInt("position");

        if (value == 0) {
                urlComplement = "Host";
                plusButton.setAlpha((float) 0);
                plusButton.setEnabled(false);
                deleteButton.setAlpha((float) 0);
                deleteButton.setEnabled(false);

            } else {
                if (value == 1) urlComplement = "Guest";
                else {
                    if (value == 2) {
                        urlComplement = "DataStore";
                    }


                    else {
                        urlComplement = "Networking";
                        plusButton.setAlpha((float) 0);
                        plusButton.setEnabled(false);
                        deleteButton.setAlpha((float) 0);
                        deleteButton.setEnabled(false);
                    }
                }
            }

//     String monip = InetAddress.getLocalHost().toString();
        final String url = "http://192.168.201.21:8081/get" + urlComplement + "Info";

        RequestQueue queue = Volley.newRequestQueue(listViewActivity2.this);


    //    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, component);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout. simple_list_item_multiple_choice, component);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                         jsonObject = new JSONObject(response);
                        //  JSONArray jsonArray ;

                        switch (Integer.toString(value)) {
                            case "0":
                                title.setText("HOSTS");
                                String hostName = jsonObject.getString("name");
                                component.add(hostName);
                                arrayAdapter.notifyDataSetChanged();
                                webInfo.setAlpha(1);
                                webInfo.setEnabled(true);
                                webInfo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(), getWebInfoActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                break;
                            case "1":
                                title.setText("VIRTUAL MACHINES");
                                jsonArray = jsonObject.getJSONArray("VMs");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String name = jsonArray.getJSONObject(i).getString("VM name");
                                    component.add(name);
                                }
                                arrayAdapter.notifyDataSetChanged();
                                break;
                            case "2":
                                title.setText("DATASTORES");
                                jsonArray = jsonObject.getJSONArray("Datastores");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String datastoreName = jsonArray.getJSONObject(i).getString("DatastoreName");
                                    component.add(datastoreName);
                                }


                                arrayAdapter.notifyDataSetChanged();
                                    break;
                            default:
                                title.setText("NETWORKING");
                                component.add("Networking");
                                arrayAdapter.notifyDataSetChanged();
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(listViewActivity2.this, "error ! :" + error, Toast.LENGTH_LONG);
                    Log.i("error ", "" + error);
                }
            });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

         queue.add(request);



         //arrayAdapter.notifyDataSetChanged();

         myListView.setAdapter(arrayAdapter);



        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SparseBooleanArray checkedItemPositions = myListView.getCheckedItemPositions();
                int itemCount = myListView.getCount();

                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){

                        names.add(component.get(i));
                        arrayAdapter.remove(component.get(i));

                    }
                }
                checkedItemPositions.clear();
                arrayAdapter.notifyDataSetChanged();

                Log.i("title",title.getText().toString());
                switch(title.getText().toString()){

                    case "VIRTUAL MACHINES":

                        String url = "http://192.168.201.21:8081/removeVm";
                        RequestQueue queue = Volley.newRequestQueue(listViewActivity2.this);
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                //    int count = arrayAdapter.getCount();

                                Toast.makeText(getApplicationContext(), "removed Succefully !", Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "target unreachable ! ", Toast.LENGTH_LONG).show();
                                Log.i("error ", "" + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                for (int i =0;i<names.size();i++) {
                                    map.put("vm_name"+i, names.get(i));
                                }
                                return map;
                            }
                        };
                        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        queue.add(request);

                        break;
                    case "DATASTORES":

                        String url2 = "http://192.168.201.21:8081/removeDs";
                        RequestQueue queue2 = Volley.newRequestQueue(listViewActivity2.this);
                        StringRequest request2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                //    int count = arrayAdapter.getCount();

                                Toast.makeText(getApplicationContext(), "removed Succefully !", Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "target unreachable ! ", Toast.LENGTH_LONG).show();
                                Log.i("error ", "" + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<String, String>();
                                for (int i =0;i<names.size();i++) {
                                    map.put("ds_name"+i, names.get(i));
                                }
                                return map;
                            }
                        };
                        request2.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                        queue2.add(request2);

                        break;
                }




            }
        });



        component.clear();


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                   Toast.makeText(listViewActivity2.this, component.get(position).toString(), Toast.LENGTH_LONG).show();

                    RequestQueue queue = Volley.newRequestQueue(listViewActivity2.this);

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                String message = "";

                                //     JSONObject jsonObject = new JSONObject(response);
                                ArrayList<String> hostData = new ArrayList<>();
                                switch (urlComplement) {
                                    case "Host":
                                        Intent hostIntent = new Intent(getApplicationContext(), showHostInfos.class);


                                        String version = jsonObject.getString("version");
                                        String hostOsType = jsonObject.getString("osType");
                                        String licenseProductName = jsonObject.getString("licenseProductName");
                                        String fullname = jsonObject.getString("fullName");

                                        String cpuReservation = jsonObject.getString("cpuReservation");
                                        String cpuUsage = jsonObject.getString("cpuUsage");
                                        String memoryReservation = jsonObject.getString("memoryReservation");
                                        String memoryUsage = jsonObject.getString("memoryUsage");
                                        String hostdsCapacity = jsonObject.getString("hostdsCapacity");
                                        String hostdsFreeSpace = jsonObject.getString("hostdsFreeSpace");
                                        String date = jsonObject.getString("serverClock");
                                        String vendor = jsonObject.getString("vendor");
                                        String model = jsonObject.getString("model");
                                        String cpuModel = jsonObject.getString("cpuModel");
                                        String memory = jsonObject.getString("memorySize");
                                        String nbrDatastores = jsonObject.getString("nbrDatastores");
                                        String nbrVMs = jsonObject.getString("nbrVMs");

                                        String hostds = jsonObject.getString("hostds");
                                        String hostvms = jsonObject.getString("hostVms");
                                        String HostName = jsonObject.getString("HostName");
                                        String defaultGateway = jsonObject.getString("defaultGateway");
                                        String dnsAdress = jsonObject.getString("dnsAdress");
                                        String VnicDevice = jsonObject.getString("VnicDevice");

                                        String macAdress = jsonObject.getString("macAdress");
                                        String ipv4Adress = jsonObject.getString("ipv4Adress");
                                        String ipv6Adress = jsonObject.getString("ipv6Adress");

                                        String nbrVswitches = jsonObject.getString("nbrVswitches");
                                        String nbrPortGroups = jsonObject.getString("nbrPortGroups");
                                        String Vswitches = jsonObject.getString("vswitches");
                                        String PortGroups = jsonObject.getString("PortGroups");

                                        hostData.add(cpuReservation);
                                        hostData.add(cpuUsage);
                                        hostData.add(memoryReservation);
                                        hostData.add(memoryUsage);
                                        hostData.add(vendor);
                                        hostData.add(model);
                                        hostData.add(cpuModel);
                                        hostData.add(memory);
                                        hostData.add(nbrDatastores);
                                        hostData.add(nbrVMs);
                                        hostData.add(date);
                                        hostData.add(HostName);
                                        hostData.add(VnicDevice);
                                        hostData.add(macAdress);
                                        hostData.add(ipv4Adress);
                                        hostData.add(ipv6Adress);
                                        hostData.add(dnsAdress);
                                        hostData.add(defaultGateway);
                                        hostData.add(nbrPortGroups);
                                        hostData.add(nbrVswitches);
                                        hostData.add(PortGroups);
                                        hostData.add(Vswitches);
                                        hostData.add(hostvms);
                                        hostData.add(hostds);
                                        hostData.add(fullname);
                                        hostData.add(hostdsCapacity);
                                        hostData.add(hostdsFreeSpace);


                                        hostIntent.putExtra("hostData", hostData);


                                        startActivity(hostIntent);
                                        break;
                                    case "Guest":
                                        Intent intent = new Intent(getApplicationContext(), showInfos.class);
                                        ArrayList<String> data = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            if (component.get(position).toString() == jsonArray.getJSONObject(i).getString("VM name")) {

                                                String usedOnDatastore = jsonArray.getJSONObject(i).getString("VmUsageOnDatastoreUsed");
                                                String usedOnCpu = jsonArray.getJSONObject(i).getString("cpuAllocation");
                                                String limitOnCpu = jsonArray.getJSONObject(i).getString("cpuLimit");
                                                String usedOnMemory = jsonArray.getJSONObject(i).getString("memoryAllocGB");

                                                String name = jsonArray.getJSONObject(i).getString("VM name");
                                                String guestOS = jsonArray.getJSONObject(i).getString("GuestOS");
                                                String snapshot_supported = jsonArray.getJSONObject(i).getString("Multiple snapshot supported");
                                                String vmMemoryGb = jsonArray.getJSONObject(i).getString("MemoryGB");
                                                String guestId = jsonArray.getJSONObject(i).getString("guestId");
                                                String guestVersion = jsonArray.getJSONObject(i).getString("version");
                                                String guestState = jsonArray.getJSONObject(i).getString("guestState");
                                                String numCpu = jsonArray.getJSONObject(i).getString("numCPU");

                                                String backing = jsonArray.getJSONObject(i).getString("DiskFileName");
                                                String capacity = jsonArray.getJSONObject(i).getString("DiskCapacityGb");
                                                String thinProvisioned = jsonArray.getJSONObject(i).getString("DiskThinProvisioned");
                                                String mode = jsonArray.getJSONObject(i).getString("DiskMode");

                                                String adapterType = jsonArray.getJSONObject(i).getString("vEthernetAdapterType");
                                                String connected = jsonArray.getJSONObject(i).getString("vEthernetConnected");
                                                String adressType = jsonArray.getJSONObject(i).getString("vEthernetAdressType");
                                                String adressMac = jsonArray.getJSONObject(i).getString("vEthernetAdressMac");

                                                String videoCardSize = jsonArray.getJSONObject(i).getString("VmVideoCardRamSize");
                                                String displaysNbr = jsonArray.getJSONObject(i).getString("VmVideoCardNumDisplays");
                                                String renderer = jsonArray.getJSONObject(i).getString("VmVideoCardUsed3dRender");
                                                message += "VM name : " + name + "\r\n" + "Guest OS : " + guestOS + "\r\n" +
                                                        "Guest Id : " + guestId + "\r\n" + "Version : " + guestVersion + "\r\n" +
                                                        "Guest State : " + guestState + "\r\n" + "Multiple Snapshot Supported : "
                                                        + snapshot_supported + "\r\n" + "Memory (MB) : " + vmMemoryGb + "\r\n" +
                                                        "CPU : " + numCpu + " vCPUs";

                                                data.add(limitOnCpu);
                                                data.add(vmMemoryGb);
                                                data.add(capacity);

                                                data.add(name);
                                                data.add(guestOS);
                                                data.add(guestState);
                                                data.add(guestVersion);


                                                data.add(backing);
                                                data.add(capacity);
                                                data.add(thinProvisioned);
                                                data.add(mode);

                                                data.add(adapterType);
                                                data.add(connected);
                                                data.add(adressType);
                                                data.add(adressMac);

                                                data.add(videoCardSize);
                                                data.add(displaysNbr);
                                                data.add(renderer);

                                                data.add(usedOnCpu);
                                                data.add(usedOnMemory);
                                                data.add(usedOnDatastore);

                                            }
                                        }
                                        Log.i("My msg : ", message);
                                        intent.putExtra("data", data);
                                        startActivity(intent);
                                        break;
                                    case "DataStore":
                                        Intent datastoreIntent = new Intent(getApplicationContext(), showDatastoreInfos.class);
                                        ArrayList<String> datastoreData = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            if (component.get(position).toString() == jsonArray.getJSONObject(i).getString("DatastoreName")) {
                                                String datastoreName = jsonArray.getJSONObject(i).getString("DatastoreName");
                                                String datastoreType = jsonArray.getJSONObject(i).getString("DSType");
                                                String datastoreCapacity = jsonArray.getJSONObject(i).getString("TotalCapacity");
                                                String datastoreFreeSpace = jsonArray.getJSONObject(i).getString("FreeSpace");
                                                String datastoreUsedSpace = jsonArray.getJSONObject(i).getString("UsedSpace");
                                                String datastoreUuid = jsonArray.getJSONObject(i).getString("UUID");
                                                String datastoreLocation = jsonArray.getJSONObject(i).getString("Location");
                                                String datastoreNbrVms = jsonArray.getJSONObject(i).getString("NbrOfVm");
                                                String datastoreTag = jsonArray.getJSONObject(i).getString("DatastoreTag");

                                                datastoreData.add(datastoreName);
                                                datastoreData.add(datastoreType);
                                                datastoreData.add(datastoreCapacity);
                                                datastoreData.add(datastoreFreeSpace);
                                                datastoreData.add(datastoreUsedSpace);
                                                datastoreData.add(datastoreLocation);
                                                datastoreData.add(datastoreUuid);
                                                datastoreData.add(datastoreTag);
                                                datastoreData.add(datastoreNbrVms);

                                                message += " name : " + datastoreName + "\r\n" + " Type : " + datastoreType + "\r\n" + "Capacity (GB): " + datastoreCapacity + "\r\n" + "Free Space (GB) : " + datastoreFreeSpace;
                                                message += "\n\n----------------\n\n";
                                                Log.i("My msg : ", message);
                                            }
                                        }
                                        datastoreIntent.putExtra("datastoreData", datastoreData);
                                        startActivity(datastoreIntent);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(listViewActivity2.this, "error ! :" + error, Toast.LENGTH_LONG);
                            Log.i("error ", "" + error);
                        }
                    });

                    request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    queue.add(request);


            }
        });

  }
}
