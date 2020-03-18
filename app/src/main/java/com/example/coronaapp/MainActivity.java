package com.example.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=Chile")
                .addHeader("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "c5a5521db8mshfe954d5f5887fbfp1a56e8jsn7066f90adde4")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject  json = new JSONObject(myResponse);
                                JSONArray lista = new JSONArray();
                                lista = json.getJSONObject("data").getJSONArray("covid19Stats");

                                ArrayList<String> array = new ArrayList<>();
                                if (lista !=null){
                                    for (int i = 0; i<lista.length(); i++){
                                        array.add(lista.getString(i));
                                    }
                                }
                                String asd = array.get(0);

                                String [] parts = asd.split(",");

                                String [] country = parts[1].split(":");
                                String [] country2 = country[1].split(" ");
                                String country3 = country2[0].replace("\""," ");

                                String [] lastupdate = parts[2].split("T");
                                String [] fecha = lastupdate[0].split(":");
                                String fechalimpia = fecha[1].replace("\"","");
                                String horalimpia = lastupdate[1].replace("\"","");

                                String [] confirmed = parts[3].split(":");

                                String [] deaths = parts[4].split(":");

                                String [] recovered = parts[5].split(":");
                                String recovered2 = recovered[1].replace("}"," ");

                                //txt.setText(json.getJSONObject("data").getJSONArray("covid19Stats"));
                                txt.setText("País: "+country3);
                                txt2.setText("Casos confirmados: "+confirmed[1]);
                                txt3.setText("Muertes: "+deaths[1]);
                                txt4.setText("Recuperaciones: "+recovered2);
                                txt5.setText("Última Actualización: "+fechalimpia+" "+horalimpia);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
