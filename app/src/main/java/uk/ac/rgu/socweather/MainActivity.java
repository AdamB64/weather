package uk.ac.rgu.socweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import uk.ac.rgu.socweather.data.HourForecast;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create some sample data
        HourForecast hf = new HourForecast();
        hf.setTemperature(32);

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle settings item selection
       if (item.getItemId()  == R.id.mi_appBarSetting){
            NavController navController = Navigation.findNavController(findViewById(R.id.fragmentContainerView));
            // work out where the user currently is
            int currentFragmentId = navController.getCurrentDestination().getId();
            // if that is different from the settings fragment
            if (currentFragmentId != R.id.settingsFragment) {
                navController.navigate(R.id.settingsFragment);
                return true;
            }
            return super.onOptionsItemSelected(item);
        } else {
           return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(findViewById(R.id.fragmentContainerView));
        // work out where the user currently is
        int currentFragmentId = navController.getCurrentDestination().getId();

        if (item.getItemId() == R.id.mi_bottomNavLocationSelection) {
            // navigate "home" to the location select fragment
            if (currentFragmentId != R.id.locationSelectionFragment) {
                navController.navigate(R.id.locationSelectionFragment);
                return true;
            }
        } else if (item.getItemId() == R.id.mi_bottomNavSetting){
                    // navigate to settings fragment
                if (currentFragmentId != R.id.settingsFragment){
                    navController.navigate(R.id.settingsFragment);
                    return true;
                }
        }
        return false;
    }
}