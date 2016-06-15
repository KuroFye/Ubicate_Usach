package com.example.vanaheim.tester_1;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import Controladores.HttpPost;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import modelos.Usuario;
import utilidades.SystemUtilities;

import com.example.vanaheim.tester_1.ValorarLugar;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    FragmentTransaction transaction;

    /**
     *          Método que se ejecuta al momento de crear la actividad, llama al fragmento que muestra una lista de elementos
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new MenuPrincipal());
        transaction.commit();
    }

    /**
     *    para pausar la camara
     */

    @Override
    public void onPause() {
        super.onPause();
        //mScannerView.stopCamera();   // Stop camera on pause
    }

    /**
     *          Método que se ejecuta al momento de presionar el botón regresar del teclado
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }// onBackPressed()

    /**
     *                                          Método que crea el menú superior
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }// onCreateOptionsMenu(Menu menu)

    /**
     *                                          Método que escucha los elementos presionados en el menú superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_activity_add:
                if (!(getFragmentManager().findFragmentByTag("isActiveNewItem") != null && getFragmentManager().findFragmentByTag("isActiveNewItem").isVisible())) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new CrearUsuario(), "isActiveNewItem");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.menu_main_activity_see:
                if (!(getFragmentManager().findFragmentByTag("isActiveNewItem") != null && getFragmentManager().findFragmentByTag("isActiveNewItem").isVisible())) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new MostrarLugares(), "isActiveNewItem");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.menu_main_valorar:
                if (!(getFragmentManager().findFragmentByTag("isActiveNewItem") != null && getFragmentManager().findFragmentByTag("isActiveNewItem").isVisible())) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new ValorarLugar(), "isActiveNewItem");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.menu_main_activity_qr:
                if (!(getFragmentManager().findFragmentByTag("isActiveNewItem") != null && getFragmentManager().findFragmentByTag("isActiveNewItem").isVisible())) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new EscanearCodigoQR(), "isActiveNewItem");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.menu_main_activity_map:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_main_activity_exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }// onOptionsItemSelected(MenuItem item)
/**
 *                                                                     Metodos de menu principal
 */
    /**
     * Metodo para usar los textos para cambiar de pagina
     */
    public void onClickCrearUsuario(View v){
        /*TextView tv = (TextView)findViewById(R.id.text_ver_lugares);
        tv.setText("Swag");*/
        Fragment CrearUsuario = new CrearUsuario();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, CrearUsuario);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClickVerLugares(View v){
        /*TextView tv = (TextView)findViewById(R.id.text_ver_lugares);
        tv.setText("Swag");*/
        Fragment MostrarLugares = new MostrarLugares();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, MostrarLugares);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void onClickValorarLugares(View v){
        /*TextView tv = (TextView)findViewById(R.id.text_ver_lugares);
        tv.setText("Swag");*/
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ValorarLugar());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClickVerMapa(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /**
     *                                                                  Metodos Crear Usuario
     */
    /**
     * conecta con la DB para crear un usuario
     */
    public void onClickBotonCrearUsuario(View v){

        EditText user = (EditText) findViewById(R.id.espacio_crear_nombre);
        EditText pass = (EditText) findViewById(R.id.espacio_crear_password);
        EditText mail = (EditText) findViewById(R.id.espacio_crear_email);
        String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/usuarios";
        Toast.makeText(this,user.getText().toString() , Toast.LENGTH_LONG).show();
        Usuario actor = new Usuario(user.getText().toString(), pass.getText().toString(), mail.getText().toString());
        String actorS = "{\"nombreUser\":\"" + actor.getNombreUser() + "\",\"mailUser\":\"" + actor.getMailUser() + "\",\"contraseñaUser\":\"" + actor.getContrasenaUser() +"\"}";
        Toast.makeText(this,"hola apreté el boton desde onclick",Toast.LENGTH_LONG).show();
        try {
            SystemUtilities su = new SystemUtilities(this.getApplicationContext());
            if (su.isNetworkAvailable()) {
                try {
                    AsyncTask resp = new HttpPost(this.getApplicationContext()).execute(actorS,URL_GET);
                    Toast.makeText(this, "se agregó a "+actor.getNombreUser(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }
    }


    /*  Metodo que permite insertar un comentario en la base de datos

*/
    public void crearComentario(View v){
        EditText comentario = (EditText) findViewById(R.id.edit_text_comentario);
        String URL_GET = "http://158.170.62.221:8080/sakila-backend-master/valoraciones";
        Toast.makeText(this, "Funciona el botón", Toast.LENGTH_SHORT).show();
        String consulta ="{\"userId\":\"1\",\"publicacionId\":\"1\",\"valoracion\":\"3\",\"texto\":\"" +comentario+"\"}";
        try {
            SystemUtilities su = new SystemUtilities(this.getApplicationContext());
            if (su.isNetworkAvailable()) {
                try {
                    //AsyncTask resp = new HttpPost(this.getApplicationContext()).execute(consulta,URL_GET);
                    Toast.makeText(this, "Comentario agregado correctamente", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }
    }





    /*
    *                               Metodo para leer codigo QR
     */
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        if (rawResult.getText().toString().equals("Estatua EAO")){
            setContentView(R.layout.activity_main);
            Fragment MostrarLugar = new MostrarLugar();
            String item = "http://158.170.62.221:8080/sakila-backend-master/publicaciones/3";
            Bundle arguments = new Bundle();
            arguments.putString("item", item);
            MostrarLugar.setArguments(arguments);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, MostrarLugar);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        // show the scanner result into dialog box.
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setMessage(rawResult.getText());
        //AlertDialog alert1 = builder.create();
        //alert1.show();

       // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    public void QrScanner(View view){

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

}
