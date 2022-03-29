package com.example.conociendoemprendedores.fragments.informacion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.conociendoemprendedores.R;

public class FragmentInformacion extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_acerca_de, container, false);

        TextView textView = (TextView) root.findViewById(R.id.textViewAcercaDe);

        textView.setText(
                "Conociendo emprendedores es una aplicación gratuita" +
                        " desarrollada por estudiantes del Colegio Gaspar García Laviana" +
                        " del Municipio de Moyogalpa con el fin de facilitar una herramienta" +
                        " para publicar o descubrir productos y servicios así mejorando sus ventas y alcance.");

        return root;
    }
}