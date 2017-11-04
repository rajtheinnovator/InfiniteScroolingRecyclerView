package com.enpassio.infinitescroolingrecyclerview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String next_url;
    RecyclerView mPopularMovieRecyclerView;
    LinearLayoutManager layoutManagerPopularMoviesPoster;

    RecyclerViewAdapter mPopularMoviesAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getItems("http://ec2-35-154-135-19.ap-south-1.compute.amazonaws.com:8001/api/reminders/");


        mPopularMovieRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManagerPopularMoviesPoster = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false);
            /* Optionally customize the position you want to default scroll to */
        layoutManagerPopularMoviesPoster.scrollToPosition(0);
            /* Attach layout manager to the RecyclerView */
        mPopularMovieRecyclerView.setLayoutManager(layoutManagerPopularMoviesPoster);
        mPopularMoviesAdapter = new RecyclerViewAdapter(MainActivity.this, new ArrayList<String>());
        mPopularMovieRecyclerView.setAdapter(mPopularMoviesAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getItems(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            next_url = jsonObject.getString("next");

                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            ArrayList<String> data = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");
                                data.add(id);
                                //Log.v("my_tag", "id is: " + id);
                            }

                            updateUI(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.i("response--", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");

                header.put("Authorization", "Token fe63a7b37e04515a4cba77d2960526a84d1a56da");

                return header;
            }
        };
        MyVolleySingleton.getInstance(MainActivity.this).getRequestQueue().add(stringRequest);
    }

    private void updateUI(ArrayList<String> data) {
        mPopularMoviesAdapter.setMovieData(data);
    }
}
