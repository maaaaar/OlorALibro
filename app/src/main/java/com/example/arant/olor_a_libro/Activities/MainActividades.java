package com.example.arant.olor_a_libro.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arant.olor_a_libro.Adaptadores.AdaptadorActividades;
import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActividades extends AppCompatActivity {

    /*creem una variable global*/
    public boolean registrado;
    int posicionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actividades);

        /**
         * Registrado:
         * agafem el boolea enviat per l'intent per a saber si esta resgitrat o no*/
        registrado = getIntent().getExtras().getBoolean("registrado");
        posicionUsuario = getIntent().getExtras().getInt("usuario");


        /**
         * Actividades:
         *Es un ArrayList on hi haurà totes les activitats creades a l'aplicació d'escriptori*/
        ArrayList<ClaseActivitats> actividades= new ArrayList<ClaseActivitats>();
        actividades = activitatsJson(actividades);

        ListView llista = (ListView) findViewById(R.id.llista);

        AdaptadorActividades adaptadorActividades = new AdaptadorActividades(this, actividades);

        llista.setAdapter(adaptadorActividades);
        /**
         * Spinner:
         * Ens serveix per a seleccionar la categoria d'activtats a mostrar(infantil, adulto)*/
        ArrayList<String> categories = new ArrayList<String>();

        categories.add("TODAS");
        categories.add("INFANTIL");
        categories.add("ADULTOS");


        final Spinner mySpinner = (Spinner) findViewById(R.id.mySpinner);

        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        mySpinner.setAdapter(adap);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<ClaseActivitats> actividades1= new ArrayList<ClaseActivitats>();
                actividades1 = activitatsJson(actividades1);
                actividades1 = omplirListView(actividades1, position);

                ListView llista = (ListView) findViewById(R.id.llista);

                AdaptadorActividades adaptadorActividades = new AdaptadorActividades(MainActividades.this, actividades1);

                llista.setAdapter(adaptadorActividades);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Quan es clica sobre qualsevol activitat de la llista passa:
         */
        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ClaseActivitats> actividades1= new ArrayList<ClaseActivitats>();
                actividades1 = activitatsJson(actividades1);
                actividades1 = omplirListView(actividades1, mySpinner.getSelectedItemPosition());

                /*Creacio intent*/
                Intent myintent = new Intent (MainActividades.this, PrincipalActivitats.class);

                /*Enviem el nom de l'intent*/
                myintent.putExtra("nombre",actividades1.get(position).getNombre());

                //si l'usuari esta registrat es passarà la posisició que ocupa l'usuari dintre l' AarrayList
                if (registrado)
                {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                //també passem els false i registrado
                myintent.putExtra("apuntado", false);
                myintent.putExtra("registrado", registrado);

                startActivity(myintent);
            }
        });
    }

    /***********************************************************************************************
     * METODES:
     ***********************************************************************************************/

    /**
     * omplirListView:
     * Utilitzem aquesta funció per a obtenir una llista auxiliar de ClaseActivitat. Diferenciant les
     * activitats en funció de si son infantils o per a adults, així doncs la llista que torni, serà
     * la que utilitzarem per a omplir la ListView amb l'ajuda de l'ActivitatAdapter*/
    public ArrayList<ClaseActivitats> omplirListView(ArrayList<ClaseActivitats> actividades, int position) {

        int i;
        ArrayList<ClaseActivitats> aux = new ArrayList<ClaseActivitats>();

        switch (position){
            case 0:
                aux = actividades;
                break;
            case 1:
                for (i = 0; i < actividades.size(); i++){
                    ClaseActivitats a = actividades.get(i);
                    if (a.isCategorias()){
                        aux.add(a);
                    }
                }
                break;
            case 2:
                for (i = 0; i < actividades.size(); i++){
                    ClaseActivitats a = actividades.get(i);
                    if (!a.isCategorias()){
                        aux.add(a);
                    }
                }
                break;
        }
        return aux;
    }

    /**
     * JSON
    */
    /*Omplir les actvitats a partir del fitxer actividades.json*/
    public ArrayList<ClaseActivitats> activitatsJson(ArrayList<ClaseActivitats> activitats){

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory()
                    + "/JSON_files/actividades.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++ ){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String)jsonObject.get("nombre");
                String fechaInicio = (String)jsonObject.get("fechaInicio");
                String fechaFinal = (String)jsonObject.get("fechaFinal");
                boolean categorias = (boolean)jsonObject.get("categorias");

                JSONArray jsonArrayT = (JSONArray)jsonObject.get("temas");
                Iterator<String> iterator = jsonArrayT.iterator();
                ArrayList<String> temas = new ArrayList<String>();
                while (iterator.hasNext()){
                    temas.add(iterator.next());
                }

                String descripcion = (String)jsonObject.get("descripcion");

                JSONArray jsonArrayL = (JSONArray)jsonObject.get("librerias");
                Iterator<String> iterator2 = jsonArrayL.iterator();

                ArrayList<String> librerias = new ArrayList<String>();
                while (iterator2.hasNext()){
                    librerias.add(iterator2.next());
                }

                int inscritos = (int)(long)jsonObject.get("inscritos");

                ClaseActivitats a = new ClaseActivitats(nombre, fechaInicio, fechaFinal, categorias, temas, descripcion, librerias, inscritos);
                activitats.add(a);
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
        return activitats;
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
                    Intent menu=new Intent(MainActividades.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Miperfiltoolbar:
                    Intent perfil = new Intent(MainActividades.this, PerfilUsuario.class);
                    perfil.putExtra("usuario", posicionUsuario);
                    perfil.putExtra("registrado", true);
                    startActivityForResult(perfil,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:

                    Intent myintent=new Intent(MainActividades.this,Librerias.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    myintent.putExtra("registrado", true);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:

                    Intent actividades=new Intent(MainActividades.this,MainActividades.class);
                    actividades.putExtra("registrado", true);
                    actividades.putExtra("usuario", posicionUsuario);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:

                    Intent redes=new Intent(MainActividades.this,DatosDeRed.class);
                    redes.putExtra("registrado", true);
                    redes.putExtra("usuario", posicionUsuario);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(MainActividades.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", true);
                    atencion.putExtra("usuario", posicionUsuario);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.Ranking:
                    Intent Ranking=new Intent(MainActividades.this,Ranking.class);
                    Ranking.putExtra("usuario", posicionUsuario);
                    Ranking.putExtra("registrado", true);
                    startActivityForResult(Ranking,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    Intent Usuario=new Intent(MainActividades.this,olorAlibro.class);
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
                    Intent menu=new Intent(MainActividades.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;

                case R.id.Libreriastoolbar:
                    Intent myintent=new Intent(MainActividades.this,Librerias.class);
                    if (registrado) {
                        myintent.putExtra("usuario", posicionUsuario);
                    }
                    myintent.putExtra("registrado", registrado);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;

                case R.id.ActividadesToolbar:
                    Intent actividades=new Intent(MainActividades.this,MainActividades.class);
                    if (registrado) {

                        actividades.putExtra("usuario", posicionUsuario);
                    }
                    actividades.putExtra("registrado", registrado);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;

                case R.id.RedToolbar:
                    Intent redes=new Intent(MainActividades.this,DatosDeRed.class);
                    if (registrado) {
                        redes.putExtra("usuario", posicionUsuario);
                    }
                    redes.putExtra("registrado", registrado);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(MainActividades.this,AtencionAlCliente.class);
                    if (registrado) {

                        atencion.putExtra("usuario", posicionUsuario);
                    }
                    atencion.putExtra("registrado", registrado);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    if (registrado) {
                        Intent Usuario=new Intent(MainActividades.this,olorAlibro.class);
                        startActivityForResult(Usuario,0);
                    }
                    else{
                        Intent Usuario=new Intent(MainActividades.this,Login.class);
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