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
import android.widget.Toast;

import com.example.arant.olor_a_libro.Clases.ClaseUsuario;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Login extends AppCompatActivity implements Serializable
{
    EditText correo, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        correo = findViewById(R.id.editTextCorreo);
        contraseña = findViewById(R.id.editTextContraseña);
        Button entrar = findViewById(R.id.buttonEntrar);

        /**
         * Evento que se inicia al clicar en el botón 'ENTRAR' del Login.
         */
        entrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * Creamos un ArrayList de usuarios vacío.
                 */
                ArrayList<ClaseUsuario> usuarios = new ArrayList<ClaseUsuario>();
                /**
                 * Lo rellenamos con los usuarios que haya en el fichero JSON.
                 */
                usuarios = readJSON2(usuarios);
                boolean encontrado = false;
                /**
                 * Cogemos el correo y contraseña que introduzca el usuario
                 * en los EditText.
                 */
                String correo_edit = correo.getText().toString();
                String contraseña_edit = contraseña.getText().toString();
                /**
                 * Creamos un iterator para recorrer todos los usuarios.
                 */
                Iterator<ClaseUsuario> iteratorusuarios = usuarios.iterator();

                int i = 0;
                while (iteratorusuarios.hasNext() && encontrado == false)
                {
                    ClaseUsuario user = iteratorusuarios.next();
                    String correo_user = user.getCorreo();
                    String contraseña_user = user.getContraseña();
                    /**
                     * Si encontramos el usuario con los datos introducidos
                     * anteriormente, guardaremos la posición del usuario en el ArrayList.
                     */
                    if(correo_edit.equals(correo_user) && contraseña_edit.equals(contraseña_user))
                    {
                        encontrado = true;
                        Intent myintent=new Intent(Login.this,olorAlibroRegistrado.class);
                        myintent.putExtra("usuario",i);
                        startActivityForResult(myintent,0);
                    }
                    i++;

                }
                /**
                 * Si no lo encontramos, se lo comunicaremos al usuario.
                 */
                if (encontrado == false) {
                    Toast.makeText(getApplicationContext(), "Este usuario no existe.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    /**
     * Evento que ocurre cuando clicamos en la palabra 'aquí'.
     */
    public void irARegistro(View v)
    {
        /**
         * Nos envía al registro.
         */
        Intent myintent=new Intent(this, RegistrarUsuario.class);
        startActivityForResult(myintent,0);
    }
    public void irARecuperarContraseña(View v)
    {
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Enviar a la página de recuperar contraseña.", Toast.LENGTH_SHORT);

        toast1.show();
    }
    /**
     * Le pasamos un ArrayList de usuarios vacío y nos devuelve otro rellenado
     * después de haber leído el JSON de usuarios (donde se encuentran guardados los usuarios).
     */
    public ArrayList<ClaseUsuario> readJSON2(ArrayList<ClaseUsuario> usuarios2)
    {

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

                ClaseUsuario usuario = new ClaseUsuario(nombre, edad, codigopostal, correo, contraseña);
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
        return usuarios2;
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
                Intent menu=new Intent(Login.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(Login.this,Librerias.class);
                myintent.putExtra("registrado", false);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(Login.this,MainActividades.class);
                actividades.putExtra("registrado", false);
                startActivityForResult(actividades,0);
                devolver=true;
                break;
            case R.id.RedToolbar:

                Intent redes=new Intent(Login.this,DatosDeRed.class);
                redes.putExtra("registrado", false);
                startActivityForResult(redes,0);
                devolver=true;
                break;
            case R.id.AtencionToolbar:

                Intent atencion=new Intent(Login.this,AtencionAlCliente.class);
                atencion.putExtra("registrado", false);
                startActivityForResult(atencion,0);
                devolver=true;
                break;
            case R.id.UsuarioToolbar:

                Intent Usuario=new Intent(Login.this,Login.class);
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