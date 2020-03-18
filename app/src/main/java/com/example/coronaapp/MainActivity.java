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
    TextView txt6;
    TextView txt7;
    TextView txt8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://coronavirus-19-api.herokuapp.com/countries/Chile")
                //.addHeader("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                //.addHeader("x-rapidapi-key", "c5a5521db8mshfe954d5f5887fbfp1a56e8jsn7066f90adde4")
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
                                String data = json.toString();
                                String [] parts = data.split(",");

                                String [] contry = parts[0].split(":");
                                String countrydef = contry[1].replace("\"","");
                                System.out.println("Pais: "+countrydef);

                                String [] cases = parts[1].split(":");
                                System.out.println("Casos: "+cases[1]);

                                String [] todayCases = parts[2].split(":");
                                System.out.println("Casos hoy "+todayCases[1]);

                                String [] deaths = parts[3].split(":");
                                System.out.println("Muertes "+deaths[1]);

                                String [] todayDeaths = parts[4].split(":");
                                System.out.println("Muertes hoy "+todayCases[1]);

                                String [] recovered = parts[5].split(":");
                                System.out.println("Recuperaciones "+recovered[1]);

                                String [] activos = parts[6].split(":");
                                System.out.println(activos[1]);

                                String [] criticos = parts[7].split(":");
                                String criticosdef = criticos[1].toString().replace("}","");
                                System.out.println("Criticos: "+criticosdef);

                                txt.setText("Pais: "+countrydef);
                                txt2.setText("Casos: "+cases[1]);
                                txt3.setText("Casos hoy: "+todayCases[1]);
                                txt4.setText("Fallecidos: "+deaths[1]);
                                txt5.setText("Fallecidos hoy: "+todayDeaths[1]);
                                txt6.setText("Recuperados: "+recovered[1]);
                                txt7.setText("Casos Activos: "+activos[1]);
                                txt8.setText("Casos Criticos: "+criticosdef);

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
