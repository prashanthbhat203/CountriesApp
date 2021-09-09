package com.example.countries;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.countries.adapters.CountryAdapter;
import com.example.countries.api.RetrofitClient;
import com.example.countries.databinding.ActivityMainBinding;
import com.example.countries.model.Country;
import com.example.countries.viewModel.CountryViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private CountryViewModel countryViewModel;
    CountryAdapter countryAdapter = new CountryAdapter();
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builder = new AlertDialog.Builder(this);

        binding.rvCountry.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCountry.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvCountry.setAdapter(countryAdapter);

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);

        makeNetworkRequest();
    }

    private void makeNetworkRequest() {
        Call<List<Country>> call = RetrofitClient.getInstance().getCountryAPI().getCountryList();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(@NonNull Call<List<Country>> call, @NonNull Response<List<Country>> response) {
                List<Country> countries = response.body();
                Log.d("Response", response.message());

                assert countries != null;
                for (Country country : countries) {
                    int id = country.getId();
                    String name = country.getName();
                    String capital = country.getCapital();
                    String region = country.getRegion();
                    List<String> flag = country.getFlags();
                    int population = country.getPopulation();
                    List<String> borders = country.getBorders();

                    Country cModel = new Country(id, name, capital, region, population,
                            borders, flag);
                    countryViewModel.insert(cModel);
                    countryAdapter.asyncListDiffer.submitList(countries);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Country>> call, @NonNull Throwable t) {
                Log.d("ErrorResponse", t.getMessage());

                countryViewModel.getAllCountry().observe(MainActivity.this,
                        countries -> countryAdapter.asyncListDiffer.submitList(countries));
            }
        });

        binding.btnDeleteAll.setOnClickListener(view -> {
            builder.setMessage("Are you sure want to delete all?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        countryViewModel.deleteAllCountry();
                        Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog alert = builder.create();
            alert.show();

        });
    }
}