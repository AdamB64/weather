package uk.ac.rgu.socweather;

import static uk.ac.rgu.socweather.R.string.tv_forecast_item_humidity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import uk.ac.rgu.socweather.data.HourForecast;

/**
 *  Array adapter for listview to diaplsy hour forecast object
 */

public class HourForecastArrayAdapter extends ArrayAdapter<HourForecast>{
    //the data to be displayed
    private List<HourForecast> mhourforecast;

    /*
     *Create a new {@Link HourForecastArrayAdapter} to display objects
     * @param content
     * @param resource
     */
    //public HourForecastArrayAdapter(@NonNull Context context, int resource) {
       // super(context, resource);
    //}

    /**
     * sets the data to be displayed
     */
    //public void sethourforecast(List<HourForecast> hourForecasts){
    //    this.mhourforecast=hourForecasts;
    //}

    /*
    *Create a new {@Link HourForecastArrayAdapter} to display objects
    * @param content
    * @param resouce
    * @param objects the {@Link HourForecast} object to be displayed
     */
    public HourForecastArrayAdapter(@NonNull Context context, int resource, List<HourForecast> objects) {
        super(context, resource,objects);
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //we need to set up convertView to display this.mHourForecast.get(position)
        View itemview =convertView;
        if (itemview==null){
            itemview= LayoutInflater.from(getContext()).inflate(R.layout.hour_forecast_list_item,parent,false);
        }

        //if(this.mhourforecast==null){
            //return itemview;
        //}

        // get the HourForecast to display
        HourForecast hourForecast = getItem(position);

        // update itemView

        // with the date
        TextView tvDate = itemview.findViewById(R.id.tvForecastDate);
        tvDate.setText(hourForecast.getDate());

        // with the time
        TextView tvTime = itemview.findViewById(R.id.tvForecastTime);
        String strTime = getContext().getString(R.string.tv_forecast_item_hour,hourForecast.getHour());
        tvTime.setText(strTime);

        // with the temperature
        ((TextView)
                itemview.findViewById(R.id.tvForecastTemp))
                .setText(
                        getContext().getString(
                                R.string.tv_forecast_item_temp,
                                String.valueOf(hourForecast.getTemperature())));

        // with the humidity
        ((TextView)
                itemview.findViewById(R.id.tvForecastHumidity))
                .setText(
                        getContext().getString(
                                R.string.tv_forecast_item_humidity,
                                hourForecast.getHumidity()));
        // with the weather
        ((TextView)
                itemview.findViewById(R.id.tvForecastWeather))
                .setText(hourForecast.getWeather());

        return itemview;
    }
}
