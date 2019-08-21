package com.chatwithoutadding;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Map;



public class TabGuardado extends Fragment {



    private ArrayList<Contactos> contactos;
    private AdapterContactos adapterContactos;
    private ListView listView;
    private SharedPreferences sharedPreferences;
    private EventBus eventBus = EventBus.getDefault();
    private OnFragmentInteractionListener mListener;
    private TextView vacio;

    public TabGuardado() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        contactos = new ArrayList<Contactos>();
        adapterContactos = new AdapterContactos(getActivity(),contactos);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            contactos.add(new Contactos(entry.getValue().toString(),getResources().getDrawable(android.R.drawable.ic_menu_send),getResources().getDrawable(android.R.drawable.ic_menu_delete)));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_guardado, container, false);

        listView = (ListView)v.findViewById(R.id.listview);

        vacio = (TextView)v.findViewById(R.id.vacio);
        listView.setEmptyView(vacio);

        listView.setAdapter(adapterContactos);



        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Subscribe
    public void guardar(Contactos c){

        contactos.add(new Contactos(sharedPreferences.getString(c.getNumeros()," "),getResources().getDrawable(android.R.drawable.ic_menu_send),getResources().getDrawable(android.R.drawable.ic_menu_delete)));
        adapterContactos.notifyDataSetChanged();

    }

    @Override
    public void onPause(){
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        eventBus.register(this);
    }
}
