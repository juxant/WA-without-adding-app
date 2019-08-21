package com.chatwithoutadding;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPaises extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Paises> paises;

    public AdapterPaises(Activity activity, ArrayList<Paises> paises) {
        this.activity = activity;
        this.paises = paises;
    }

    @Override
    public int getCount() {
        return paises.size();
    }

    @Override
    public Object getItem(int position) {
        return paises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_paises, null);
        }

        Paises dir = paises.get(position);

        TextView texto = (TextView) v.findViewById(R.id.texto);
        texto.setText(dir.getNombre());
        texto.setGravity(Gravity.CENTER);

        ImageView imagen = (ImageView) v.findViewById(R.id.imagen);
        imagen.setImageDrawable(dir.getImagen());

        return v;

    }
}
