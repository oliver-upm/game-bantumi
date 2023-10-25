package es.upm.miw.bantumi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.model.ResultadoViewModel;
import es.upm.miw.bantumi.views.ResultadoListAdapter;

public class ResultadoActivity extends AppCompatActivity {
    private ResultadoViewModel resultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        setTitle(getString(R.string.label_resultados));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.resultado_recyclerview);
        final ResultadoListAdapter adapter = new ResultadoListAdapter(new ResultadoListAdapter.GameResultDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.resultViewModel = new ViewModelProvider(this).get(ResultadoViewModel.class);
        resultViewModel.getTop10().observe(this, adapter::submitList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
