package uk.ac.rgu.socweather;

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
 * ArrayAdapter for {@link android.widget.ListView}s to display {@link HourForecast} objects
 */
import uk.ac.rgu.socweather.data.HourForecast;

/**
 * ArrayAdapter for {@link android.widget.ListView}s to display {@link HourForecast} objects
 */
public class HourForecastArrayAdapter extends ArrayAdapter<HourForecast> {

    // the data to be displayed
    private List<HourForecast> mHourForecasts;


    /**
     * Create a new {@link HourForecastArrayAdapter} to display objects
     * @param context
     * @param resource
     * @param objects The {@link HourForecast} objects to be displayed
     */
    public HourForecastArrayAdapter(@NonNull Context context, int resource, List<HourForecast> objects) {
        super(context, resource, objects);
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
        // we need to setup convertView to display this.mHourForecasts.get(position)

        // get the view that will be used to display the weather forecast
        View itemView = convertView;
        if (itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.hour_forecast_list_item, parent,false );
        }

        // get the HourForecast to display
        HourForecast hourForecast = getItem(position);

        // update itemView

        // with the date
        TextView tvDate = itemView.findViewById(R.id.tvForecastDate);
        tvDate.setText(hourForecast.getDate());

        // with the time
        TextView tvTime = itemView.findViewById(R.id.tvForecastTime);
        String strTime = getContext().getString(R.string.tv_forecast_item_hour,hourForecast.getHour());
        tvTime.setText(strTime);

        // with the temperature
        ((TextView)
                itemView.findViewById(R.id.tvForecastTemp))
                .setText(
                        getContext().getString(
                                R.string.tv_forecast_item_temp,
                                String.valueOf(hourForecast.getTemperature())));

        // with the humidity
        ((TextView)
                itemView.findViewById(R.id.tvForecastHumidity))
                .setText(
                        getContext().getString(
                                R.string.tv_forecast_item_humidity,
                                hourForecast.getHumidity()));
        // with the weather
        ((TextView)
                itemView.findViewById(R.id.tvForecastWeather))
                .setText(hourForecast.getWeather());

        return itemView;
    }
}