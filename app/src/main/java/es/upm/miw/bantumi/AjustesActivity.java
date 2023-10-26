package es.upm.miw.bantumi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        setTitle(getString(R.string.label_ajustes));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ajustes, new AjustesFragment())
                .commit();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Log.i(MainActivity.LOG_TAG, "volviendo a la actividad principal desde ajustes");
        return true;
    }

    public static class AjustesFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferencias, rootKey);
        }
    }
}
