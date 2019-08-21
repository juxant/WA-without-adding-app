package com.chatwithoutadding;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterContactos extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Contactos> contactos;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor miEditor;
    private ListView listView;

    public AdapterContactos(Activity activity, ArrayList<Contactos> contactos) {
        this.activity = activity;
        this.contactos = contactos;
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactos.get(position);
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
            v = inf.inflate(R.layout.item_contactos, null);
        }

        listView = (ListView)parent.findViewById(R.id.listview);

        TextView numeros = (TextView) v.findViewById(R.id.textocontacto);
        numeros.setText(contactos.get(position).getNumeros());

        ImageView enviar = (ImageView) v.findViewById(R.id.enviar);
        enviar.setImageDrawable(contactos.get(position).getEnviar());

        ImageView borrar = (ImageView) v.findViewById(R.id.borrar);
        borrar.setImageDrawable(contactos.get(position).getBorrar());

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
                dialogo.setMessage(R.string.dialogo);
                dialogo.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                        miEditor = sharedPreferences.edit();
                        miEditor.remove(contactos.get(listView.getPositionForView(v)).getNumeros());

                        miEditor.apply();

                        contactos.remove(listView.getPositionForView(v));

                        AdapterContactos.super.notifyDataSetChanged();
                    }
                });

                dialogo.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialogo.show();
                    }
                });



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + contactos.get(listView.getPositionForView(v)).getNumeros());
                Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
                activity.startActivity(intent);
            }
        });

        return v;

    }
}
