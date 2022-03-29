package com.example.conociendoemprendedores.fragments.registrarse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.example.conociendoemprendedores.R;


public class FragmentRegistrarse3 extends Fragment{

    private EditText editTextUsername, editTextPassword;

    public FragmentRegistrarse3(){

    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = layoutInflater.inflate(R.layout.fragment_registrarse3, container, false);

        editTextUsername = (EditText) view.findViewById(R.id.fragment_registrarse_editTextUsername);
        editTextPassword = (EditText) view.findViewById(R.id.fragment_registrarse_editTextPassword);

        return view;
    }

    public String getUsername(){
        String username = editTextUsername.getText().toString();
        return username;
    }

    public String getPassword(){
        String password = editTextPassword.getText().toString();
        return password;
    }

    public boolean CamposValidos(){
        if (getUsername().length() >= 4 && getPassword().length() >= 8){
            return true;
        } else {
            return false;
        }
    }
}
