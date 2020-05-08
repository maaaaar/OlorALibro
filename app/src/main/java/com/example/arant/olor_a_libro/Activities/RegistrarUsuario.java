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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
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

public class RegistrarUsuario extends AppCompatActivity {
    private EditText nombre, edad, codigo_postal, correo, contraseña, repetir_contraseña;
    private Button btn_registro;
    private ProgressBar cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        nombre = findViewById(R.id.etNombre);
        edad = findViewById(R.id.etEdad);
        codigo_postal = findViewById(R.id.etCodigoPostal);
        correo = findViewById(R.id.etCorreo);
        contraseña = findViewById(R.id.etContraseña);
        repetir_contraseña = findViewById(R.id.etRepetirContraseña);

        cargando = findViewById(R.id.registroCargando);

        btn_registro = findViewById(R.id.buttonRegistro);
        final ArrayList<ClaseUsuarioActividades> usuarios = new ArrayList<ClaseUsuarioActividades>();
        /**
         * Evento que se inicia al hacer clic en el botón 'Registrarse'
         */
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Hacemos visible la barra de carga circular y escondemos el botón.
                 */
                cargando.setVisibility(View.VISIBLE);
                btn_registro.setVisibility(View.GONE);

                /**
                 * Si la contraseña y repetir contraseña y además todos los EditText
                 * no están vacíos pasamos a la siguiente condición.
                 */
                if (contraseña.getText().toString().equals(repetir_contraseña.getText().toString()) && !(nombre.getText().toString().isEmpty()
                        || edad.getText().toString().isEmpty() || codigo_postal.getText().toString().isEmpty() || correo.getText().toString().isEmpty()
                        || contraseña.getText().toString().isEmpty()))
                {
                    /**
                     * Le pasamos el ArrayList de usuarios y el correo introducido por el usuario.
                     * Si no existe ese correo, creamos el usuario y lo introducimos en el archivo JSON.
                     */
                    if (!readJSON2(correo.getText().toString(), usuarios)) {
                        ArrayList<ClaseActivitats> activitats = new ArrayList<ClaseActivitats>();

                        ClaseUsuarioActividades usu3 = new ClaseUsuarioActividades(nombre.getText().toString(), Integer.parseInt(edad.getText().toString()),
                                Integer.parseInt(codigo_postal.getText().toString()), correo.getText().toString(), contraseña.getText().toString(), activitats, 0);
                        usuarios.add(usu3);

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
                        Intent myintent = new Intent(RegistrarUsuario.this, olorAlibroRegistrado.class);
                        myintent.putExtra("usuario",(usuarios.size()-1));
                        startActivityForResult(myintent, 0);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Ya hay una cuenta con este correo.", Toast.LENGTH_LONG).show();
                        cargando.setVisibility(View.GONE);
                        btn_registro.setVisibility(View.VISIBLE);
                        correo.setText("");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos.", Toast.LENGTH_LONG).show();
                    cargando.setVisibility(View.GONE);
                    btn_registro.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    //Creamos un booleano del menu del action bar

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    // Creamos otro boolean con las opciones del menu.
    //En cada caso le decimos a donde queremos que nos mande cuando clickemos
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean devolver;
        switch (item.getItemId()) {
            case R.id.MenuOlorAlibro:
                Intent menu=new Intent(RegistrarUsuario.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(RegistrarUsuario.this,Librerias.class);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(RegistrarUsuario.this,MainActividades.class);
                actividades.putExtra("registrado", false);
                startActivityForResult(actividades,0);
                devolver=true;
                break;

            case R.id.RedToolbar:
                Intent redes=new Intent(RegistrarUsuario.this,DatosDeRed.class);
                startActivityForResult(redes,0);
                devolver=true;
                break;

            case R.id.AtencionToolbar:
                Intent atencion=new Intent(RegistrarUsuario.this,AtencionAlCliente.class);
                startActivityForResult(atencion,0);
                devolver=true;
                break;

            case R.id.UsuarioToolbar:
                Intent Usuario=new Intent(RegistrarUsuario.this,Login.class);
                startActivityForResult(Usuario,0);
                devolver=true;
                break;
            default:
                devolver=super.onOptionsItemSelected(item);
                break;

        }
        return devolver;
    }

    public boolean readJSON2(String correo_registrar,ArrayList<ClaseUsuarioActividades> usuarios2)
    {
        boolean encontrado = false;

        JSONParser parser = new JSONParser();

        try
        {

            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory() + "/JSON_files/usuarios.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (int i=0; i<jsonArray.size(); i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String) jsonObject.get("nombre");
                int edad = (int) (long) jsonObject.get("edad");
                int codigopostal = (int) (long) jsonObject.get("codigopostal");
                String correo = (String) jsonObject.get("correo");
                String contraseña = (String) jsonObject.get("contraseña");

                JSONArray jsonArrayA = (JSONArray)jsonObject.get("actividades");
                Iterator<String> iterator = jsonArrayA.iterator();
                ArrayList<String> actividades = new ArrayList<String>();
                while (iterator.hasNext()){
                    actividades.add(iterator.next());
                }

                int puntuacion = (int)(long) jsonObject.get("puntuacion");

                ClaseUsuarioActividades usuario = new ClaseUsuarioActividades(nombre, edad, codigopostal, correo, contraseña, actividades, puntuacion);
                usuarios2.add(usuario);
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

        Iterator<ClaseUsuarioActividades> iteratorusuarios = usuarios2.iterator();
        while (iteratorusuarios.hasNext() && encontrado == false)
        {
            ClaseUsuarioActividades user = iteratorusuarios.next();

            if (user.getCorreo().equals(correo_registrar)) {
                encontrado = true;
            }

        }
        return encontrado;
    }
}






