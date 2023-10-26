package es.upm.miw.bantumi.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ResultadoRepository {

    private final ResultadoDAO resultadoDAO;
    private final LiveData<List<Resultado>> mejoresResultados;

    ResultadoRepository(Application application) {
        ResultadoRoomDatabase db = ResultadoRoomDatabase.getDatabase(application);
        this.resultadoDAO = db.resultadoDAO();
        this.mejoresResultados = this.resultadoDAO.getTop10();
    }

    void insert(Resultado resultado) {
        ResultadoRoomDatabase.databaseWriteExecutor.execute(() -> {
            this.resultadoDAO.insert(resultado);
        });
    }

    void deleteAll() {
        ResultadoRoomDatabase.databaseWriteExecutor.execute(() -> {
            this.resultadoDAO.deleteAll();
        });
    }

    LiveData<List<Resultado>> getTop10() {
        return this.mejoresResultados;
    }

    LiveData<List<Resultado>> getTop10BySeeds(int numSemillasSeleccionado) {
        return this.resultadoDAO.getTop10BySeeds(numSemillasSeleccionado);
    }
}
