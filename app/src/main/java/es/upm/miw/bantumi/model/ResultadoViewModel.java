package es.upm.miw.bantumi.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ResultadoViewModel extends AndroidViewModel {

    private final ResultadoRepository resultadoRepository;
    private final LiveData<List<Resultado>> mejoresResultados;

    public ResultadoViewModel(Application application) {
        super(application);
        this.resultadoRepository = new ResultadoRepository(application);
        this.mejoresResultados = this.resultadoRepository.getTop10();
    }

    public void insert(Resultado resultado) {
        this.resultadoRepository.insert(resultado);
    }

    public void deleteAll() {
        this.resultadoRepository.deleteAll();
    }

    public LiveData<List<Resultado>> getTop10() {
        return this.mejoresResultados;
    }

    public LiveData<List<Resultado>> getTop10BySeeds(int numSemillasSeleccionado) {
        return this.resultadoRepository.getTop10BySeeds(numSemillasSeleccionado);
    }
}
