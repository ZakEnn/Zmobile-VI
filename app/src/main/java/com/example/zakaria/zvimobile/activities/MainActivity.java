package com.example.zakaria.zvimobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zakaria.zvimobile.R;


import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView et_serverIp,et_username,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       et_serverIp = (EditText)findViewById(R.id.et_serverIP);
       et_username = (EditText)findViewById(R.id.et_username);
       et_password = (EditText)findViewById(R.id.et_password);

       final String msg="";

        Button button = (Button) findViewById(R.id.button);
        try {
           // String monip = InetAddress.getLocalHost().toString();
            final String url = "http://192.168.201.21:8081/";

             button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        final String serverIp = et_serverIp.getText().toString();
                        final String username = et_username.getText().toString();
                        final String password = et_password.getText().toString();


                    Log.i("ip : ",serverIp);
                    Log.i("user : ",username);
                    Log.i("pass :",password);



                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                          //  Log.i("my success ", response);
                            Toast.makeText(getApplicationContext(),"Connection established !",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), gridViewActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Connection Error ! :", Toast.LENGTH_LONG).show();
                            Log.i("error ",""+error);
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<String,String>();
                            map.put("ip_value",serverIp);
                            map.put("user_name",username);
                            map.put("pass",password);
                            return map;
                        }
                    };
                    queue.add(request);
                }

                });

        } catch (Exception e) {
            e.printStackTrace();
        }

}


}

