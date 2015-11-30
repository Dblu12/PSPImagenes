package com.example.david.pspimagenes;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> lista= new ArrayList<>();
    private EditText et;
    private ListView lv;
    private Adaptador a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView) findViewById(R.id.listView);
        et= (EditText) findViewById(R.id.editText);

        init();
    }

    class Tarea extends AsyncTask<String, Integer,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> baf=new ArrayList<>();
            System.out.println("a");
            try{
                URL url = new URL(params[0]);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream(), "UTF-8"));
                String inputLine;
                System.out.println("b");
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println("c");
                    if (inputLine.contains("<img") && inputLine.contains("src=\"")) {
                        inputLine = inputLine.trim();

                        String subLine= inputLine.substring(inputLine.indexOf("src=\""), inputLine.lastIndexOf("\""));

                        subLine= subLine.substring(5, subLine.length());

                        subLine= subLine.substring(0, subLine.indexOf("\""));

                        if(!subLine.contains("http")){
                            subLine= "http:" + subLine;
                        }
                        baf.add(subLine);
//                        Log.v("INTERNET", subLine.substring(1,inputLine.indexOf("\"")));

                        System.out.println("d");
                    }
                }
                in.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baf;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            System.out.println("e");
            a= new Adaptador(MainActivity.this, R.layout.item, s);
            lv.setAdapter(a);
        }
    }

    private void init(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view.findViewById(R.id.tvImagen);
                Intent i= new Intent(MainActivity.this, Main2Activity.class);
                Bundle bun= new Bundle();
                bun.putString("url", tv.getTag()+"");
                i.putExtras(bun);
                startActivity(i);
            }
        });
    }

    public void buscar(View v){
        Tarea t= new Tarea();
        t.execute(new String[]{et.getText().toString()});
    }
}
