package com.example.arant.olor_a_libro.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
import com.example.arant.olor_a_libro.Clases.ClaseUsuario;
import com.example.arant.olor_a_libro.Clases.ClaseUsuarioActividades;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrincipalActivitats extends AppCompatActivity {

    public boolean registrado;
    int posicionUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_activitats);

        /**
         * Intents:
         * obtenim els valor de les variables a tarvés del Intnets*/
        String nom = getIntent().getExtras().getString("nombre");
        registrado = getIntent().getExtras().getBoolean("registrado");
        posicionUsuario = getIntent().getExtras().getInt("usuario");
        final boolean apuntado = getIntent().getExtras().getBoolean("apuntado");

        /**
         * Actividades:
         *Es un ArrayList on hi haurà totes les activitats creades a l'aplicació d'escriptori*/
        ArrayList<ClaseActivitats> actividades = new ArrayList<ClaseActivitats>();

        /*omplim la llista amb el mètode activitatsJson*/
        actividades = activitatsJson(actividades);

        ClaseActivitats a = new ClaseActivitats();

        /*Busquem l'activitat que tingui el mateix nom de l'activitat que hem passat amb l'intent*/
        int i=0;
        boolean encontrado = false;
        while (!encontrado && i<actividades.size()){

            if (nom.equals(actividades.get(i).getNombre())){
                a = actividades.get(i);
                encontrado = true;
            }
            i++;
        }

        /******************************************************************************************
         * Views del XML
         *idetifiquem les Viewa amb el seu id corresponent*/
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView categoria = (TextView) findViewById(R.id.categoria);
        GridView temas = (GridView) findViewById(R.id.temas);
        TextView dataInicio = (TextView) findViewById(R.id.dataInicio);
        TextView dataFinal = (TextView) findViewById(R.id.dataFinal);
        TextView descripcion = (TextView) findViewById(R.id.descripcion);


        /*mirem quina categoria té l'activitat i en funció d'això posarem un text o l'altre */
        String category;
        if (!a.isCategorias()) {
            category = "Adulto";
        } else {
            category = "Infantil";
        }
        categoria.setText(category);

        /*Adapter personalitzat per a que esl temes es vegin de color blanc*/
        ArrayList<String> temes =  a.getTemas();
        temas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a.getTemas()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.white));
                return view;
            }
        });

        /*situem els valors de l'activitat a les Views corresponents*/
        nombre.setText(a.getNombre());
        String dataI = a.getFechaInicio().toString();
        String dataF = a.getFechaFinal().toString();
        dataI = convertirData(dataI);
        dataF = convertirData(dataF);
        dataInicio.setText(dataI);
        dataFinal.setText(dataF);
        descripcion.setText(a.getDescripcion());


        /*****************************************************************************************
         * Spinner:
         * que serveix per a escollir la llibreria*/

        Spinner spinner = (Spinner) findViewById(R.id.librerias);

        ArrayList<String> libreriasA = afegirDefault(a.getLlibrerias());

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter <String>(this, R.layout.spinner, libreriasA);

        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarBoton(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*****************************************************************************************
         * Boto apuntar-se:
         * botó que nomes es mostra quan hi ha una llibreria selecionada
         */

        Button apuntarse = (Button) findViewById(R.id.boton_apuntarse);

        final ClaseActivitats finalA = a;

        /*Si es clica a la botó: */
        apuntarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Si el usuario esta registrado*/
                if(registrado){

                    /*Si el usuario no esta registrado*/
                    if(!apuntado) {
                        Toast.makeText(PrincipalActivitats.this, "Apuntado/a correctamente.", Toast.LENGTH_SHORT).show();
                        ArrayList<ClaseUsuarioActividades> usuarios = new ArrayList<>();
                        llenarUsuarios(usuarios);
                        int posicionUsuario = getIntent().getExtras().getInt("usuario");

                        /*metode per apuntar a un usuari i pujar la seva puntuació*/
                        apuntarUsuario(finalA.getNombre(), usuarios, posicionUsuario);
                    }else
                    {
                        Toast.makeText(PrincipalActivitats.this, "Ya estás apuntado a esta actividad.", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(PrincipalActivitats.this, "Tienes que estar registrado para apuntarte.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /***********************************************************************************************
     * METODES:
     ***********************************************************************************************/

    /*mostrar boto*/
    public void mostrarBoton(int posicio){
        Button apuntarse = (Button) findViewById(R.id.boton_apuntarse);
        if (posicio == 0)                                                                           //si la posicó és 0 ("--Ninguna Selecionsda--") no mostrara el boto
        {
            apuntarse.setVisibility(View.GONE);
        }
        else{
            apuntarse.setVisibility(View.VISIBLE);
        }
    }

    /*afegir "ninguna selecionada"*/
    public ArrayList<String> afegirDefault(List<String> llibreries) {
        ArrayList<String> aux = new ArrayList<String>();                                            //afegim la "llibreria" "--Ninguna Selcionada--" perquè es mostri a l'Spinner
        String libreria = "--Ninguna Selecionada--";
        aux.add(libreria);
        for (int i = 0; i < llibreries.size(); i++) {
            libreria = llibreries.get(i);
            aux.add(libreria);
        }
        return aux;
    }

    /*JSON*/
    public ArrayList<ClaseActivitats> activitatsJson(ArrayList<ClaseActivitats> activitats) {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory()
                    + "/JSON_files/actividades.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String) jsonObject.get("nombre");
                String fechaInicio = (String) jsonObject.get("fechaInicio");
                String fechaFinal = (String) jsonObject.get("fechaFinal");
                boolean categorias = (boolean) jsonObject.get("categorias");


                JSONArray jsonArrayT = (JSONArray) jsonObject.get("temas");
                Iterator<String> iterator = jsonArrayT.iterator();
                ArrayList<String> temas = new ArrayList<>();
                while (iterator.hasNext()) {
                    temas.add(iterator.next());
                }
                String descripcion = (String) jsonObject.get("descripcion");

                JSONArray jsonArrayL = (JSONArray) jsonObject.get("librerias");
                Iterator<String> iterator1 = jsonArrayL.iterator();
                ArrayList<String> librerias = new ArrayList<>();
                while (iterator1.hasNext()) {
                    librerias.add(iterator1.next());
                }

                int inscritos = (int) (long) jsonObject.get("inscritos");

                ClaseActivitats a = new ClaseActivitats(nombre, fechaInicio, fechaFinal, categorias,
                        temas, descripcion, librerias, inscritos);
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

   public String convertirData(String data){
        char[] dataChar = new char[10];
        for (int i =0; i < 10; i++){
            dataChar[i] = data.charAt(i);
        }
        data = String.valueOf(dataChar);
        return data;
    }

    public void apuntarUsuario(String nombre, ArrayList<ClaseUsuarioActividades> usuarios, int usuario){

        usuarios.get(usuario).getActividades().add(nombre);
        int puntos = usuarios.get(usuario).getPuntuacion();
        puntos = (puntos + 20);
        usuarios.get(usuario).setPuntuacion(puntos);

        JSONArray jsonArray = new JSONArray();
        JSONObject obj;
        Iterator<ClaseUsuarioActividades> iteratorusuarios = usuarios.iterator();
        while (iteratorusuarios.hasNext()) {
            obj = new JSONObject();
            ClaseUsuarioActividades user = iteratorusuarios.next();
            obj.put("nombre", user.getNombre());
            obj.put("edad", user.getEdad());
            obj.put("codigopostal", user.getCodigopostal());
            obj.put("correo", user.getCorreo());
            obj.put("contraseña", user.getContraseña());
            obj.put("actividades", user.getActividades());
            obj.put("puntuacion",user.getPuntuacion());
            jsonArray.add(obj);
        }

        try {
            FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + "/JSON_files/usuarios.json");
            file.write(jsonArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

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

    /*MENU*/

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
                    Intent menu=new Intent(PrincipalActivitats.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Miperfiltoolbar:
                    Intent perfil = new Intent(PrincipalActivitats.this, PerfilUsuario.class);
                    perfil.putExtra("usuario", posicionUsuario);
                    perfil.putExtra("registrado", true);
                    startActivityForResult(perfil,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:

                    Intent myintent=new Intent(PrincipalActivitats.this,Librerias.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    myintent.putExtra("registrado", true);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:

                    Intent actividades=new Intent(PrincipalActivitats.this,MainActividades.class);
                    actividades.putExtra("registrado", true);
                    actividades.putExtra("usuario", posicionUsuario);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:

                    Intent redes=new Intent(PrincipalActivitats.this,DatosDeRed.class);
                    redes.putExtra("registrado", true);
                    redes.putExtra("usuario", posicionUsuario);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;
                case R.id.AtencionToolbar:

                    Intent atencion=new Intent(PrincipalActivitats.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", true);
                    atencion.putExtra("usuario", posicionUsuario);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;

                case R.id.Ranking:
                    Intent Ranking=new Intent(PrincipalActivitats.this,Ranking.class);
                    Ranking.putExtra("usuario", posicionUsuario);
                    Ranking.putExtra("registrado", true);
                    startActivityForResult(Ranking,0);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:

                    Intent Usuario=new Intent(PrincipalActivitats.this,olorAlibro.class);
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
                    Intent menu=new Intent(PrincipalActivitats.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;

                case R.id.Libreriastoolbar:
                    Intent myintent=new Intent(PrincipalActivitats.this,Librerias.class);
                    if (registrado) {
                        myintent.putExtra("usuario", posicionUsuario);
                    }
                    myintent.putExtra("registrado", registrado);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;

                case R.id.ActividadesToolbar:
                    Intent actividades=new Intent(PrincipalActivitats.this,MainActividades.class);
                    if (registrado) {

                        actividades.putExtra("usuario", posicionUsuario);
                    }
                    actividades.putExtra("registrado", registrado);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;

                case R.id.RedToolbar:
                    Intent redes=new Intent(PrincipalActivitats.this,DatosDeRed.class);
                    if (registrado) {
                        redes.putExtra("usuario", posicionUsuario);
                    }
                    redes.putExtra("registrado", registrado);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;

                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(PrincipalActivitats.this,AtencionAlCliente.class);
                    if (registrado) {

                        atencion.putExtra("usuario", posicionUsuario);
                    }
                    startActivityForResult(atencion,0);
                    atencion.putExtra("registrado", registrado);
                    devolver=true;
                    break;

                case R.id.UsuarioToolbar:
                    if (registrado) {
                        Intent Usuario=new Intent(PrincipalActivitats.this,olorAlibro.class);
                        startActivityForResult(Usuario,0);
                    }
                    else{
                        Intent Usuario=new Intent(PrincipalActivitats.this,Login.class);
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

