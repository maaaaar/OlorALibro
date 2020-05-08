package com.example.arant.olor_a_libro.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arant.olor_a_libro.Adaptadores.AdaptadorUsuariosRanking;
import com.example.arant.olor_a_libro.Clases.ClaseUsuarioActividades;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Ranking extends AppCompatActivity {

    private int posicionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        posicionUsuario = getIntent().getExtras().getInt("usuario");

        /*Intents*/
        int posicionUsuario = getIntent().getExtras().getInt("usuario");

        ArrayList<ClaseUsuarioActividades> usuarios = new  ArrayList<ClaseUsuarioActividades>();

        llenarUsuarios(usuarios);

        String correo = usuarios.get(posicionUsuario).getCorreo();

        Collections.sort(usuarios, new Comparator<ClaseUsuarioActividades>() {
            @Override
            public int compare(ClaseUsuarioActividades o1, ClaseUsuarioActividades o2) {
                return new Integer(o1.getPuntuacion()).compareTo(new Integer(o2.getPuntuacion()));
            }
        });

        Collections.reverse(usuarios);

        AdaptadorUsuariosRanking adaptadorUsuarios = new AdaptadorUsuariosRanking(this, usuarios,correo );

        ListView listView = (ListView) findViewById(R.id.ranking);

        listView.setAdapter(adaptadorUsuarios);


    }


    /**
     * Meotdes:
     */

    public void llenarUsuarios(ArrayList<ClaseUsuarioActividades> usuarios){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory()
                    + "/JSON_files/usuarios.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++ ){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String)jsonObject.get("nombre");
                int edad = (int)(long)jsonObject.get("edad");
                int codigoPostal = (int)(long)jsonObject.get("codigopostal");
                String correo = (String)jsonObject.get("correo");
                String contraseña = (String)jsonObject.get("contraseña");

                JSONArray jsonArrayA = (JSONArray)jsonObject.get("actividades");
                Iterator<String> iterator = jsonArrayA.iterator();
                ArrayList<String> actividades = new ArrayList<String>();
                while (iterator.hasNext()){
                    actividades.add(iterator.next());
                }
                int puntuacion = (int)(long) jsonObject.get("puntuacion");
                ClaseUsuarioActividades u = new ClaseUsuarioActividades(nombre, edad, codigoPostal, correo, contraseña, actividades, puntuacion);
                usuarios.add(u);
            }
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

    @Override
    //Creamos un booleano del menu del action bar

    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu2);
        return super.onCreateOptionsMenu(menu2);
    }

    @Override

    // Creamos otro boolean con las opciones del menu.
    //En cada caso le decimos a donde queremos que nos mande cuando clickemos
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean devolver;
        switch (item.getItemId()) {
            case R.id.MenuOlorAlibro:
                Intent menu=new Intent(Ranking.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Miperfiltoolbar:
                Intent perfil = new Intent(Ranking.this, PerfilUsuario.class);
                perfil.putExtra("usuario", posicionUsuario);
                perfil.putExtra("registrado", true);
                startActivityForResult(perfil,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(Ranking.this,Librerias.class);
                myintent.putExtra("usuario", posicionUsuario);
                myintent.putExtra("registrado", true);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(Ranking.this,MainActividades.class);
                actividades.putExtra("registrado", true);
                actividades.putExtra("usuario", posicionUsuario);
                startActivityForResult(actividades,0);
                devolver=true;
                break;
            case R.id.RedToolbar:

                Intent redes=new Intent(Ranking.this,DatosDeRed.class);
                redes.putExtra("registrado", true);
                redes.putExtra("usuario", posicionUsuario);
                startActivityForResult(redes,0);
                devolver=true;
                break;
            case R.id.AtencionToolbar:

                Intent atencion=new Intent(Ranking.this,AtencionAlCliente.class);
                atencion.putExtra("registrado", true);
                atencion.putExtra("usuario", posicionUsuario);
                startActivityForResult(atencion,0);
                devolver=true;
                break;

            case R.id.Ranking:
                Intent Ranking=new Intent(Ranking.this,Ranking.class);
                Ranking.putExtra("usuario", posicionUsuario);
                Ranking.putExtra("registrado", true);
                startActivityForResult(Ranking,0);
                devolver=true;
                break;

            case R.id.UsuarioToolbar:
                Intent Usuario=new Intent(Ranking.this,olorAlibro.class);
                Usuario.putExtra("registrado", true);
                Usuario.putExtra("usuario", posicionUsuario);
                startActivityForResult(Usuario,0);
                devolver=true;
                break;
            default:
                devolver=super.onOptionsItemSelected(item);
                break;

        }
        return devolver;
    }
}
