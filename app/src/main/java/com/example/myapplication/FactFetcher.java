package com.example.myapplication;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FactFetcher {
    List<Fact> facts;
    public void fetchCatFacts() {
        CatFactsAPI api = RetrofitInstance.getRetrofitInstance().create(CatFactsAPI.class);
        Call<List<Fact>> call = api.getCatFacts();

        call.enqueue(new Callback<List<Fact>>() {

            @Override
            public void onResponse(Call<List<Fact>> call, Response<List<Fact>> response) {
                if (response.isSuccessful()) {
                    facts = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Fact>> call, Throwable t) {
                Log.e("MainActivity", "Error fetching cat facts", t);
            }
        });
    }
}
