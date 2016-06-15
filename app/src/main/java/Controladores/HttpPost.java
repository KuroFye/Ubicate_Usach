package Controladores;

import android.content.Context;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bpastene on 20-05-16.
 */
public class HttpPost extends AsyncTask<String, Void, String> {
    private String json;
    private String url;
    private HttpURLConnection httpCon;
    //private Context context;
    //private ProgressDialog proDialog;

    public HttpPost(Context context) {
        //  this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
// Showing progress loading dialog
        //proDialog = new ProgressDialog(context);
        //   proDialog.setMessage("Please wait...");
        // proDialog.setCancelable(false);
        //proDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        this.json=params[0];
        this.url= params[1];
        URL url2;
        try {
            url2 = new URL(url);
            httpCon = (HttpURLConnection) url2.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestMethod("POST");
            httpCon.setReadTimeout(10000);
            httpCon.setConnectTimeout(15000);
            httpCon.connect();

            OutputStream os = httpCon.getOutputStream();
            DataOutputStream osw = new DataOutputStream(os);
            //OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.writeBytes(json);
            osw.flush();
            osw.close();
            int statusCode = httpCon.getResponseCode();
            if (statusCode==204) return "La inserción fue correcta";
            else return "Hay un poblema con la consulta";

        } catch (MalformedURLException e) {
            return "Error al crear la consulta: "+e.toString();
        } catch (IOException e) {
            return "Error IO: "+ e.toString();
        }catch (Exception e){
            return "Error Exception: "+e.toString();
        }
    }


    @Override
    protected void onPostExecute(String requestresult) {
        super.onPostExecute(requestresult);
        System.out.println(requestresult );
        httpCon.disconnect();
    }
}
