package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements TitleFragment.TitleFragmentInterface, DetailsFragment.DetailsFragmentInterface {
    //ДЛЯ ПОЛУЧЕНИЯ ЛОКАЦИИ
    FusedLocationProviderClient fusedLocationProviderClient;
    int counter = 0;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                5);

        //устанавливаем TitleFragment при создании Activity
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_container, new TitleFragment(), TitleFragment.TITLE_FRAGMENT_TAG)
                .commit();
        //ДЛЯ ПОЛУЧЕНИЯ ЛОКАЦИИ
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (counter >= 3) {
                    stopLocationUpdates();
                    return;
                }
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.i("LOCATION", location.getLatitude() + " ");
                }
                counter++;
            }
        };
        //ПРИМЕР СОХРАНЕНИЯ В SHARED PREFERENCES
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("big_value", 1);

        // Выполняется синхронно
        // editor.commit();

        // Выполняется асинхронно
        editor.apply();
    }



    @Override
    protected void onResume() {
        super.onResume();
        getLastPosition();
    }

    //реализуем метод интерфйса TitleFragmentInterface
    //в этом случае клик кнопки в TitleFragment вызывает
    //создание DetailsFragment и переход к нему
    //попутно передаем данные
    @Override
    public void onTitleViewsClickListener(int id, String text) {
        //создаем экземпляр фрагмента
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        //передаем строку в DetailsFragment
        //можно сделать это во время создания DetailsFragment
        //для этого используем newInstance
        bundle.putString(TitleFragment.TITLE_FRAGMENT_TAG, text);
        detailsFragment.setArguments(bundle);

        //переход к DetailsFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, detailsFragment, DetailsFragment.DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    //реализуем метод интерфйса DetailFragmentInterface
    //в этом случае клик кнопки в DetailsFragment вызывает
    //создание TitleFragment и переход к нему
    @Override
    public void onDetailsViewsListener(int id) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, new TitleFragment(), TitleFragment.TITLE_FRAGMENT_TAG)
                .commit();
    }

    /*  1. Получить последнюю позицию пользователя;
        2. Сохранить её в SharedPreferences;
        3. Запросить обновление геопозиции
        4. После того, как геопозиция обновиться 3 раза,
        вновь сохранить самую актуальную геопозицию,
        сравнив её с уже сохранённой и остановить
        обновления.*/

    public void getLastPosition() {
        Log.i("LOCATION", "LOCATION");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i("LOCATION", "ACCESS_FINE_LOCATION GRANTED");
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.i("LOCATION", location.getLatitude() + "");
                            } else {
                                Log.i("LOCATION", "Empty");
                            }
                        }
                    });
        }
    }

    public void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }

    }

    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 5: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}