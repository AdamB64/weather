package uk.ac.rgu.socweather;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rgu.socweather.data.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationConfirmationFragment extends Fragment implements View.OnClickListener {

    // parameter argument names
    public static final String ARG_PARAM_LOCATION = "location";
    public static final String ARG_PARAM_NUMBER_OF_DAYS = "numberOfDays";

    // paramaters
    private String mLocation;
    private int mNumberOfDays;

    public LocationConfirmationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param location The location to confirm
     * @param numberOfDays The number of days to get the forecast for.
     * @return A new instance of fragment LocationConfirmationFragment.
     */
    public static LocationConfirmationFragment newInstance(String location, int numberOfDays) {
        LocationConfirmationFragment fragment = new LocationConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_LOCATION, location);
        args.putInt(ARG_PARAM_NUMBER_OF_DAYS, numberOfDays);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mLocation = getArguments().getString(ARG_PARAM_LOCATION);
            this.mNumberOfDays = getArguments().getInt(ARG_PARAM_NUMBER_OF_DAYS);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // for navigate to the forecast fragment
        Button btnGetForecastLocationConfirm = view.findViewById(R.id.btnGetForecastLocationConfirm);
        btnGetForecastLocationConfirm.setOnClickListener(this);

        // add click listeners for alernative lists
        Button btnBasicListView = view.findViewById(R.id.btnGetForecastBasicList);
        btnBasicListView.setOnClickListener(this);

        Button btnCustomListView = view.findViewById(R.id.btnGetForecastCustomList);
        btnCustomListView.setOnClickListener(this);

        TextView tvConfirmLocationLabel = view.findViewById(R.id.tvConfirmLocationLabel);
        tvConfirmLocationLabel.setText(getString(R.string.tvConfirmLocationLabelLoading, mLocation));

        downloadLocationOptions(view);
    }

    /**
     * Uses the weatherapi web service to get the potential locations matching
     * the name provided by the user and uses them to populoate the spinner
     */
    private void downloadLocationOptions(View view){
        // get the suitable / candidate locations from the web service
        Uri uri = Utils.buildUri("https://api.weatherapi.com/v1/search.json?key=a3b9cc3fb35943d5826152257210311", "q", mLocation);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // convert response to a JSON Object
                        List<String> locations = new ArrayList<String>();
                        try {
                            JSONArray rootObj = new JSONArray(response);
                            for (int i = 0, j = rootObj.length(); i<j; i++){
                                JSONObject locationOjb = rootObj.getJSONObject(i);
                                if (locationOjb.has("name")) {
                                    String name = locationOjb.getString("name");
                                    locations.add(name);
                                }
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), getString(R.string.error_downloading_locations), Toast.LENGTH_LONG);
                        } finally {
                            if (locations.size() > 0) {
                                // we have some locations so display them
                                // update the spinner by creating an ArrayAdapter
                                Spinner spinner = view.findViewById(R.id.spConfirmLocation);
                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                                        getContext(), android.R.layout.simple_spinner_item, locations);
                                spinner.setAdapter(spinnerAdapter);

                                // prompt for selection
                                TextView tvConfirmLocationLabel = view.findViewById(R.id.tvConfirmLocationLabel);
                                tvConfirmLocationLabel.setText(R.string.tvConfirmLocationLabel);

                                // allow the user to take action
                                Button btnGetForecastLocationConfirm = view.findViewById(R.id.btnGetForecastLocationConfirm);
                                btnGetForecastLocationConfirm.setEnabled(true);
                                Button btnBasicListView = view.findViewById(R.id.btnGetForecastBasicList);
                                btnBasicListView.setEnabled(true);
                                Button btnCustomListView = view.findViewById(R.id.btnGetForecastCustomList);
                                btnCustomListView.setEnabled(true);
                            } else {
                                TextView tvConfirmLocationLabel = view.findViewById(R.id.tvConfirmLocationLabel);
                                tvConfirmLocationLabel.setText(getString(R.string.tv_confirm_location_no_locations, mLocation));
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.error_downloading_locations), Toast.LENGTH_LONG);
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void onClick(View view) {
        // get the selection from the spinner
        Spinner spinner = getView().findViewById(R.id.spConfirmLocation);
        String location = spinner.getSelectedItem().toString();

        // for now just pass on what was received here
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_LOCATION, location);
        bundle.putInt(ARG_PARAM_NUMBER_OF_DAYS, this.mNumberOfDays);

        if (view.getId() == R.id.btnGetForecastLocationConfirm){
            // navigate to the forecast fragment
            Navigation.findNavController(view).navigate(R.id.action_locationConfirmationFragment_to_forecastFragment, bundle);
        } else if (view.getId() == R.id.btnGetForecastBasicList){
            Navigation.findNavController(view).navigate(R.id.action_locationConfirmationFragment_to_basicListViewFragment, bundle);
        }  else if (view.getId() == R.id.btnGetForecastCustomList){
            Navigation.findNavController(view).navigate(R.id.action_locationConfirmationFragment_to_customListViewFragment, bundle);
        }
    }
}