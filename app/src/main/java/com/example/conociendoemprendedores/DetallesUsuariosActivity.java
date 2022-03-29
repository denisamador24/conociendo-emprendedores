package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.conociendoemprendedores.fragments.detallesusuarios.SectionPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class DetallesUsuariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuarios);

        String telefono = getIntent().getExtras().getString("telefono");
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager_DetallesUsuariosActivity);
        viewPager.setAdapter(sectionPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs_DetallesUsuariosActivity);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton button = findViewById(R.id.fab_llamar_DetallesUsuarioActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!telefono.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+telefono));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Este perfil no tiene numero de telefono", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}