package uk.ac.rgu.socweather.data;

/**
 * This class provides details of the weather forecast for a specific hour of a specific day.
 */
public class HourForecast {

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

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    //the weather icon
    private String iconURL;

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
}
