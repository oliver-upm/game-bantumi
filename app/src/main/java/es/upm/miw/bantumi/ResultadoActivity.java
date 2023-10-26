package es.upm.miw.bantumi;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import es.upm.miw.bantumi.dialogs.BorrarResultadosDialogFragment;
import es.upm.miw.bantumi.model.ResultadoViewModel;
import es.upm.miw.bantumi.views.ResultadoListAdapter;

public class ResultadoActivity extends AppCompatActivity {

    private ResultadoViewModel resultadoViewModel;
    private ResultadoListAdapter resultadoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        setTitle(getString(R.string.label_resultados));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.resultado_recyclerview);
        this.resultadoListAdapter = new ResultadoListAdapter(new ResultadoListAdapter.GameResultDiff());
        recyclerView.setAdapter(resultadoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.resultadoViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);

        filtroSemillas();
    }

    private void filtroSemillas() {
        Spinner spinnerSemillas = findViewById(R.id.spinnerSemillas);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.semillas_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemillas.setAdapter(spinnerAdapter);
        spinnerSemillas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    int numeroSemillasSeleccionado = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                    Log.i(MainActivity.LOG_TAG, "filtrando por " + numeroSemillasSeleccionado + " semillas");
                    resultadoViewModel.getTop10BySeeds(numeroSemillasSeleccionado).observe(ResultadoActivity.this, resultadoListAdapter::submitList);
                } else {
                    resultadoViewModel.getTop10().observe(ResultadoActivity.this, resultadoListAdapter::submitList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                resultadoViewModel.getTop10().observe(ResultadoActivity.this, resultadoListAdapter::submitList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resultado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(MainActivity.LOG_TAG, "volviendo a la actividad principal desde mejores resultados");
                finish();
                return true;
            case R.id.accionBorrarResultados:
                Log.i(MainActivity.LOG_TAG, "opcion BORRAR RESULTADOS");
                BorrarResultadosDialogFragment borrarResultadosDialogFragment = new BorrarResultadosDialogFragment();
                borrarResultadosDialogFragment.show(getSupportFragmentManager(), "frgBorrarResultados");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void borrarTodo() {
        Log.i(MainActivity.LOG_TAG, "borrando todos los resultados...");
        this.resultadoViewModel.deleteAll();
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtSnackbarBorrarResultados),
                Snackbar.LENGTH_LONG
        ).show();
    }
}
