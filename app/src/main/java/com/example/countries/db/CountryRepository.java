package com.example.countries.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.countries.model.Country;

import java.util.List;

public class CountryRepository {

    private final CountryDAO countryDAO;
    private final LiveData<List<Country>> allCountry;

    public CountryRepository(Application application) {
        CountryDatabase database = CountryDatabase.getInstance(application);
        countryDAO = database.countryDAO();
        allCountry = countryDAO.getAllCountry();
    }

    public void insertCountry(Country country) {
        new InsertCountryAsyncTask(countryDAO).execute(country);
    }

    public LiveData<List<Country>> getAllCountry() {
        return allCountry;
    }

    public void deleteAllCountry() {
        new DeleteAllCrewAsyncTask(countryDAO).execute();
    }

    private static class InsertCountryAsyncTask extends AsyncTask<Country, Void, Void> {
        private final CountryDAO countryDAO;

        public InsertCountryAsyncTask(CountryDAO countryDAO) {
            this.countryDAO = countryDAO;
        }

        @Override
        protected Void doInBackground(Country... countries) {
            countryDAO.insertCountry(countries[0]);
            return null;
        }
    }

    private static class DeleteAllCrewAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CountryDAO dao;

        public DeleteAllCrewAsyncTask(CountryDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCountry();
            return null;
        }
    }
}
