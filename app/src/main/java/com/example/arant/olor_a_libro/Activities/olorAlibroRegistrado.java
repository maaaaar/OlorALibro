    package com.example.arant.olor_a_libro.Activities;

    import android.content.Intent;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.ListView;

    import com.example.arant.olor_a_libro.Adaptadores.Adaptador;
    import com.example.arant.olor_a_libro.Clases.ClaseMenu;
    import com.example.arant.olor_a_libro.Clases.ClaseUsuario;
    import com.example.arant.olor_a_libro.R;


    import java.util.ArrayList;

    public class olorAlibroRegistrado extends AppCompatActivity {
        //DECLARAR VARIABLES
        private ListView lista;
        private Adaptador adapt;
        private int posicionUsuario;
        private ImageButton imagen;
        private Button logout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Le indicamos que layout vamos a usar
            setContentView(R.layout.listayfoto);
            //Declaramos el action bar
            ActionBar act =getSupportActionBar();

            //le indicamos que en action bar queremos un subtitulo que diga MENU
            act.setSubtitle("Menu");

            /*Agafem les intents*/

            posicionUsuario = getIntent().getExtras().getInt("usuario");
            //Le indicamos a que list view nos referimos
           imagen =(ImageButton) findViewById(R.id.botonfoto);
           logout = findViewById(R.id.buttonLOGOUT);
           lista=(ListView) findViewById(R.id.ListaMenuyfoto);



            //Llamamos al metodo , con los datos que queremos que nos rellene .
            adapt=new Adaptador(this,LlenarMenu());
            //Unimos la lista y el adaptador
            lista.setAdapter(adapt);

            //Cuando clickemos en la imagen nos mandara al perfil del usuario
            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent=new Intent(olorAlibroRegistrado.this,PerfilUsuario.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    startActivityForResult(myintent,0);
                }
            });
            //En cada posicion le indicamos que cuando haga click nos lleve a X sitio.
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                    String textValue = (String) adapter.getItemAtPosition(position);

                    ;
                    if(position==0){
                        Intent myintent=new Intent(v.getContext(),Librerias.class);
                        myintent.putExtra("registrado", true);
                        myintent.putExtra("usuario", posicionUsuario);
                        startActivityForResult(myintent,0);
                    }
                    if(position==1){
                        Intent myintent=new Intent(v.getContext(),MainActividades.class);
                        myintent.putExtra("registrado", true);
                        myintent.putExtra("usuario", posicionUsuario);
                        myintent.putExtra("apuntado", false);
                        startActivityForResult(myintent,0);
                    }
                    if(position==2){
                        Intent myintent=new Intent(v.getContext(),Main_Activity_Actividades_Usuario.class);
                        myintent.putExtra("registrado", true);
                        myintent.putExtra("usuario", posicionUsuario);
                        startActivityForResult(myintent,0);
                    }
                    if(position==3){
                        Intent myintent=new Intent(v.getContext(),DatosDeRed.class);
                        myintent.putExtra("usuario", posicionUsuario);
                        myintent.putExtra("registrado", true);
                        startActivityForResult(myintent,0);
                    }
                    if(position==4){
                        Intent myintent=new Intent(v.getContext(),AtencionAlCliente.class);
                        myintent.putExtra("usuario", posicionUsuario);
                        myintent.putExtra("registrado", true);
                        startActivityForResult(myintent,0);
                    }
                    if(position==5){
                        Intent myintent=new Intent(v.getContext(),Ranking.class);
                        myintent.putExtra("usuario", posicionUsuario);
                        myintent.putExtra("registrado", true);
                        startActivityForResult(myintent,0);
                    }
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent=new Intent(v.getContext(),olorAlibro.class);

                    startActivityForResult(myintent,0);
                }
            });

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
                    Intent menu=new Intent(olorAlibroRegistrado.this,olorAlibro.class);
                    startActivityForResult(menu,0);
                    devolver=true;
                    break;
                case R.id.Miperfiltoolbar:
                    Intent perfil = new Intent(olorAlibroRegistrado.this, PerfilUsuario.class);
                    perfil.putExtra("usuario", posicionUsuario);
                    perfil.putExtra("registrado", true);
                    startActivityForResult(perfil,0);
                    devolver=true;
                    break;
                case R.id.Libreriastoolbar:
                    Intent myintent=new Intent(olorAlibroRegistrado.this,Librerias.class);
                    myintent.putExtra("usuario", posicionUsuario);
                    myintent.putExtra("registrado", true);
                    startActivityForResult(myintent,0);
                    devolver=true;
                    break;
                case R.id.ActividadesToolbar:
                    Intent actividades=new Intent(olorAlibroRegistrado.this,MainActividades.class);
                    actividades.putExtra("registrado", true);
                    actividades.putExtra("usuario", posicionUsuario);
                    startActivityForResult(actividades,0);
                    devolver=true;
                    break;
                case R.id.RedToolbar:
                    Intent redes=new Intent(olorAlibroRegistrado.this,DatosDeRed.class);
                    redes.putExtra("registrado", true);
                    redes.putExtra("usuario", posicionUsuario);
                    startActivityForResult(redes,0);
                    devolver=true;
                    break;
                case R.id.AtencionToolbar:
                    Intent atencion=new Intent(olorAlibroRegistrado.this,AtencionAlCliente.class);
                    atencion.putExtra("registrado", true);
                    atencion.putExtra("usuario", posicionUsuario);
                    startActivityForResult(atencion,0);
                    devolver=true;
                    break;
                case R.id.Ranking:
                    Intent Ranking=new Intent(olorAlibroRegistrado.this,Ranking.class);
                    Ranking.putExtra("usuario", posicionUsuario);
                    Ranking.putExtra("registrado", true);
                    startActivityForResult(Ranking,0);
                    devolver=true;
                    break;
                case R.id.UsuarioToolbar:
                    Intent Usuario=new Intent(olorAlibroRegistrado.this,olorAlibro.class);
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

    /*Declaramos un metodo con una ArrayList de la clasemenu y lo llenamos con los
        datos que queremos que tenga*/

        private ArrayList<ClaseMenu> LlenarMenu(){

            ArrayList<ClaseMenu> lista=new ArrayList<>();
            lista.add(new ClaseMenu(R.mipmap.bookshelf,"Librerías"));
            lista.add(new ClaseMenu(R.mipmap.jigsaw,"Actividades"));
            lista.add(new ClaseMenu(R.mipmap.jigsaw,"Mis Actividades"));
            lista.add(new ClaseMenu(R.mipmap.equipo,"Datos de Red"));
            lista.add(new ClaseMenu(R.mipmap.servicio,"Atención al Cliente"));
            lista.add(new ClaseMenu(R.mipmap.ranking, "Ranking"));

            return lista;
        }
    }
