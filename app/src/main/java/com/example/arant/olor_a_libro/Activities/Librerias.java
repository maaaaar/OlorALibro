package com.example.arant.olor_a_libro.Activities;


import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arant.olor_a_libro.Adaptadores.AdaptadorLibrerias;
import com.example.arant.olor_a_libro.Clases.ClaseLibreria;

import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Librerias extends AppCompatActivity {
    //Declaramos una list view
    private ListView lista;

    boolean registrado;
    int posicionUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Le decimos de que xml cogemos los datos.
        setContentView(R.layout.listalibrerias);

        /*Intent de registrado*/
        registrado = getIntent().getExtras().getBoolean("registrado");
        posicionUsuario = getIntent().getExtras().getInt("usuario");
        //Llamamos a la listView
        lista=(ListView) findViewById(R.id.llista2);
        //Creamos una ArrayList de la ClaseLibreria para poder leer el JSON
        ArrayList<ClaseLibreria> librerias = readJSON2();
        //Declaramos el TextView y llamamos a cual nos referimos
        TextView texto=(TextView)findViewById(R.id.TextoTituloL1);
        //Declaramos el adaptador
        final AdaptadorLibrerias adapte=new AdaptadorLibrerias(this,librerias);
        //unimos el adaptador con la lista
        lista.setAdapter(adapte);
        //Hacemos el set para que cuando clickemos en alguna parte de la lista haga lo siguiente
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //   String textValue = (String)parent.getItemAtPosition(position);

                //Hacemos un intent para que cuando hagamos click en algun elemento de la lista nos lleve al elemento indicado
                Intent myintent=new Intent(Librerias.this,PrincipalLibrerias.class);
                //Cogemos la posicion
                ClaseLibreria libreriaas = adapte.getItem(position);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                myintent.putExtra("registrado", registrado);
                myintent.putExtra("sitio", libreriaas);
                myintent.putExtra("posicion",position);
                startActivityForResult(myintent,1);

            }
        });

    }
    public ArrayList<ClaseLibreria> readJSON2()
    {   //Cremos una arrayList de la clase libreria
        ArrayList<ClaseLibreria> librerias2=new ArrayList<>();


        JSONParser parser = new JSONParser();

        try
        {
            //Le indicamos de donde queremos que lea los json
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory() + "/JSON_files/Principallibrerias.json"));

            JSONArray jsonArray = (JSONArray) obj;
    /*Hacemos un for y declaramos el tipo y le decimos el nombre que tenemos en nuestra clase
    libreria al cual queremos que haga referencia y llene el array de json
     */

            for (int i=0; i<jsonArray.size(); i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String foto=(String)jsonObject.get("foto");
                String nombre = (String) jsonObject.get("nombre");
                String direccion = (String) jsonObject.get("direccion");
                int telefono = (int)((long) jsonObject.get("telefono"));
                String nombrecontacto = (String) jsonObject.get("nombreContacto");
                String descripcion = (String) jsonObject.get("descripcion");
                //constructor con los datos
                ClaseLibreria libreria = new ClaseLibreria(foto,nombre, direccion, telefono, nombrecontacto, descripcion);
                //llenamos el array.
                librerias2.add(libreria);
            }


        } catch (ParseException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return librerias2;
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
                    Intent menu=new Intent(Librerias.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Miperfiltoolbar:
                    Intent perfil = new Intent(Librerias.this, PerfilUsuario.class);
                    perfil.putExtra("usuario", posicionUsuario);
                    perfil.putExtra("registrado", true);
                    startActivityForResult(perfil,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:

                    Intent myintent=new Intent(Librerias.this,Librerias.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    myintent.putExtra("registrado", true);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:

                    Intent actividades=new Intent(Librerias.this,MainActividades.class);
                    actividades.putExtra("registrado", true);
                    actividades.putExtra("usuario", posicionUsuario);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:

                    Intent redes=new Intent(Librerias.this,DatosDeRed.class);
                    redes.putExtra("registrado", true);
                    redes.putExtra("usuario", posicionUsuario);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(Librerias.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", true);
                    atencion.putExtra("usuario", posicionUsuario);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.Ranking:
                    Intent Ranking=new Intent(Librerias.this,Ranking.class);
                    Ranking.putExtra("usuario", posicionUsuario);
                    Ranking.putExtra("registrado", true);
                    startActivityForResult(Ranking,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    Intent Usuario=new Intent(Librerias.this,olorAlibro.class);
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
                    Intent menu=new Intent(Librerias.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;

                case R.id.Libreriastoolbar:
                    Intent myintent=new Intent(Librerias.this,Librerias.class);
                    if (registrado) {
                        myintent.putExtra("usuario", posicionUsuario);
                    }
                    myintent.putExtra("registrado", registrado);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;

                case R.id.ActividadesToolbar:
                    Intent actividades=new Intent(Librerias.this,MainActividades.class);
                    if (registrado) {

                        actividades.putExtra("usuario", posicionUsuario);
                    }
                    actividades.putExtra("registrado", registrado);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;

                case R.id.RedToolbar:
                    Intent redes=new Intent(Librerias.this,DatosDeRed.class);
                    if (registrado) {
                        redes.putExtra("usuario", posicionUsuario);
                    }
                    redes.putExtra("registrado", registrado);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(Librerias.this,AtencionAlCliente.class);
                    if (registrado) {

                        atencion.putExtra("usuario", posicionUsuario);
                    }
                    atencion.putExtra("registrado", registrado);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    if (registrado) {
                        Intent Usuario=new Intent(Librerias.this,olorAlibro.class);
                        startActivityForResult(Usuario,0);
                    }
                    else{
                        Intent Usuario=new Intent(Librerias.this,Login.class);
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

