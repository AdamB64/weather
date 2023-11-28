package uk.ac.rgu.socweather.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HourForecastDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void Insert(HourForecast hf);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void Insert(List<HourForecast> hf);

    @Delete
    public void delete(HourForecast hf);

    @Delete
    public void delete(List<HourForecast> hf);

    @Query("SELECT * FROM hourforecast WHERE location LIKE :location AND date LIKE :date ORDER BY hour ASC")
    public List<HourForecast> findByLocationDate(String location,String date);
}
