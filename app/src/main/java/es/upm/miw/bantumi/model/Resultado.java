package es.upm.miw.bantumi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import es.upm.miw.bantumi.utils.Converters;

@Entity(tableName = "resultado_table")
public class Resultado {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "fechaHoraPartida")
    @TypeConverters(Converters.class)
    private Date fechaHoraPartida;

    @ColumnInfo(name = "nombreGanador")
    private String nombreGanador;

    @ColumnInfo(name = "numSemillasGanador")
    private int numSemillasGanador;

    @ColumnInfo(name = "nombrePerdedor")
    private String nombrePerdedor;

    @ColumnInfo(name = "numSemillasPerdedor")
    private int numSemillasPerdedor;

    public Resultado(Date fechaHoraPartida, String nombreGanador, int numSemillasGanador, String nombrePerdedor, int numSemillasPerdedor) {
        this.fechaHoraPartida = fechaHoraPartida;
        this.nombreGanador = nombreGanador;
        this.numSemillasGanador = numSemillasGanador;
        this.nombrePerdedor = nombrePerdedor;
        this.numSemillasPerdedor = numSemillasPerdedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaHoraPartida() {
        return fechaHoraPartida;
    }

    public void setFechaHoraPartida(Date fechaHoraPartida) {
        this.fechaHoraPartida = fechaHoraPartida;
    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }

    public int getNumSemillasGanador() {
        return numSemillasGanador;
    }

    public void setNumSemillasGanador(int numSemillasGanador) {
        this.numSemillasGanador = numSemillasGanador;
    }

    public String getNombrePerdedor() {
        return nombrePerdedor;
    }

    public void setNombrePerdedor(String nombrePerdedor) {
        this.nombrePerdedor = nombrePerdedor;
    }

    public int getNumSemillasPerdedor() {
        return numSemillasPerdedor;
    }

    public void setNumSemillasPerdedor(int numSemillasPerdedor) {
        this.numSemillasPerdedor = numSemillasPerdedor;
    }
}
