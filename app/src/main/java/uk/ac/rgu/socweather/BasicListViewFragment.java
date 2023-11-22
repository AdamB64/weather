package uk.ac.rgu.socweather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.rgu.socweather.data.HourForecast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicListViewFragment extends Fragment {
    // tag for loggging message
    private static final String TAG = "ForecastFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BasicListViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static uk.ac.rgu.socweather.ForecastFragment newInstance(String param1, String param2) {
        uk.ac.rgu.socweather.ForecastFragment fragment = new uk.ac.rgu.socweather.ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadForecast(view);
    }

    private void downloadForecast(View view) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=a3b9cc3fb35943d5826152257210311&q=Aberdeen&days=3";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                // for formatting the date of the forecast
                SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.forecast_date_format));

                // for storing all the weather forecast
                List<HourForecast> forecastList = new ArrayList<HourForecast>(24 * 5);

                try {
                    // convert text response to a JSON object for processing
                    JSONObject rootObj = new JSONObject(response);
                    // get the forecast value
                    JSONObject forecastObject = rootObj.getJSONObject("forecast");
                    // get the forecast day value - an array of days
                    JSONArray forecastDayArray = forecastObject.getJSONArray("forecastday");
                    // for every forecast day
                    for (int i = 0, j = forecastDayArray.length(); i < j; i++) {
                        // get the day at position i
                        JSONObject dayObject = forecastDayArray.getJSONObject(i);
                        // from the day, get the hour array
                        JSONArray hourArray = dayObject.getJSONArray("hour");
                        for (int ii = 0, jj = hourArray.length(); ii < jj; ii++) {
                            // get the forecast hour object
                            JSONObject forecastHourObject = hourArray.getJSONObject(ii);
                            // now extract the forecast info
                            double temperature = forecastHourObject.getDouble("temp_c");
                            int humidity = forecastHourObject.getInt("humidity");
                            // get the condition object to work out the weather description
                            JSONObject conditionObject = forecastHourObject.getJSONObject("condition");
                            // get the weather description
                            String weather = conditionObject.getString("text");
                            // get the weather icon
                            String weatherIcon = conditionObject.getString("icon");

                            // work out the date and time
                            long timeEpoch = forecastHourObject.getLong("time_epoch");
                            Calendar calendar = Calendar.getInstance();
                            // the time in the forecast json is in seconds, so convert to millisecond
                            calendar.setTimeInMillis(timeEpoch * 1000);

                            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                            // format the date for display
                            String dayMonth = sdf.format(calendar.getTime());

                            // create the HourForecast domain object for this hour
                            HourForecast hourForecast = new HourForecast();
                            hourForecast.setTemperature(temperature);
                            hourForecast.setHumidity(humidity);
                            hourForecast.setWeather(weather);
                            hourForecast.setIconURL(weatherIcon);
                            hourForecast.setHour(hourOfDay);
                            hourForecast.setDate(dayMonth);

                            // add this hour forecast to the list
                            forecastList.add(hourForecast);
                        }
                    }
                } catch (JSONException e) {
                    // display an error on the Toast and label text view
                    Toast.makeText(getContext(), R.string.error_parsing_forecast, Toast.LENGTH_LONG);
                    ((TextView) getActivity().findViewById(R.id.tvForecastLabel)).setText(R.string.error_parsing_forecast);

                    Log.d(TAG, "Parsing JSON error" + e.getLocalizedMessage());
                    e.printStackTrace();
                } finally {
                    // do something the forecasts that have been downloaded
                    Log.e(TAG, "Downloaded " + forecastList.size() + " forecasts");
                    // remove the spinner
                    ProgressBar pg = view.findViewById(R.id.pb_forecastFragment);
                    pg.setVisibility(View.GONE);

                    // if we have some data, then enable the relevant Views
                    if (forecastList.size() > 0) {
                        // display the forecast list
                        ListView lv = view.findViewById(R.id.lvBasicForecast);
                        lv.setVisibility(View.VISIBLE);

                        // add the contents of forecastList to lv
                        ArrayAdapter<HourForecast> adapter = new ArrayAdapter<HourForecast>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                forecastList
                        );
                        lv.setAdapter(adapter);

                        // enable the buttons for sharing
                        getActivity().findViewById(R.id.btnShareForecast).setEnabled(true);
                        getActivity().findViewById(R.id.btnShowLocationMap).setEnabled(true);
                        getActivity().findViewById(R.id.btnCheckForecastOnline).setEnabled(true);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.error_downloading_forecast, Toast.LENGTH_LONG);
                ((TextView) getActivity().findViewById(R.id.tvForecastLabel)).setText(R.string.error_downloading_forecast);
                Log.e(TAG, "Error downloading " + error.getMessage());
            }
        });
        // create a new RequestQueue
        RequestQueue rq = Volley.newRequestQueue(getContext());
        // add the request to make it
        rq.add(request);
    }
}