package es.upm.miw.bantumi.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class ManejadorMemoriaInterna {

    private final String LOG_TAG = "MiW";
    private final String nombreFichero = "partidaBantumi.txt";

    public void guardarPartida(Context context, String partidaSerializada) {
        try {
            FileOutputStream fos;

            fos = context.openFileOutput(nombreFichero, Context.MODE_PRIVATE);
            fos.write(partidaSerializada.getBytes());
            fos.write('\n');
            fos.close();
            Log.i(LOG_TAG, "partida " + partidaSerializada + " guardada en " + nombreFichero);

        } catch (Exception e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String recuperarPartida(Context context) {
        String partidaSerializada = null;
        BufferedReader fin;

        try {   // Leer fichero y mostrarlo en TextView
            fin = new BufferedReader(
                    new InputStreamReader(context.openFileInput(nombreFichero)));
            partidaSerializada = fin.readLine();
            fin.close();
            Log.i(LOG_TAG, "partida " + partidaSerializada + " recuperada de " + nombreFichero);
        } catch (Exception e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return partidaSerializada;
    }
}
