package com.ivoandr.shoppinglist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PublicListsActivity extends AppCompatActivity {
    private static final int LIST_REQUEST = 0;
    private static final int NEW_LIST_REQUEST = 1;

    private final ArrayList<ShoppingList> listsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_lists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fabNew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewListActivity.class);
                startActivityForResult(intent, NEW_LIST_REQUEST);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getData();
    }

    @Override
    public void onResume() {
        super.onResume();

//        while (getData().is)
        getData();
    }

    private void getData() {
//        swipeRefreshLayout.setRefreshing(true);
        boolean isLoading = true;
        String url = LinksManager.getInstance().getAllLists();

        ConnectionManager connectionManager = new ConnectionManager(this, url);
        connectionManager.execute();

        try {
            String result = connectionManager.get();
            listsList.clear();

            try {
                JSONObject data;
                data = new JSONObject(result);
                int length = data.getJSONArray("lists").length();
                Gson gson = new Gson();

                Log.i("Data", data.toString());

                for (int i = 0; i < length; i++) {
                    ShoppingList currentList = gson.fromJson(data.getJSONArray("lists").getJSONObject(i).toString(), ShoppingList.class);
                    listsList.add(currentList);
                }

//                swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
                printLists();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printLists() {
        ArrayList<String> listStringArray = new ArrayList<>();

        for (ShoppingList list : listsList) {
            if (list.getId() == 1) {
                Button endingItemsButton = findViewById(R.id.buttonEndingItems);
                int endingItemCount = list.getItemsCount();

                if (endingItemCount > 0) {
                    endingItemsButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    endingItemsButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
                String title = getResources().getString(R.string.endingItems) + " - " + endingItemCount + " " + getResources().getString(R.string.items);
                endingItemsButton.setText(title);
            }
            else {
                String currentList = list.getName() + " - " + list.getItemsCount() + " " + getResources().getString(R.string.items) + "\n" + list.getFormattedDate();
                listStringArray.add(currentList);
            }
        }

        Log.i("listData", listStringArray.toString());

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStringArray);
        try {
            ListView publicListView = findViewById(R.id.publicListsView);
            publicListView.setAdapter(listAdapter);

            publicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                    CurrentList currentList = CurrentList.getInstance();
                    currentList.setId(listsList.get(position).getId());
                    currentList.setName(listsList.get(position).getName());
                    currentList.setDate(listsList.get(position).getDate());
                    currentList.setItemsCount(listsList.get(position).getItemsCount());
                    Intent intent = new Intent(PublicListsActivity.this, CurrentListActivity.class);
                    startActivityForResult(intent, LIST_REQUEST);
                }
            });
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public void onEndingItems(View view) {
        int lastPos = listsList.size() - 1;
        Log.i("List", String.valueOf(lastPos));
        CurrentList currentList = CurrentList.getInstance();
        currentList.setId(listsList.get(lastPos).getId());
        currentList.setName(listsList.get(lastPos).getName());
        currentList.setDate(listsList.get(lastPos).getDate());
        currentList.setItemsCount(listsList.get(lastPos).getItemsCount());
        Intent intent = new Intent(this, CurrentListActivity.class);
        startActivityForResult(intent, LIST_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_LIST_REQUEST && resultCode == RESULT_OK) {
		    getData();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
