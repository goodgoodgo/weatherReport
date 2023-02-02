package com.example.weatherreport.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class DBEngine {
    private static final String TAG = "DBEngine";

    private WeatherDao weatherDao;

    public DBEngine(Context context) {

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(context);
        weatherDao = weatherDatabase.get_weather_dao();

    }
    //插入
    public void insert_weather(Weather ... weather) {
        new InsertAsynTask(weatherDao).execute(weather);
    }
    //更新
    public void update_weather(Weather ... weather) {
        new UpdateAsynTask(weatherDao).execute(weather);
    }
    //删除
    public void delete_weather(Weather ... weather) {
        new DeleteAsynTask(weatherDao).execute(weather);
    }
    //全部删除
    public void delete_all_weather(Weather ... weather) {
        new DeleteAllAsynTask(weatherDao).execute();
    }
    //全部查询
    public void quary_all_weather(Weather ... weather) {
        new QuaryAllAsynTask(weatherDao).execute();
    }


    //开启异步操作
    static class InsertAsynTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public InsertAsynTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather ... weathers) {
            weatherDao.insert_weather(weathers);
            return null;
        }
    }
    static class UpdateAsynTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public UpdateAsynTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weathers) {
            this.weatherDao.update_weather(weathers);
            return null;
        }
    }
    static class DeleteAsynTask extends AsyncTask<Weather,Void,Void> {
        private WeatherDao weatherDao;
        public DeleteAsynTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Weather... weathers) {
            this.weatherDao.delete_weather(weathers);
            return null;
        }
    }
    //全部删除
    static class DeleteAllAsynTask extends AsyncTask<Void,Void,Void> {
        private WeatherDao weatherDao;
        public DeleteAllAsynTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            this.weatherDao.delete_all_weather();
            return null;
        }
    }
    static class QuaryAllAsynTask extends AsyncTask<Void,Void,Void> {

        private WeatherDao weatherDao;
        public QuaryAllAsynTask(WeatherDao weatherDao) {
            this.weatherDao = weatherDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Weather> all_weather = this.weatherDao.get_all_weather();
            //遍历全部查询的结果
            for (Weather weather:all_weather)
            {
                Log.i(TAG, "doInBackground: "+weather.toString());
            }
            return null;
        }
    }
}

