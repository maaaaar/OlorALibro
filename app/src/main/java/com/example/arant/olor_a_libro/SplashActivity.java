package com.example.arant.olor_a_libro;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.example.arant.olor_a_libro.Activities.olorAlibro;

public class SplashActivity extends Activity {
    private final int DURACION_SPLASH = 4000; // 4 segundos
    private ProgressBar miprogress;
    private ObjectAnimator anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.principal);
        miprogress = (ProgressBar) findViewById(R.id.circularProgress);
        //instanciamos el animador
        //Construye y devuelve un ObjectAnimator que anima.
        anim = ObjectAnimator.ofInt(miprogress, "progress", 0, 100);
        mostrarProgress();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(SplashActivity.this, olorAlibro.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);


    }
    private void mostrarProgress(){
        //agregamos el tiempo de la animacion a mostrar
        anim.setDuration(15000);
        anim.setInterpolator(new DecelerateInterpolator());
        //iniciamos el progressbar
        anim.start();
    }
}
