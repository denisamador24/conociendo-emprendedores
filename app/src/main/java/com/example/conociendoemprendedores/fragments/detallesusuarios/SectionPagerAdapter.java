package com.example.conociendoemprendedores.fragments.detallesusuarios;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private static String[] TAB_TITLES = new String[] {"Informacion","Ofrece"};
    private int idUsuario;

    public SectionPagerAdapter( FragmentManager fm) {
        super(fm);

        this.idUsuario = idUsuario;

    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return InformacionUsuariosFragment.newInstance();
        } else {
            return FotosUsuariosFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
