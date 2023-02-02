package com.example.weatherreport.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Weather.class},version = 1,exportSchema = false)//关联数据库
public abstract class WeatherDatabase extends RoomDatabase {
    //用户只需要操作DAO
    public abstract WeatherDao get_weather_dao();
    //单例模式
    private static WeatherDatabase weatherDatabase;
    public static synchronized WeatherDatabase getInstance(Context context){
        if(weatherDatabase == null){
            weatherDatabase = Room.databaseBuilder(context.getApplicationContext(),WeatherDatabase.class,"student_database")
                    .allowMainThreadQueries() //主线程也能操作数据库 只能测试用
                    .build();
        }
        return weatherDatabase;
    }
}

