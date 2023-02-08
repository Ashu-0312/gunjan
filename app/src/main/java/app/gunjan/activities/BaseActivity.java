package app.gunjan.activities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import app.gunjan.utill.ContextWrapper;
import app.gunjan.utill.FCSharedPreferances;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {

        Locale newLocale;
        String lang = FCSharedPreferances.getSharedPreferance(this).getSAVE_LANG();
        newLocale = new Locale(lang);

        Context context = ContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }
}
