package com.example.arant.olor_a_libro.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arant.olor_a_libro.Adaptadores.AdaptadorActividades;
import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
import com.example.arant.olor_a_libro.Clases.ClaseMenu;
import com.example.arant.olor_a_libro.Clases.ClaseUsuario;
import com.example.arant.olor_a_libro.Clases.ClaseUsuarioActividades;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Main_Activity_Actividades_Usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main___actividades__usuario);
        final int posicionUsuario = getIntent().getExtras().getInt("usuario");

        /******************************************************************************************
         * USUARIOS
         *
         * Creem un ArrayList<Usuario> on hi guardarem totes les usuaris donats pel JSON
         * usuarios utilitzant el metode llenarUsuarios (explicació a la linia xx)
         */
        ArrayList<ClaseUsuarioActividades> usuarios = new ArrayList<ClaseUsuarioActividades>();

        llenarUsuarios(usuarios);

        /******************************************************************************************
         * ACTIVIDADES
         *
         * Creem un ArrayList<Actividad> on hi guardarem totes les activitats donades pel JSON
         * activiades utiltzem el metode llenarActividades (explicació a la linia xx)
         */

        ArrayList<ClaseActivitats> actividades = new ArrayList<ClaseActivitats>();
        llenarActividades(actividades);

        /**
         * compararActividades explicació del mètode a la Linia xx
         */
        actividades = compararActividades(posicionUsuario, usuarios, actividades);

        /**
         * Creem dos ArrayList<Actividad> per emmagatzemar les activivdades fetes i les apuntades
         */

        final ArrayList<ClaseActivitats> actividadesHechas = new ArrayList<ClaseActivitats>();
        final ArrayList<ClaseActivitats> actividadesApuntadas = new ArrayList<ClaseActivitats>();

        /**
         * explicacio del mètode a la Linia xx
         */
        diferenciaActivitats(actividades, actividadesApuntadas, actividadesHechas);

        /**
         * creem 2 adapters, un per a cada ArrayList<Actividad> (hechas i apuntadas)
         */
        final AdaptadorActividades adapterA = new AdaptadorActividades(this, actividadesApuntadas);
        final AdaptadorActividades adapterH = new AdaptadorActividades(this, actividadesHechas);

        /**
         * Busquem la llista per l'id
         */
        final ListView llista = (ListView) findViewById(R.id.llista);

        /**
         * coloquem l'adapterA perquè volem que al començar l'activity a la llista es mostrin les
         * activitata apuntades de l'usuari
         */
        llista.setAdapter(adapterA);

        /******************************************************************************************
         * TEXTVIEW
         * inicialitzem els TextView a final el posem final per poder-lo utilitzar després
         */
        final TextView hechas = findViewById(R.id.hechas);
        final TextView apuntadas = findViewById(R.id.apuntadas);

        /**
         * posem el fons de color groc del TextView apuntadas, d'aquesta forma lusuari podrà veure
         * quin delles dues opcions del botSwitch esta Selecionada (apuntadas o hechas). Com he dit
         * abans, iniciarem l'Activity amb apuntadas, ´ñes per aix+o que situem el fons d'apuntadas
         * de color groc
         */
        apuntadas.setBackgroundColor(Color.YELLOW);

        /******************************************************************************************
         * SWITCH
         *
         * inicialitzem el boto(switch) amnb id i el posem final per poder-lo utilitzar després
         */
        final Switch botoSwitch = (Switch)findViewById(R.id.boto);

        /**
         * quan es cliqui sobre el boto passara:
         */
        botoSwitch.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*si els boto és a la dreta (hechas)*/
                if (botoSwitch.isChecked())
                {
                    /*Col·locarem l'adapterH (llista hechas) a la llista (ListView)*/
                    llista.setAdapter(adapterH);

                    /*canvia a color groc el fons del TextView Hechas*/
                    hechas.setBackgroundColor(Color.YELLOW);

                    /*fa invisible el fons del TextView apuntadas*/
                    apuntadas.setBackgroundColor(0);
                }
                /*en cas contrari*/
                else{
                    /*Col·locarem l'adapterA (llista apuntadas) a la llista (ListView)*/
                    llista.setAdapter(adapterA);

                    /*canvia a color groc el fons del TextView Apuntadas*/
                    apuntadas.setBackgroundColor(Color.YELLOW);

                    /*fa invisible el fons del TextView Hechas*/
                    hechas.setBackgroundColor(0);
                }

            }
        });

        /**
         * Quan es clicqui sobre un element de la llista (ListView)
         */

        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent myintent = new Intent (Main_Activity_Actividades_Usuario.this, PrincipalActivitats.class);
                if (botoSwitch.isChecked()){
                    myintent.putExtra("nombre", actividadesHechas.get(position).getNombre());

                }
                else{
                    myintent.putExtra("nombre", actividadesApuntadas.get(position).getNombre());

                }
                myintent.putExtra("usuario", posicionUsuario);
                myintent.putExtra("registrado", true);
                myintent.putExtra("apuntado", true);
                startActivity(myintent);
            }
        });

    }

    /********************************************************************************************
     * METDODES:
     ********************************************************************************************/

    /**
     * Compara la FechaFinal amb la dada actual
     * @param dataActivitat -> Es una string que contes la data de l'activitat (FechaFinal)
     * @return apuntada -> boleea que indica si es apuntada o hecha
     */
    public boolean compararDates (String dataActivitat){
        boolean apuntada = true;

        /*creem un format de data igual al de dataActivitat*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        /*creem una variable auxiliar de tipus data*/
        Date dataActividad = new Date();

        /*provem*/
        try {
            /*a la variable aux creada anteriorment li passam dataActivitat que automaticmaent es
             * converteix en una variable de tipus data amb la forma -> yyyy-mm-dd
             */
            dataActividad = dateFormat.parse(dataActivitat);

            /*Creem una variable de tipus data que contindrà el valor actual*/
            Date dataActual = Calendar.getInstance().getTime();

            /*Convertim la variable DataActual en una String*/
            String dataActualS = dateFormat.format(dataActual);

            /*Passem dataActualS a Date (de nou eò amb el format -> yyyy-mm-dd*/
            dataActual = dateFormat.parse(dataActualS);

            /**
             * com que les dates tenen EL MATEIX format les podem comparar
             */

            /* Si la dacta actual és posterior a la data de l'activitat, al ser la FechaFinal, vol
             * dir que l'activitat s'ha acabat i pertant ja no és una apuntada, així doncs canviem
             * el boola a false (implica que la actividad es Hecha.
             */
            if (dataActual.after(dataActividad))
            {
                apuntada=false;
            }
        }
        /*en cas ue no funcioni*/
        catch (Exception e){
            Toast.makeText(Main_Activity_Actividades_Usuario.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return apuntada;
    }

    /*JSON*/
    /*omplir en un ArrayList els usuaris des de el JSON*/
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

    /*omplir les activitats en un nArrayList des de el JSON*/
    public void llenarActividades(ArrayList<ClaseActivitats> actividades){
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

                actividades.add(a);

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

    /*Omplir activitats que coincideixenin amb l'arrayList d'activitats*/
    public ArrayList<ClaseActivitats> compararActividades (int poseUsuario,                   //usuario
                                                     ArrayList<ClaseUsuarioActividades> usuarios         //arrayListUsuarios
            , ArrayList<ClaseActivitats> actividades) //todasActividades
    {
        /*creamos arrayList auxiliar para almacenar nuevas actividades*/
        ArrayList<ClaseActivitats> nuevasActividades = new ArrayList<ClaseActivitats>();

        /*obtenemos el numero de actividades que tiene el usuario*/
        int numeroActividades = usuarios.get(poseUsuario).getActividades().size();

        /*ejecutamos el for tantas veces como actividades tenga el usuario*/
        for (int i = 0; i < numeroActividades; i++ ){

            /*cogemos el nombre de la activitat que esta en la posicion i del ususaio*/
            String nombreActvidad = usuarios.get(poseUsuario).getActividades().get(i).toString();

            /*ejecutamos el for tantas veces como actividades tengamos*/
            for (int j = 0; j < actividades.size(); j++ ){

                /*si el nombre de la actividad es igual al nombre de la actividad en la posision j*/
                if (nombreActvidad.equals(actividades.get(j).getNombre())){

                    /*creamos nueva actividad en base a la actividad que hay en la posicion j del ArrayList*/
                    ClaseActivitats a = actividades.get(j);

                    /*añadimos l actividad a el ArrayList*/
                    nuevasActividades.add(a);
                }
            }
        }

        /*Devolvemos el ArrayList con las actividades*/
        return nuevasActividades;
    }

    /**
     * Aquest metode serveixz per a dieferenciar les activitats entre fete o apuntades en funció de
     * la FechaFinal
     * @param activtats
     */
    public void diferenciaActivitats(ArrayList<ClaseActivitats> activtats, ArrayList<ClaseActivitats> apuntadas, ArrayList<ClaseActivitats> hechas){
        for (int i=0; i<activtats.size(); i++){

            if(compararDates(activtats.get(i).getFechaFinal())){
                apuntadas.add(activtats.get(i));
            }
            else {
                hechas.add(activtats.get(i));
            }
        }

    }
    }

