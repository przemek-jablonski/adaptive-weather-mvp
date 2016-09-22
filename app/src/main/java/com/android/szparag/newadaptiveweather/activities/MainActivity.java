package com.android.szparag.newadaptiveweather.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.R;

import java.util.Random;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    public String sharedPrefsKey = "dagger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dagger dependency injection
        ((AppController)getApplication()).getComponent().inject(this);

        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean(sharedPrefsKey, true).commit();
        } else {
            sharedPreferences.edit().putBoolean(sharedPrefsKey, false).commit();
        }


        //layout (nested fragment inside .xml here)
        setContentView(R.layout.activity_main);

        //toolbar instantiation from .xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //floating action button instantiation + onclick
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
