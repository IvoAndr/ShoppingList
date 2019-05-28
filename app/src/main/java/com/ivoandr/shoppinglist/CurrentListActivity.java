package com.ivoandr.shoppinglist;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CurrentListActivity extends AppCompatActivity {
    private CurrentList currentList = CurrentList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabDelList = findViewById(R.id.fabDelList);
        if (currentList.getId() != 1) {
            fabDelList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = LinksManager.getInstance().getDelList(currentList.getId());
                    ConnectionManager connectionManager = new ConnectionManager(CurrentListActivity.this, url);
                    connectionManager.execute();

                    Snackbar.make(view, url, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        else {
            fabDelList.hide();
        }

        FloatingActionButton fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = currentList.getName() + " " + currentList.getFormattedDate();
        setTitle(title);

        getData();
    }

    private void getData() {
//        swipeRefreshLayout.setRefreshing(true);
        String url = LinksManager.getInstance().getListByDate(currentList.getId());
        final ArrayList<Item> items = new ArrayList<>();

        ConnectionManager connectionManager = new ConnectionManager(this, url);
        connectionManager.execute();

        try {
            String result = connectionManager.get();
            items.clear();

            try {
                JSONObject data;
                data = new JSONObject(result);
                int length = data.getJSONArray("items").length();
                Gson gson = new Gson();

                Log.i("Data", data.toString());

                for (int i = 0; i < length; i++) {
                    Item currentItem = gson.fromJson(data.getJSONArray("items").getJSONObject(i).toString(), Item.class);
                    items.add(currentItem);
                }

                currentList.setItems(items);

//                swipeRefreshLayout.setRefreshing(false);
                printItems();
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

    private void printItems() {
        final ArrayList<Item> items = currentList.getItems();
        ArrayList<String> itemsStringArray = new ArrayList<>();
        double totalPrice = 0.0;

        for (Item item : items) {
            String itemTitle = item.getItem()
                    + ", " + item.getQuantity()
                    + item.getQuantityType()
                    + ", " + item.getPrice()
                    + "лв. Общо: " + item.getQuantity() * item.getPrice() + "лв.";
            itemsStringArray.add(itemTitle);

            totalPrice += item.getPrice() * item.getQuantity();
        }

        TextView totalPriceView = findViewById(R.id.totalPriceView);
        String totalPriceString = totalPrice + "лв.";
        totalPriceView.setText(totalPriceString);

        Log.i("listData", itemsStringArray.toString());

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsStringArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = view.findViewById(android.R.id.text1);

                if (items.get(position).getStatus() == 1) {
//                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    textView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
                else if (items.get(position).getStatus() == 2) {
                    textView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                else if (items.get(position).getStatus() == 0) {
                    textView.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                }

                return view;
            }
        };

        try {
            ListView currentListItemsView = findViewById(R.id.currentListItems);
            currentListItemsView.setAdapter(itemsAdapter);

            currentListItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//                    CurrentList currentList = CurrentList.getInstance();
//                    currentList.setId(listsList.get(position).getId());
//                    currentList.setName(listsList.get(position).getName());
//                    currentList.setDate(listsList.get(position).getDate());
//                    currentList.setItemsCount(listsList.get(position).getItemsCount());
//                    Intent intent = new Intent(PublicListsActivity.this, CurrentListActivity.class);
//                    startActivityForResult(intent, LIST_REQUEST);
                }
            });
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
