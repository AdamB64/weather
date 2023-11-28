package uk.ac.rgu.socweather.data;

import android.content.Context;

import java.util.List;

public class WeatherForecastRepository {

    private HourForecastDAO mHourForecastDAO;

    public WeatherForecastRepository(Context context){
        super();
        mHourForecastDAO = WeatherDatabase.getInstance(context).hourForecastDAO();
    }

    /**
     * stores hourforcast locally on the device in a roomSQL database
     * @param hourForecasts
     */
    public void storeHourForecasts(List<HourForecast> hourForecasts){
        this.mHourForecastDAO.Insert(hourForecasts);
    }

    /**
     * deletes hourforcast locally on the device in a roomSQL database
     * @param hourForecasts
     */
    public void deleteHourForecasts(List<HourForecast> hourForecasts){
        this.mHourForecastDAO.delete(hourForecasts);
    }

    /**
     * return any forecast stored for the location and date
     * @param location
     * @param date
     */
    public List<HourForecast> getHourForecasts(String location,String date){
        return this.mHourForecastDAO.findByLocationDate(location,date);
    }
}
