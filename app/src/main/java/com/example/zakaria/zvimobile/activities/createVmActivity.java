package com.example.zakaria.zvimobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class createVmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String guestid ="";
    public String datastoreNm ="";
    public String netNm ="";
    public String nbrcpu ="";
    public String vmcd ="";

    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter4;
    ArrayList<String> guestId = new ArrayList<String>();
    ArrayList<String> nbrCpu = new ArrayList<>();
    ArrayList<String> vmCd = new ArrayList<>();
    EditText diskSize;
    EditText memorySize;
    String memoryCapacity;
    String diskCapacity;

    public ArrayList<String> getdatastoreNames(){

        final String urlds = "http://192.168.201.21:8081/getHostInfo";
        final ArrayList<String> dsnames  = new ArrayList<>();
        final ArrayList<String> netnames  = new ArrayList<>();

        RequestQueue queueds = Volley.newRequestQueue(createVmActivity.this);
        StringRequest requestds = new StringRequest(Request.Method.GET, urlds, new Response.Listener<String>() {

            JSONArray jsonArray ;
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String netAdapterNames = jsonObject.getString("PortGroups");
                    String dtsrNames = jsonObject.getString("hostds");
                    memoryCapacity = jsonObject.getString("memorySize");
                    diskCapacity = jsonObject.getString("hostdsCapacity");

                    String[] dtsrNamesArray = dtsrNames.split("\\n     ");
                 //   jsonArray = jsonObject.getJSONArray("Datastores");
                    for (int i = 0; i < dtsrNamesArray.length ; i++) {
                     //  String dsName = jsonArray.getJSONObject(i).getString("DatastoreName");
                        dsnames.add(dtsrNamesArray[i]);
                        Log.i("namesDS :",dsnames.get(i));
                    }
                      //Datastores Names
                    Spinner dsSpinner = findViewById(R.id.datastoreSpinner);

                    adapter3 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dsnames);

                     // Specify the layout to use when the list of choices appears
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      // Apply the adapter to the spinner

                    dsSpinner.setAdapter(adapter3);
                    dsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           datastoreNm =  dsnames.get(position);
                           Log.i("dsnam--e : ",datastoreNm);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    String[] netAdapterNamesArray = netAdapterNames.split("\\n     ");
                    //   jsonArray = jsonObject.getJSONArray("Datastores");
                    for (int i = 0; i < netAdapterNamesArray.length ; i++) {
                        //  String dsName = jsonArray.getJSONObject(i).getString("DatastoreName");
                        netnames.add(netAdapterNamesArray[i]);
                        Log.i("namesDS :",netnames.get(i));
                    }
                    //Datastores Names
                    Spinner netSpinner = findViewById(R.id.networkAdapterSpinner);

                    adapter4 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, netnames);

                    // Specify the layout to use when the list of choices appears
                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner

                    netSpinner.setAdapter(adapter4);

                    netSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            netNm =  netnames.get(position);
                            Log.i("dsnam--e : ",netNm);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(createVmActivity.this, "error ! :" + error, Toast.LENGTH_LONG);
                Log.i("error ", "" + error);
            }
        });
        requestds.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

         queueds.add(requestds);

         return dsnames;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vm);



        memorySize = (EditText) findViewById(R.id.memorySizeValueEditText);
        diskSize = (EditText) findViewById(R.id.diskSizeValueEditText);

      getdatastoreNames();


        ArrayAdapter<String> adapter;
        Button button = (Button) findViewById(R.id.validateButton);
        final EditText vmName = (EditText) findViewById(R.id.nameEditText);
        Spinner guestOsSpinner = findViewById(R.id.guestOsSpinner);


        ArrayList<String> guestOs = new ArrayList<String>();
        guestOs.add("Ubuntu Linux (64-bit)");
        guestOs.add("Ubuntu Linux (32-bit)");
        guestOs.add("RedHat Fedora");
        guestOs.add("Debien GNU/Linux");
        guestOs.add("Microsoft Windows 7 ");
        guestOs.add("Microsoft Windows Vista ");
        guestOs.add("Microsoft Windows XP ");
        guestOs.add("Asianux Server 3");
        guestOs.add("SUSE Linux Enterprise 10 (32-bit)");

        guestId.add("ubuntu64Guest");
        guestId.add("ubuntuGuest");
        guestId.add("redhatGuest");
        guestId.add("debian4Guest");
        guestId.add("windows7Guest");
        guestId.add("winVista64Guest");
        guestId.add("winXPProGuest");
        guestId.add("asianux3Guest");
        guestId.add("sles10Guest");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guestOs);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        guestOsSpinner.setAdapter(adapter);

        guestOsSpinner.setOnItemSelectedListener(this);
//number of CPUS

        ArrayAdapter<String> adapter2;
        Spinner nbrCpuSpinner = findViewById(R.id.nbrCpuSpinner);

         nbrCpu = new ArrayList<String>();
        nbrCpu.add("1");
        nbrCpu.add("2");


        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nbrCpu);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nbrCpuSpinner.setAdapter(adapter2);
        nbrCpuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               nbrcpu = nbrCpu.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter5;
        Spinner vmCdSpinner = (Spinner)findViewById(R.id.vmCdSpinner);

        vmCd = new ArrayList<String>();
        vmCd.add("Périphérique hôte");
        vmCd.add("Fichier ISO");


        adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vmCd);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vmCdSpinner.setAdapter(adapter5);
        vmCdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vmcd = vmCd.get(position).intern();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         // Log.i("ds names ------ ", dsnames.get(0));
        try {

            // String monip = InetAddress.getLocalHost().toString();
            button.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    String url = "http://192.168.201.21:8081/createVm";
                    final String vmname = vmName.getText().toString();
                    final String memorysize = memorySize.getText().toString();
                    final String disksize = diskSize.getText().toString();

                    if (Long.parseLong(memorySize.getText().toString()) > (Float.parseFloat(memoryCapacity)) * 1024) {

                        memorySize.setText(Long.toString(Math.round(Float.parseFloat(memoryCapacity)) * 1024));
                        Toast.makeText(getApplicationContext(), "Memory size Not supported !"+("\ud83d\ude1f"), Toast.LENGTH_LONG).show();

                    } else if(vmname.isEmpty()){

                        Toast.makeText(getApplicationContext(), "Please give a Name to this VM ! " +("\ud83d\ude22"), Toast.LENGTH_LONG).show();

                    }
                    else {
                        if(Long.parseLong(diskSize.getText().toString()) > ((Float.parseFloat(diskCapacity) * 1024 )/3) ) {

                            diskSize.setText(Long.toString(Math.round(Float.parseFloat(diskCapacity) * 1024 /3)) );
                            Toast.makeText(getApplicationContext(), "Disk size Not supported !"+("\ud83d\ude1f"), Toast.LENGTH_LONG).show();

                        }
                   else if((Long.parseLong(memorySize.getText().toString())% 4) != 0 ){

                          memorySize.setText("");
                            Toast.makeText(getApplicationContext(), "Memory size must be multiple of 4 ! "+("\ud83d\udea8"), Toast.LENGTH_LONG).show();

                       }

                     else{
                            RequestQueue queue = Volley.newRequestQueue(createVmActivity.this);
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Creation Succeed !"+("\ud83d\ude01"), Toast.LENGTH_LONG).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "target unreachable ! "+("\ud83d\ude28"), Toast.LENGTH_LONG).show();
                                    Log.i("error ", "" + error);
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("vm_name", vmname);
                                    Log.i("vm_name", vmname);
                                    Log.i("guest_id", guestid);
                                    map.put("guest_id", guestid);
                                    map.put("disk_size", disksize);
                                    map.put("memory_size", memorysize);
                                    map.put("datastore_name", datastoreNm);
                                    map.put("nbr_Cpu", nbrcpu);
                                    map.put("vm_cd", vmcd);
                                    map.put("net_name", netNm);

                                    return map;
                                }
                            };
                            request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            queue.add(request);
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        guestid = guestId.get(position);
        Log.i("position ------------ :",Integer.toString(position));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
