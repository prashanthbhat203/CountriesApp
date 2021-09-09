package com.example.countries.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.countries.db.CountryRepository;
import com.example.countries.model.Country;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private final CountryRepository repository;
    private final LiveData<List<Country>> allCountry;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository(application);
        allCountry = repository.getAllCountry();
    }

    public void insert(Country country) {
        repository.insertCountry(country);
    }

    public LiveData<List<Country>> getAllCountry() {
        return allCountry;
    }

    public void deleteAllCountry() {
        repository.deleteAllCountry();
    }
}
