    package com.example.arant.olor_a_libro.Activities;

    import android.Manifest;
    import android.app.Activity;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListView;

    import com.example.arant.olor_a_libro.Adaptadores.Adaptador;
    import com.example.arant.olor_a_libro.Clases.ClaseMenu;
    import com.example.arant.olor_a_libro.R;


    import java.util.ArrayList;

    public class olorAlibro extends AppCompatActivity {
    //DECLARAR VARIABLES
        private ListView lista;
        private Adaptador adapt;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Le indicamos que layout vamos a usar
            setContentView(R.layout.lista);
            //Declaramos el action bar
            ActionBar act =getSupportActionBar();
            //le indicamos que en action bar queremos un subtitulo que diga MENU
             act.setSubtitle("Menu");




            //PERMISOS//
            if (android.os.Build.VERSION.SDK_INT >= 23)
            {
                // Si executem la versió Marshmallow (6.0) o posterior, haurem de demanar
                // permisos en temps d'execució

                // Comprovem si l'usuari ja ens ha donat permisos en una execió anterior
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {

                    // Si l'usuari no ens havia atorgat permisos, els hi demanem i
                    // executem el nostre codi

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);


                    // Codi que volem executar

                }
                else
                {
                    // Si l'usuari ja ens havia atorgat permisos en una execució anterior,
                    // executem directament el nostre codi

                    // Codi que volem executar

                }
            }
            else
            {

                // Si executem una versió anterior a la versió Marshmallow (6.0),
                // no cal demanar cap permís, i podem executar el nostre codi directament

                // Codi que volem executar

            }
            //Le indicamos a que list view nos referimos
            lista=(ListView) findViewById(R.id.ListaMenu);
            //Llamamos al metodo , con los datos que queremos que nos rellene .
            adapt=new Adaptador(this,LlenarMenu());
            //Unimos la lista y el adaptador
            lista.setAdapter(adapt);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                    String textValue = (String) adapter.getItemAtPosition(position);

                    ;
                    //En cada posicion le indicamos que cuando haga click nos lleve a X sitio.
                    if(position==0){
                        Intent myintent=new Intent(v.getContext(),Librerias.class);
                        myintent.putExtra("registrado", false);
                        startActivityForResult(myintent,0);
                    }
                    if(position==1){
                        Intent myintent=new Intent(v.getContext(),MainActividades.class);
                        myintent.putExtra("registrado", false);
                        startActivityForResult(myintent,0);

                    }
                    if(position==2){
                        Intent myintent=new Intent(v.getContext(),DatosDeRed.class);
                        myintent.putExtra("registrado", false);
                        startActivityForResult(myintent,0);
                    }
                    if(position==3){
                        Intent myintent=new Intent(v.getContext(),AtencionAlCliente.class);
                        myintent.putExtra("registrado", false);
                        startActivityForResult(myintent,0);
                    }




                }
            });

        }
        @Override
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
                    Intent menu=new Intent(olorAlibro.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:

                    Intent myintent=new Intent(olorAlibro.this,Librerias.class);
                    myintent.putExtra("registrado", false);

                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:

                    Intent actividades=new Intent(olorAlibro.this,MainActividades.class);
                    actividades.putExtra("registrado", false);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:

                    Intent redes=new Intent(olorAlibro.this,DatosDeRed.class);
                    redes.putExtra("registrado", false);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;
                case R.id.AtencionToolbar:

                    Intent atencion=new Intent(olorAlibro.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", false);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;
                case R.id.UsuarioToolbar:

                    Intent Usuario=new Intent(olorAlibro.this,Login.class);
                    Usuario.putExtra("registrado", false);
                    startActivityForResult(Usuario,0);
                    devolver=true;
                    break;
                default:
                    devolver=super.onOptionsItemSelected(item);
                    break;

            }
            return devolver;
        }



        /*Declaramos un metodo con una ArrayList de la clasemenu y lo llenamos con los
        datos que queremos que tenga*/
        private ArrayList<ClaseMenu> LlenarMenu(){

            ArrayList<ClaseMenu> lista=new ArrayList<>();
            lista.add(new ClaseMenu(R.mipmap.bookshelf,"Librerías"));
            lista.add(new ClaseMenu(R.mipmap.jigsaw,"Actividades"));
            lista.add(new ClaseMenu(R.mipmap.equipo,"Datos de Red"));
            lista.add(new ClaseMenu(R.mipmap.servicio,"Atención al Cliente"));

            return lista;
        }
    }

