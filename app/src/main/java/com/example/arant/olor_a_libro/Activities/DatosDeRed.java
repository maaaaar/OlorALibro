package com.example.arant.olor_a_libro.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arant.olor_a_libro.R;
//import com.github.chrisbanes.photoview.PhotoView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatosDeRed extends AppCompatActivity {

    private TextView proyectoView;
    private TextView grupoView;
    public ViewImageExtended viewImageExtended;
    public Bitmap bitmap = null; // El bitmap de la imagen
    public FragmentActivity activity; // En el constructor del fragment pasas el activity como referencia this.activity = activity;
    public boolean registrado;
    int posicionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_de_red);

        /*Intent de registrado*/
        registrado = getIntent().getExtras().getBoolean("registrado");
        posicionUsuario = getIntent().getExtras().getInt("usuario");

        proyectoView = findViewById(R.id.Proyecto);


        ImageView marImage = findViewById(R.id.mar);
        ImageView arantxaImage = findViewById(R.id.arantxa);
        ImageView luisImage = findViewById(R.id.luis);
        ImageView martiImage = findViewById(R.id.marti);


        Bitmap bitmapMar = BitmapFactory.decodeResource(getResources(), R.mipmap.mar);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapMar);
        mDrawable.setCircular(true);
        marImage.setImageDrawable(mDrawable);

        Bitmap bitmapArantxa = BitmapFactory.decodeResource(getResources(), R.mipmap.arantxa);
        RoundedBitmapDrawable aDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapArantxa);
        aDrawable.setCircular(true);
        arantxaImage.setImageDrawable(aDrawable);

        Bitmap bitmapLuis = BitmapFactory.decodeResource(getResources(), R.mipmap.luis);
        RoundedBitmapDrawable lDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapLuis);
        lDrawable.setCircular(true);
        luisImage.setImageDrawable(lDrawable);

        Bitmap bitmapMarti = BitmapFactory.decodeResource(getResources(), R.mipmap.marti);
        RoundedBitmapDrawable miDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapMarti);
        miDrawable.setCircular(true);
        martiImage.setImageDrawable(miDrawable);

        /*marImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DatosDeRed.this);
                View mView = getLayoutInflater().inflate(R.layout.foto_mar, null);
                PhotoView photoView = mView.findViewById(R.id.marFoto);
                photoView.setImageResource(R.mipmap.margran);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        arantxaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DatosDeRed.this);
                View mView = getLayoutInflater().inflate(R.layout.foto_arantxa, null);
                PhotoView photoView = mView.findViewById(R.id.arantxaFoto);
                photoView.setImageResource(R.mipmap.arantxagran);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });
        luisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DatosDeRed.this);
                View mView = getLayoutInflater().inflate(R.layout.foto_luis, null);
                PhotoView photoView = mView.findViewById(R.id.luisFoto);
                photoView.setImageResource(R.mipmap.luisgran);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });
        martiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DatosDeRed.this);
                View mView = getLayoutInflater().inflate(R.layout.foto_marti, null);
                PhotoView photoView = mView.findViewById(R.id.martiFoto);
                photoView.setImageResource(R.mipmap.martigran);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });*/
        proyectoView = findViewById(R.id.Proyecto);
        grupoView = findViewById(R.id.Grupo);

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            } else {

            }
        } else {

        }
        readJSON(proyectoView, grupoView);
    }

    public void readJSON(TextView proyecto, TextView grupo) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory() + "/JSON_files/datos_de_red.json"));

            JSONObject jsonObject = (JSONObject) obj;

            String descProj = (String) jsonObject.get("proyecto");
            proyecto.setText(descProj);

            String descGrup = (String) jsonObject.get("grupo");
            grupo.setText(descGrup);

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    //Creamos un booleano del menu del action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (registrado){
            inflater.inflate(R.menu.menu2, menu);
        }else{
            inflater.inflate(R.menu.menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    // Creamos otro boolean con las opciones del menu.
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean devolver;

        if (registrado){
            switch (item.getItemId()) {
                case R.id.MenuOlorAlibro:
                    Intent menu=new Intent(DatosDeRed.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Miperfiltoolbar:
                    Intent perfil = new Intent(DatosDeRed.this, PerfilUsuario.class);
                    perfil.putExtra("usuario", posicionUsuario);
                    perfil.putExtra("registrado", true);
                    startActivityForResult(perfil,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:

                    Intent myintent=new Intent(DatosDeRed.this,Librerias.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    myintent.putExtra("registrado", true);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:

                    Intent actividades=new Intent(DatosDeRed.this,MainActividades.class);
                    actividades.putExtra("registrado", true);
                    actividades.putExtra("usuario", posicionUsuario);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:

                    Intent redes=new Intent(DatosDeRed.this,DatosDeRed.class);
                    redes.putExtra("registrado", true);
                    redes.putExtra("usuario", posicionUsuario);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;
                case R.id.AtencionToolbar:

                    Intent atencion=new Intent(DatosDeRed.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", true);
                    atencion.putExtra("usuario", posicionUsuario);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.Ranking:
                    Intent Ranking=new Intent(DatosDeRed.this,Ranking.class);
                    Ranking.putExtra("usuario", posicionUsuario);
                    Ranking.putExtra("registrado", true);
                    startActivityForResult(Ranking,0);
                    devolver=true;
                    break;
                case R.id.UsuarioToolbar:

                    Intent Usuario=new Intent(DatosDeRed.this,olorAlibro.class);
                    Usuario.putExtra("registrado", true);
                    Usuario.putExtra("usuario", posicionUsuario);
                    startActivityForResult(Usuario,0);
                    devolver=true;
                    break;
                default:
                    devolver=super.onOptionsItemSelected(item);
                    break;

            }
        }
        else {
            switch (item.getItemId()) {

                //En cada caso le decimos a donde queremos que nos mande cuando clickemos
                case R.id.MenuOlorAlibro:
                    Intent menu=new Intent(DatosDeRed.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;

                case R.id.Libreriastoolbar:
                    Intent myintent=new Intent(DatosDeRed.this,Librerias.class);
                    if (registrado) {
                        myintent.putExtra("usuario", posicionUsuario);
                    }
                    myintent.putExtra("registrado", registrado);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;

                case R.id.ActividadesToolbar:
                    Intent actividades=new Intent(DatosDeRed.this,MainActividades.class);
                    if (registrado) {

                        actividades.putExtra("usuario", posicionUsuario);
                    }
                    actividades.putExtra("registrado", registrado);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;

                case R.id.RedToolbar:
                    Intent redes=new Intent(DatosDeRed.this,DatosDeRed.class);
                    if (registrado) {
                        redes.putExtra("usuario", posicionUsuario);
                    }
                    redes.putExtra("registrado", registrado);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(DatosDeRed.this,AtencionAlCliente.class);
                    if (registrado) {

                        atencion.putExtra("usuario", posicionUsuario);
                    }
                    atencion.putExtra("registrado", registrado);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    if (registrado) {
                        Intent Usuario=new Intent(DatosDeRed.this,olorAlibro.class);
                        startActivityForResult(Usuario,0);
                    }
                    else{
                        Intent Usuario=new Intent(DatosDeRed.this,Login.class);
                        startActivityForResult(Usuario,0);
                    }

                    devolver=true;
                    break;
                default:
                    devolver=super.onOptionsItemSelected(item);
                    break;

            }
        }

        return devolver;
    }
}