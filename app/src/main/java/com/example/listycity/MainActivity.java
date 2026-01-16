package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> cityList;
    private ArrayAdapter<String> adapter;
    private ListView cityListView;

    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityListView = findViewById(R.id.city_list);
        Button addCityButton = findViewById(R.id.add_city_button);
        Button deleteCityButton = findViewById(R.id.delete_city_button);

        // Starter cities (optional)
        cityList = new ArrayList<>();
        cityList.add("Edmonton");
        cityList.add("Calgary");
        cityList.add("Vancouver");

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                cityList
        );
        cityListView.setAdapter(adapter);
        cityListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Tap to select a city
        cityListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityListView.setItemChecked(position, true);
        });

        // ADD CITY button
        addCityButton.setOnClickListener(v -> {
            EditText input = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setMessage("Enter city name")
                    .setView(input)
                    .setPositiveButton("CONFIRM", (dialog, which) -> {
                        String cityName = input.getText().toString().trim();
                        if (!cityName.isEmpty()) {
                            cityList.add(cityName);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        });

        // DELETE CITY button
        deleteCityButton.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                cityList.remove(selectedPosition);
                adapter.notifyDataSetChanged();

                selectedPosition = -1;
                cityListView.clearChoices();
            }
        });
    }
}
