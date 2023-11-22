package uk.ac.rgu.socweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    }

    @Override
    public void onClick(View view) {
        // for now just pass on what was received here
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_LOCATION, this.mLocation);
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