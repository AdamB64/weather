package uk.ac.rgu.socweather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationSelectionFragment extends Fragment implements View.OnClickListener {

    public LocationSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocationSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationSelectionFragment newInstance() {
        LocationSelectionFragment fragment = new LocationSelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // for navigating to the LocationConfirmationFragment
        Button getForecastButton = view.findViewById(R.id.btnGetForecast);
        getForecastButton.setOnClickListener(this);

        // for navigating to the ForecastFragment
        Button gpsForecastButton = view.findViewById(R.id.btnGpsForecast);
        gpsForecastButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // get the nav contoller
       NavController navController = Navigation.findNavController(view);

           //get the number of days to get the forecast for
            EditText etNumberOfDays = getActivity().findViewById(R.id.editTextNumber);
            String etNumberOfDaysString = etNumberOfDays.getText().toString();
            if("".equals(etNumberOfDaysString)){
                //should probably change the text colour of the relevant field
                //but for now will just display a Toast message
                Toast.makeText(getContext(), R.string.Toast_enter_num_days_error, Toast.LENGTH_LONG).show();
                return;
            }
            int numberOfDays = Integer.parseInt(etNumberOfDaysString);

         if (view.getId() ==R.id.btnGetForecast) {
             //check / get the location entered
             String locationEntered= ((EditText)getActivity().findViewById(R.id.etEnterLocation)).getText().toString();
             if("".equals(locationEntered)){
                 //should probably change the text colour of the relevant field
                 //but for now will just display a Toast message
                 Toast.makeText(getContext(), R.string.Toast_Provide_location_error, Toast.LENGTH_LONG).show();
                 return;
             }

             //build the bundle to send
             Bundle bundle = new Bundle();
             bundle.putString(LocationConfirmationFragment.ARG_PARAM_LOCATION,locationEntered);
             bundle.putInt(LocationConfirmationFragment.ARG_PARAM_NUMBER_OF_DAYS,numberOfDays);


             // navigate to the LocationConfirmationFragment
             navController.navigate(R.id.action_locationSelectionFragment_to_locationConfirmationFragment, bundle);

         } else if (view.getId() == R.id.btnGpsForecast) {
             // TODO: get the location from GPS
             // navigate to the Forecast Fragment
             navController.navigate(R.id.action_locationSelectionFragment_to_forecastFragment);
         }

    }
}