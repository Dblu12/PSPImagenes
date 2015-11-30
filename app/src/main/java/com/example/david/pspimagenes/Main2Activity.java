package com.example.david.pspimagenes;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class Main2Activity extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv= (ImageView) findViewById(R.id.ivImagen);

        System.out.println(getIntent().getExtras().getString("url"));
        Tarea2 t= new Tarea2();
        t.execute(new String[]{getIntent().getExtras().getString("url")});
    }

    class Tarea2 extends AsyncTask<String, Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                InputStream in = url.openStream();
                OutputStream out = new FileOutputStream(getFilesDir() + "/imagen.jpg");
                byte[] buffer = new byte[2048];
                int longitud;
                while((longitud = in.read(buffer))!=-1){
                    out.write(buffer,0,longitud);
                }
                out.close();
                in.close();
            }catch(MalformedURLException ex){
                return null;
            }catch(IOException ex){
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                InputStream in= new FileInputStream(getFilesDir() + "/imagen.jpg");
                Uri uri= Uri.parse(getFilesDir() + "/imagen.jpg");
                iv.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
