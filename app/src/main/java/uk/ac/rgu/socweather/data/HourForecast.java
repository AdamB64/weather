package uk.ac.rgu.socweather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.PrivateKey;

/**
 * This class provides details of the weather forecast for a specific hour of a specific day.
 */
@Entity(tableName = "HourForecast")
public class HourForecast {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    // the forecasted temp in degress celcius
    private double temperature;

    // the forecasted humidity in % (between 0 and 100)
    private int humidity;

    // the hour of the date that the forecast applies for (between 0 and 23)
    private int hour;

    // the date the forecast applies for, formatted day-month
    private String date;

    // the weather description
    private String weather;

    // the weather icon
    private String iconURL;

    // the location the forecast is for
    private String location;

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    /**
     * Default constructor
     */
    public HourForecast() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "HourForecast{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", hour=" + hour +
                ", date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                ", iconURL='" + iconURL + '\'' +
                ", location='"+ location+'\''+
                '}';
    }
}
