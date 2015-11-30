package com.example.david.pspimagenes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 24/11/2015.
 */
public class Adaptador extends ArrayAdapter<String> {

    private int res;
    private LayoutInflater lInflator;
    private List<String> imagenes;
    private Context con;

    static class ViewHolder {

        public TextView tv;

    }


    public Adaptador(Context context, int resource, List<String> objects) {

        super(context, resource, objects);

        this.res = resource; // LAYOUT
        this.imagenes = objects; // LISTA DE VALORES
        this.con = context; // ACTIVIDAD

        lInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder gv = new ViewHolder();
        if (convertView == null) {
            convertView = lInflator.inflate(res, null);


            TextView tv = (TextView) convertView.findViewById(R.id.tvImagen);
            gv.tv = tv;


            convertView.setTag(gv);
        } else {
            gv = (ViewHolder) convertView.getTag();
        }

        gv.tv.setText(imagenes.get(position));

        //--

        gv.tv.setTag(imagenes.get(position));

        return convertView;
    }


}
