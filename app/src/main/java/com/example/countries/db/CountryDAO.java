package com.example.countries.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.countries.model.Country;

import java.util.List;

@Dao
public interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertCountry(Country country);

    @Query("SELECT * FROM country_table")
    LiveData<List<Country>> getAllCountry();

    @Query("DELETE FROM country_table")
    void deleteAllCountry();
}
