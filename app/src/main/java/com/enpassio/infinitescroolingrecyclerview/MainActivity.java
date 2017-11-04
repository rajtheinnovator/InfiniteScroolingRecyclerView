package com.enpassio.infinitescroolingrecyclerview;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getItems("http://ec2-35-154-135-19.ap-south-1.compute.amazonaws.com:8001/api/reminders/");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void getItems(String url) {
        System.out.println("getItemcalled");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(new Date()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.v("my_tag", "json response is: "+response);
                            JSONObject jsonObject=new JSONObject(response);

                            Log.v("my_tag", "json jsonObject is: "+jsonObject);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            Log.v("my_tag", "json array is: "+jsonArray);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.i("response--", String.valueOf(error));
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String>header=new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");

                 header.put("Authorization","Token fe63a7b37e04515a4cba77d2960526a84d1a56da");
                //header.put("Authorization","Token "+ "fe63a7b37e04515a4cba77d2960526a84d1a56da");

                // header.put("Content-Type", "application/x-www-form-urlencoded");

                return header;
            }
        } ;
        MyVolleySingleton.getInstance(MainActivity.this).getRequestQueue().add(stringRequest);
    }
}
