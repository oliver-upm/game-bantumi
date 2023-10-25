package es.upm.miw.bantumi.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.model.Resultado;

public class ResultadoViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvFecha;
    private final TextView tvGanador;
    private final TextView tvPerdedor;
    private final TextView tvSemillasGanador;
    private final TextView tvSemillasPerdedor;

    public ResultadoViewHolder(View itemView) {
        super(itemView);
        this.tvFecha = itemView.findViewById(R.id.textViewFecha);
        this.tvGanador = itemView.findViewById(R.id.textViewGanador);
        this.tvPerdedor = itemView.findViewById(R.id.textViewPerdedor);
        this.tvSemillasGanador = itemView.findViewById(R.id.textViewNumSemillasGanador);
        this.tvSemillasPerdedor = itemView.findViewById(R.id.textViewNumSemillasPerdedor);
    }

    public void bind(Resultado resultado) {
        this.tvFecha.setText(resultado.getFecha().toString());
        this.tvGanador.setText(resultado.getNombreGanador());
        this.tvPerdedor.setText(resultado.getNombrePerdedor());
        this.tvSemillasGanador.setText(resultado.getNumSemillasGanador().toString());
        this.tvSemillasPerdedor.setText(resultado.getNumSemillasPerdedor().toString());
    }

    static ResultadoViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resultado_recyclerview_item, parent, false);
        return new ResultadoViewHolder(view);
    }
}
