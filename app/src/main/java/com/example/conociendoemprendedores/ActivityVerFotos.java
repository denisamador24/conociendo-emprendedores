package com.example.conociendoemprendedores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.conociendoemprendedores.fragments.inicio.ConsulListUser;

public class ActivityVerFotos extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fotos);

        imageView = (ImageView) findViewById(R.id.imageView_VerFoto);

        int seleccion = getIntent().getExtras().getInt("seleccion");

        if(seleccion == 1){

            byte[] imageArray = Base64.decode(ConsulListUser.image, Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
            imageView.setImageBitmap(image);
        } else{
            imageView.setImageResource(R.mipmap.ic_launcher);
        }

    }

    @Override
    public void onBackPressed(){
        finish();
    }

}