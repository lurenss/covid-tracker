package com.example.mainpage;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;





public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        OkHttpClient client = new OkHttpClient();


        String url1 = "https://corona.lmao.ninja/v2/historical/italy";
        String url2 = "https://corona.lmao.ninja/countries/italy";

        Request request1 = new Request.Builder().url(url1).build();
        Request request2 = new Request.Builder().url(url2).build();

        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String res1 = response.body().string();
                    try {
                        JSONObject js1 =  new JSONObject(res1);
                        JSONObject js2 =  new JSONObject(res1);
                        js1 = js1.getJSONObject("timeline");
                        js1 = js1.getJSONObject("cases");
                        js2 = js2.getJSONObject("timeline");
                        js2 = js2.getJSONObject("deaths");


                        ArrayList cases = new ArrayList();
                        ArrayList deaths = new ArrayList();
                        Iterator<String> iter = js1.keys();

                        while (iter.hasNext()) {
                            String key = iter.next();
                            Object valcase = js1.get(key);
                            Object valdeath = js2.get(key);
                            cases.add(valcase);
                            deaths.add(valdeath);
                            Log.i("cases", String.valueOf(valcase));
                            Log.i("death", String.valueOf(valcase));
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    try{
                        String res2 = response.body().string();
                        JSONObject js2 =  new JSONObject(res2);
                        String todayCases =  js2.getString("todayCases");
                        String todayDeaths =  js2.getString("todayDeaths");
                        String recovered =  js2.getString("recovered");
                        Log.i("todayCases",todayCases);
                        Log.i("todayDeaths",todayDeaths);
                        Log.i("recovered",recovered);
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
