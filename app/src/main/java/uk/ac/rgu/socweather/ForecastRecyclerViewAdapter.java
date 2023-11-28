package uk.ac.rgu.socweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.rgu.socweather.data.HourForecast;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.HourForecastViewHolder> {

    // member variable for storing the context
    private Context mContext;

    // member variable for storing the data
    private List<HourForecast> mHourForecasts;

    public ForecastRecyclerViewAdapter(Context context, List<HourForecast> data){
        super();
        this.mContext = context;
        this.mHourForecasts = data;
    }

    public void setHourForecasts(List<HourForecast> data){
        this.mHourForecasts =data;
    }

    @NonNull
    @Override
    public ForecastRecyclerViewAdapter.HourForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hourForecastListItemView = LayoutInflater.from(this.mContext).inflate(R.layout.hour_forecast_list_item, parent, false);
        HourForecastViewHolder viewHolder = new HourForecastViewHolder(hourForecastListItemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRecyclerViewAdapter.HourForecastViewHolder holder, int position) {

        // get the HourForecast to be displayed
        HourForecast hourForecast = this.mHourForecasts.get(position);

        // get the hour_forecast_list_item View from the holder
        View hourForecastListItemView = holder.mHourForecastListItemView;

        // set all the TextView values
        // set the date
        TextView tvForecastDate = hourForecastListItemView.findViewById(R.id.tvForecastDate);
        tvForecastDate.setText(hourForecast.getDate());

        // set the time
        TextView tvForecastTime = hourForecastListItemView.findViewById(R.id.tvForecastTime);
        String forecastTime = this.mContext.getString(R.string.tv_forecast_item_hour, hourForecast.getHour());
        tvForecastTime.setText(forecastTime);

        // set the temperature
        TextView tvForecastTemp = hourForecastListItemView.findViewById(R.id.tvForecastTemp);
        tvForecastTemp.setText(
                this.mContext.getString(R.string.tv_forecast_item_temp, String.valueOf(hourForecast.getTemperature()))
        );

        // set the humidity
        TextView tvForecastHumidity = hourForecastListItemView.findViewById(R.id.tvForecastHumidity);
        tvForecastHumidity.setText(
                this.mContext.getString(R.string.tv_forecast_item_humidity, hourForecast.getHumidity())
        );

        // set the weather
        TextView tvForecastWeather = hourForecastListItemView.findViewById(R.id.tvForecastWeather);
        tvForecastWeather.setText(hourForecast.getWeather());

    }

    @Override
    public int getItemCount() {
        if (this.mHourForecasts == null)
            return 0;
        return this.mHourForecasts.size();
    }


    class HourForecastViewHolder extends RecyclerView.ViewHolder {
        private View mHourForecastListItemView;
        private ForecastRecyclerViewAdapter mAdapter;

        public HourForecastViewHolder(View hourForecastListItemView, ForecastRecyclerViewAdapter adapter){
            super(hourForecastListItemView);
            this.mHourForecastListItemView = hourForecastListItemView;
            this.mAdapter = adapter;
        }


    }
}