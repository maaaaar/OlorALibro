package com.example.arant.olor_a_libro.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class PerfilUsuario extends AppCompatActivity {
    EditText nombre, edad, codigo_postal, correo, contraseña;
    Button editar;
    public int posicionUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        /*Intent*/
        posicionUsuario = getIntent().getExtras().getInt("usuario");


        /*Arraylist*/
        final ArrayList<ClaseUsuarioActividades> usuarios = new ArrayList<ClaseUsuarioActividades>();

        llenarUsuarios(usuarios);


        editar = findViewById(R.id.buttonEditar);

        nombre = findViewById(R.id.editTextNombre);
        edad = findViewById(R.id.editTextEdad);
        codigo_postal = findViewById(R.id.editTextCodigoPostal);
        correo = findViewById(R.id.editTextCorreo);
        contraseña = findViewById(R.id.editTextContraseña);
        TextView puntuacion = findViewById(R.id.textViewPuntuacion);
        //
        nombre.setEnabled(false);
        edad.setEnabled(false);
        codigo_postal.setEnabled(false);
        correo.setEnabled(false);
        contraseña.setEnabled(false);
        /*String correo_user2 = "";*/



        JSONParser parser = new JSONParser();
        try
        {
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory()
                    + "/JSON_files/usuarios.json"));

            JSONArray jsonArray = (JSONArray) obj;

            JSONObject jsonObject = (JSONObject) jsonArray.get(posicionUsuario);

            String nombree = (String) jsonObject.get("nombre");
            int edadd = (int) (long) jsonObject.get("edad");
            int codigopostal = (int) (long) jsonObject.get("codigopostal");
            String correoo = (String) jsonObject.get("correo");
            String contraseñaa = (String) jsonObject.get("contraseña");
            int puntos = (int)(long) jsonObject.get("puntuacion");
            nombre.setText(nombree);
            edad.setText(Integer.toString(edadd));
            codigo_postal.setText(Integer.toString(codigopostal));
            correo.setText(correoo);
            contraseña.setText(contraseñaa);
            puntuacion.setText(Integer.toString(puntos));

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


        /**
         * Evento que se inicia al clicar el botón 'EDITAR'
         */
        editar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * Si al hacer clic se encuentra el texto inicial, pondrá 'Editar datos'
                 */
                if (editar.getText() == "Editar datos")
                {
                    /**
                     * entonces haremos que el usuario pueda editar los EditText
                     */
                    nombre.setEnabled(true);
                    edad.setEnabled(true);
                    codigo_postal.setEnabled(true);
                    correo.setEnabled(true);
                    contraseña.setEnabled(true);
                    /**
                     * y cambiaremos el texto del botón a 'Guardar datos'.
                     */
                    editar.setText("Guardar datos");
                }
                /**
                 * Una vez editados los datos, pondrá 'Guardar datos'
                 */
                else
                {
                    /**
                     * Al hacer clic haremos que el usuario ya no pueda
                     * editar los EditText.
                     */
                    nombre.setEnabled(false);
                    edad.setEnabled(false);
                    codigo_postal.setEnabled(false);
                    correo.setEnabled(false);
                    contraseña.setEnabled(false);
                    editar.setText("Editar datos");
                    /**
                     * Y si los EditText NO están vacíos, cogeremos esos nuevos datos y
                     * se los cambiaremos al usuario.
                     */
                    if(!(nombre.getText().toString().isEmpty()
                            || edad.getText().toString().isEmpty() || codigo_postal.getText().toString().isEmpty() || correo.getText().toString().isEmpty()
                            || contraseña.getText().toString().isEmpty())) {


                        usuarios.get(posicionUsuario).setNombre(nombre.getText().toString());
                        usuarios.get(posicionUsuario).setEdad(Integer.parseInt(edad.getText().toString()));
                        usuarios.get(posicionUsuario).setCodigopostal(Integer.parseInt(codigo_postal.getText().toString()));
                        usuarios.get(posicionUsuario).setCorreo(correo.getText().toString());
                        usuarios.get(posicionUsuario).setContraseña(contraseña.getText().toString());

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
                            obj.put("puntuacion", user.getPuntuacion());
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
                    else {
                        Toast.makeText(getApplicationContext(), "Faltan datos por rellenar.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button boto = (Button) findViewById(R.id.buttonActividades);

        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(v.getContext(),Main_Activity_Actividades_Usuario.class);
                myintent.putExtra("usuario", posicionUsuario);
                startActivityForResult(myintent,0);
            }
        });



    }
    //Creamos un booleano del menu del action bar

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    // Creamos otro boolean con las opciones del menu.
    //En cada caso le decimos a donde queremos que nos mande cuando clickemos
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean devolver;
        switch (item.getItemId()) {
            case R.id.MenuOlorAlibro:
                Intent menu=new Intent(PerfilUsuario.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(PerfilUsuario.this,Librerias.class);
                myintent.putExtra("usuario", posicionUsuario);
                myintent.putExtra("registrado", true);

                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(PerfilUsuario.this,MainActividades.class);
                actividades.putExtra("registrado", true);
                actividades.putExtra("usuario", posicionUsuario);
                startActivityForResult(actividades,0);
                devolver=true;
                break;
            case R.id.RedToolbar:

                Intent redes=new Intent(PerfilUsuario.this,DatosDeRed.class);
                redes.putExtra("registrado", true);
                redes.putExtra("usuario", posicionUsuario);
                startActivityForResult(redes,0);
                devolver=true;
                break;
            case R.id.AtencionToolbar:

                Intent atencion=new Intent(PerfilUsuario.this,AtencionAlCliente.class);
                atencion.putExtra("registrado", true);
                atencion.putExtra("usuario", posicionUsuario);
                startActivityForResult(atencion,0);
                devolver=true;
                break;

            case R.id.Ranking:
                Intent Ranking=new Intent(PerfilUsuario.this,Ranking.class);
                Ranking.putExtra("usuario", posicionUsuario);
                Ranking.putExtra("registrado", true);
                startActivityForResult(Ranking,0);
                devolver=true;
                break;

            case R.id.UsuarioToolbar:

                Intent Usuario=new Intent(PerfilUsuario.this,Login.class);
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
}
