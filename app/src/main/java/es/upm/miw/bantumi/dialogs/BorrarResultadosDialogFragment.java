package es.upm.miw.bantumi.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.ResultadoActivity;

public class BorrarResultadosDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final ResultadoActivity resultado = (ResultadoActivity) requireActivity();

        assert resultado != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(resultado);
        builder
                .setTitle(R.string.txtDialogoBorrarResultadosTitulo)
                .setMessage(R.string.txtDialogoBorrarResultadosPregunta)
                .setPositiveButton(
                        getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resultado.borrarTodo();
                            }
                        }
                )
                .setNegativeButton(
                        getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        return builder.create();
    }
}

