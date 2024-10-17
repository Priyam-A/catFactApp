package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private CatFactAdapter catFactAdapter;
    private CatFactViewModel catFactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        catFactAdapter = new CatFactAdapter();
        viewPager.setAdapter(catFactAdapter);

        // Initialize ViewModel
        catFactViewModel = new ViewModelProvider(this).get(CatFactViewModel.class);

        // Observe LiveData
        catFactViewModel.getCatFacts().observe(this, catFacts -> {
            if (catFacts != null) {
                catFactAdapter.setCatFacts(catFacts);
            }
        });

        // Fetch Cat Facts
        catFactViewModel.fetchCatFacts();
    }
}
