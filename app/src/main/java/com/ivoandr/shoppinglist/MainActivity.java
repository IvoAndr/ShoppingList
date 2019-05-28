package com.ivoandr.shoppinglist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_private_lists) {
            Intent intent = new Intent(this, PrivateListsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_public_lists) {
            Intent intent = new Intent(this, PublicListsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPublicLists(View view) {
        Intent intent = new Intent(this, PublicListsActivity.class);
        startActivity(intent);
    }

    public void onPrivateLists(View view) {
        Intent intent = new Intent(this, PrivateListsActivity.class);
        startActivity(intent);
    }

    public void onSettings(View view) {

    }

    public void onHelp(View view) {

    }
}
