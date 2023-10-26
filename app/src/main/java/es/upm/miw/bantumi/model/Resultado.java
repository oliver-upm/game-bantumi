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

    @ColumnInfo(name = "fecha")
    @TypeConverters(Converters.class)
    private Date fecha;

    @ColumnInfo(name = "nombreGanador")
    private String nombreGanador;

    @ColumnInfo(name = "numSemillasGanador")
    private int numSemillasGanador;

    @ColumnInfo(name = "nombrePerdedor")
    private String nombrePerdedor;

    @ColumnInfo(name = "numSemillasPerdedor")
    private int numSemillasPerdedor;

    @ColumnInfo(name = "numSemillasPartida")
    private int numSemillasPartida;

    public Resultado(Date fecha, String nombreGanador, int numSemillasGanador, String nombrePerdedor, int numSemillasPerdedor, int numSemillasPartida) {
        this.fecha = fecha;
        this.nombreGanador = nombreGanador;
        this.numSemillasGanador = numSemillasGanador;
        this.nombrePerdedor = nombrePerdedor;
        this.numSemillasPerdedor = numSemillasPerdedor;
        this.numSemillasPartida = numSemillasPartida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }

    public Integer getNumSemillasGanador() {
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

    public Integer getNumSemillasPerdedor() {
        return numSemillasPerdedor;
    }

    public void setNumSemillasPerdedor(int numSemillasPerdedor) {
        this.numSemillasPerdedor = numSemillasPerdedor;
    }

    public Integer getNumSemillasPartida() {
        return numSemillasPartida;
    }

    public void setNumSemillasPartida(int numSemillasPartida) {
        this.numSemillasPartida = numSemillasPartida;
    }
}
