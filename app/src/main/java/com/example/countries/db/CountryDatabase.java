package com.example.countries.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.countries.model.Country;
import com.example.countries.utils.DataConverters;

@Database(entities = {Country.class}, version = 7, exportSchema = false)
@TypeConverters(DataConverters.class)
public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDAO countryDAO();

    private static CountryDatabase mInstance;

    public static synchronized CountryDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    CountryDatabase.class, "Country_Database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(mInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(CountryDatabase instance) {
            CountryDAO dao = instance.countryDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
