package es.upm.miw.bantumi.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultadoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Resultado resultado);

    @Query("DELETE FROM resultado_table")
    void deleteAll();

    @Query("SELECT * FROM resultado_table ORDER BY numSemillasGanador DESC LIMIT 10")
    LiveData<List<Resultado>> getTop10();

    @Query("SELECT * FROM resultado_table WHERE numSemillasPartida = :numSemillasSeleccionado ORDER BY numSemillasGanador ASC LIMIT 10")
    LiveData<List<Resultado>> getTop10BySeeds(int numSemillasSeleccionado);
}