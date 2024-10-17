package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatFactViewModel extends ViewModel {
    private MutableLiveData<List<Fact>> catFactsLiveData = new MutableLiveData<>();

    public LiveData<List<Fact>> getCatFacts() {
        return catFactsLiveData;
    }

    public void fetchCatFacts() {
        CatFactsAPI catFactApi = RetrofitInstance.getRetrofitInstance().create(CatFactsAPI.class);
        Call<List<Fact>> call = catFactApi.getCatFacts();

        call.enqueue(new Callback<List<Fact>>() {
            @Override
            public void onResponse(Call<List<Fact>> call, Response<List<Fact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    catFactsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Fact>> call, Throwable t) {
                // Handle failure
                catFactsLiveData.postValue(null);
            }
        });
    }
}
