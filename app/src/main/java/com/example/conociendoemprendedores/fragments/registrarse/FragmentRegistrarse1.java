package com.example.conociendoemprendedores.fragments.registrarse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.conociendoemprendedores.R;

public class FragmentRegistrarse1 extends Fragment{

    private EditText editTextDireccion, editTextTelefono;
    private Spinner spinnerComunidades;

    public FragmentRegistrarse1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrase1, container, false);

        editTextTelefono = (EditText) view.findViewById(R.id.editText_telefono_fragmentRegistrarse);
        editTextDireccion = (EditText) view.findViewById(R.id.editText_direccion_fragmentgRegistrase);
        spinnerComunidades = (Spinner) view.findViewById(R.id.fragment_registrarse_spinnerComunidades);

        String[] comunidades = {
                "Selecciona una comunidad",
                "Moyogalpa",
                "La Paloma",
                "Esquipulas",
                "Los Ángeles",
                "Sacramento",
                "San Lazaro",
                "San Jose",
                "Las Cruzes",
                "La flor",
                "La concha",
                "San Marcos"
        };

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, comunidades);
        spinnerComunidades.setAdapter(adapter);
        return view;
    }

    public String getTelefono(){
        String telefono = editTextTelefono.getText().toString();
        return telefono;
    }

    public String getDireccion(){
        String direccion = editTextDireccion.getText().toString();
        return direccion;
    }

    public String getComunidad(){
        String comunidad = spinnerComunidades.getSelectedItem().toString();
        return comunidad;
    }

    public boolean CamposValidos(){
        if(getDireccion().equals("") || getComunidad().equals("Selecciona una comunidad") || getTelefono().equals("")){
            return false;
        } else {
            return true;
        }
    }
}
