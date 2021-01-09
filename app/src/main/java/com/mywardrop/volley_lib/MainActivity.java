package com.mywardrop.volley_lib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {



    final static String TAG = "volley";
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "geting data......");
      //  callingSingleOutputVolley();
        callingArrayOutputVolley();

    }

    public void callingSingleOutputVolley()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("getting data");
        progressDialog.show();
        Log.d(TAG, "Loading......");
        //for perforing task on other thread and starting the json requst
        mRequestQueue = Volley.newRequestQueue(this);

        //Volley Json obejct for getting data from server
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://official-joke-api.appspot.com/random_joke", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //When a valid response is back from servier
                // @para respnse - contain all the result data
                try{
                    int id = response.getInt("id");
                    String joke_type = response.getString("type");
                    String joke_question = response.getString("setup");
                    String punchline = response.getString("punchline");
                    Log.d(TAG, id + " -- " + joke_type);
                    Log.d(TAG, joke_question);
                    Log.d(TAG,  punchline);
                    progressDialog.dismiss();
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"Internet is down , try again", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }
                Log.d(TAG, response+"");
            }
            //Impleted two anonymous classes
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // for error
                Log.d(TAG,error.toString());
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }


    public void callingArrayOutputVolley()
    {
        //for perforing task on other thread and starting the json requst
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("getting data");
        progressDialog.show();
        mRequestQueue = Volley.newRequestQueue(this);
        String url  = "https://official-joke-api.appspot.com/random_ten";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray arrayResponse)
            {
                try{
                    for(int i = 0 ; i <  arrayResponse.length() ; i++)
                    {
                        JSONObject response = arrayResponse.getJSONObject(i);
                        int id = response.getInt("id");
                        String joke_type = response.getString("type");
                        String joke_question = response.getString("setup");
                        String punchline = response.getString("punchline");
                        Log.d(TAG, id + " -- " + joke_type);
                        Log.d(TAG, joke_question);
                        Log.d(TAG, punchline);
                    }
                    progressDialog.dismiss();
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"Internet is down , try again", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }

                Log.d(TAG,arrayResponse.toString());
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,error.toString());
            }
        }
                );
        //Volley Json Arrray for getting data from server

                mRequestQueue.add(jsonArrayRequest);
    }
}