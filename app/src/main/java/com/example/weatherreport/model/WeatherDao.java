package com.example.weatherreport.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {
    //增
    @Insert
    void insert_weather(Weather ... weathers);

    //改
    @Update
    void update_weather(Weather ... weathers);

    //删
    @Delete
    void delete_weather(Weather ... weathers);

    //删
    @Query("DELETE FROM Weather")
    void delete_all_weather();

    //查
    @Query("SELECT * FROM Weather ORDER BY id DESC")
    List<Weather> get_all_weather();

}
