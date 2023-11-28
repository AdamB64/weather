package uk.ac.rgu.socweather.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HourForecast.class},version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract HourForecastDAO hourForecastDAO();

    public static WeatherDatabase INSTANCE;

    public static WeatherDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (WeatherDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context, WeatherDatabase.class,"weatherDatabse")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
