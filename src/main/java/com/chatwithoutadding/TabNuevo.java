package com.chatwithoutadding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class TabNuevo extends Fragment {

    private Button boton;
    private EditText texto;
    private EditText textoPrefijo;
    private Spinner menuDesplegable;
    private TypedArray imagen;
    private String[] nombres;
    private ArrayList<Paises> paises;
    private int[] prefijo;
    private CheckBox checkBox;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private EventBus eventBus = EventBus.getDefault();
    private AdapterPaises adapterPaises;
    private TelephonyManager tm;
    private AdView mAdView;

    public TabNuevo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_nuevo, container, false);



        mAdView = (AdView) v.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        boton = (Button) v.findViewById(R.id.boton);
        texto = (EditText) v.findViewById(R.id.numeros);
        menuDesplegable = (Spinner) v.findViewById(R.id.spinner);
        textoPrefijo = (EditText) v.findViewById(R.id.textoPrefijo);
        checkBox = (CheckBox)v.findViewById(R.id.checkBox);
        paises = new ArrayList<Paises>();
        armado();
        adapterPaises = new AdapterPaises(getActivity(),paises);
        menuDesplegable.setAdapter(adapterPaises);
        tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

        for (int i=0;i<adapterPaises.getCount();i++){

            if(tm.getNetworkCountryIso().toUpperCase().equals(paises.get(i).getNombre())){
                menuDesplegable.setSelection(i);
                textoPrefijo.setSelection(textoPrefijo.getText().length());
            }
        }

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(checkBox.isChecked()){
                    String contacto = textoPrefijo.getText().toString() + texto.getText().toString();

                    SharedPreferences.Editor miEditor = sharedPreferences.edit();
                    miEditor.putString(contacto,contacto);
                    miEditor.apply();
                    eventBus.post(new Contactos(contacto,getResources().getDrawable(android.R.drawable.ic_menu_send),getResources().getDrawable(android.R.drawable.ic_menu_delete)));
                }

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + textoPrefijo.getText().toString() + texto.getText().toString());
                Intent intent  = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

            }
        });

        menuDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
                textoPrefijo.setText("" + paises.get(position).getPrefijo());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textoPrefijo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!textoPrefijo.getText().toString().equals("")) {

                    for (int i = 0; i < adapterPaises.getCount(); i++) {

                        if (Integer.parseInt(textoPrefijo.getText().toString()) == paises.get(i).getPrefijo()) {
                            menuDesplegable.setSelection(i);
                            textoPrefijo.setSelection(textoPrefijo.getText().length());
                        }
                    }
                }
            }
        });

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

    private void armado(){

        nombres = getResources().getStringArray(R.array.nombres);
        imagen = getResources().obtainTypedArray(R.array.paises);
        prefijo = getResources().getIntArray(R.array.prefijos);



        for (int i=0;i<nombres.length;i++){

            paises.add(new Paises(nombres[i],imagen.getDrawable(i),prefijo[i]));
        }
    }


}
