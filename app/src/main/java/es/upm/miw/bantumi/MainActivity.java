package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Locale;

import es.upm.miw.bantumi.dialogs.FinalAlertDialog;
import es.upm.miw.bantumi.dialogs.GuardarPartidaDialogFragment;
import es.upm.miw.bantumi.dialogs.RecuperarPartidaDialogFragment;
import es.upm.miw.bantumi.dialogs.ReiniciarDialogFragment;
import es.upm.miw.bantumi.model.BantumiViewModel;
import es.upm.miw.bantumi.model.Resultado;
import es.upm.miw.bantumi.model.ResultadoViewModel;
import es.upm.miw.bantumi.utils.ManejadorMemoriaInterna;

public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = "MiW";
    int numInicialSemillas;
    JuegoBantumi juegoBantumi;
    JuegoBantumi.Turno turnoInicial;
    BantumiViewModel bantumiVM;
    ManejadorMemoriaInterna manejadorMemoriaInterna;
    SharedPreferences preferencias;
    ResultadoViewModel resultadoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        cargarPreferencias();
        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, turnoInicial, numInicialSemillas);
        manejadorMemoriaInterna = new ManejadorMemoriaInterna();
        crearObservadores();
        resultadoViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);
    }

    private void cargarPreferencias() {
        setNombreJugador();
        setNumInicialSemillas();
        setTurnoInicial();
    }

    private void setNombreJugador() {
        TextView tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer1.setText(getNombreJugador());
    }

    private String getNombreJugador() {
        return preferencias.getString(
                getString(R.string.key_NombreJugador1),
                getString(R.string.txtPlayer1));
    }

    private void setNumInicialSemillas() {
        int numInicialSemillas = preferencias.getInt(
                getString(R.string.key_numIncialSemillas),
                getResources().getInteger(R.integer.default_numInicialSemillas));
        this.numInicialSemillas = numInicialSemillas;
    }

    private void setTurnoInicial() {
        String turno = preferencias.getString(
                getString(R.string.key_TurnoInicial),
                getString(R.string.default_txtInitialTurn));
        this.turnoInicial = this.turnoInicial.valueOf(turno);
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos   posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                Log.i(LOG_TAG, "opción AJUSTES");
                startActivity(new Intent(this, AjustesActivity.class));
                break;
            case R.id.opcAcercaDe:
                Log.i(LOG_TAG, "opción ACERCA DE");
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                break;
            case R.id.opcReiniciarPartida:
                Log.i(LOG_TAG, "opción REINICIAR");
                ReiniciarDialogFragment reiniciarDialogFragment = new ReiniciarDialogFragment();
                reiniciarDialogFragment.show(getSupportFragmentManager(), "frgReiniciar");
                break;
            case R.id.opcGuardarPartida:
                Log.i(LOG_TAG, "opción GUARDAR");
                GuardarPartidaDialogFragment guardarPartidaDialogFragment = new GuardarPartidaDialogFragment();
                guardarPartidaDialogFragment.show(getSupportFragmentManager(), "frgGuardar");
                break;
            case R.id.opcRecuperarPartida:
                Log.i(LOG_TAG, "opción RECUPERAR");
                if (juegoBantumi.isJuegoEmpezado()) {
                    RecuperarPartidaDialogFragment recuperarPartidaDialogFragment = new RecuperarPartidaDialogFragment();
                    recuperarPartidaDialogFragment.show(getSupportFragmentManager(), "frgRecuperar");
                } else {
                    this.recuperarPartida();
                }
                break;
            case R.id.opcMejoresResultados:
                Log.i(LOG_TAG, "opción MEJORES RESULTADOS");
                startActivity(new Intent(this, ResultadoActivity.class));
                break;
            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    public void reiniciar() {
        Log.i(LOG_TAG, "reiniciando partida...");
        cargarPreferencias();
        juegoBantumi.reiniciar(this.turnoInicial, this.numInicialSemillas);
    }

    public void guardarPartida() {
        Log.i(LOG_TAG, "guardando partida...");
        this.manejadorMemoriaInterna.guardarPartida(this, juegoBantumi.serializa());
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtSnackbarPartidaGuardada),
                Snackbar.LENGTH_LONG
        ).show();
    }

    public void recuperarPartida() {
        Log.i(LOG_TAG, "recuperando partida...");
        String partidaSerializada = this.manejadorMemoriaInterna.recuperarPartida(this);
        String msgSnackbar;
        if (partidaSerializada != null) {
            juegoBantumi.deserializa(partidaSerializada);
            this.numInicialSemillas = juegoBantumi.getNumInicialSemillas();
            msgSnackbar = getString(R.string.txtSnackbarPartidaRecuperada);
        } else {
            msgSnackbar = getString(R.string.txtSnackbarPartidaNoEncontrada);
        }
        Snackbar.make(
                findViewById(android.R.id.content),
                msgSnackbar,
                Snackbar.LENGTH_LONG
        ).show();
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria [7..12]
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String nombreGanador, nombrePerdedor;
        int posGanador, posPerdedor;
        if (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas) {
            nombreGanador = this.getNombreJugador();
            nombrePerdedor = getString(R.string.txtPlayer2);
            posGanador = 6;
            posPerdedor = 13;
        } else {
            nombreGanador = getString(R.string.txtPlayer2);
            nombrePerdedor = this.getNombreJugador();
            posGanador = 13;
            posPerdedor = 6;
        }

        String texto = "¡¡¡ " + nombreGanador + " GANA !!!";
        if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
        }
        Snackbar.make(
                        findViewById(android.R.id.content),
                        texto,
                        Snackbar.LENGTH_LONG
                )
                .show();

        Resultado resultado = new Resultado(
                new Date(),
                nombreGanador,
                juegoBantumi.getSemillas(posGanador),
                nombrePerdedor,
                juegoBantumi.getSemillas(posPerdedor),
                this.numInicialSemillas
        );
        this.resultadoViewModel.insert(resultado);
        Log.i(LOG_TAG, "resultado insertado en la base de datos");
        // terminar
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }
}