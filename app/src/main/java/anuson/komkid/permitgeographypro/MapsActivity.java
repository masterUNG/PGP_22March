package anuson.komkid.permitgeographypro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private GoogleMap mMap;
    private String[] loginStrings;
    private LocationManager locationManager;
    private Criteria criteria;
    private Double userLatADouble= 0.0 , userLngADouble = 0.0;
    private String[] titleString;
    private final int[] index = {0};
    private Button button;
    private double[] latDoubles, lngDoubles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout_maps);

        setUpConstant();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }// Main Method

    private void buttonController() {

        button = (Button) findViewById(R.id.btnChooseSpinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("1MarchV1", "index ==> " + index[0]);

                LatLng latLng = new LatLng(latDoubles[index[0]], lngDoubles[index[0]]);
                moveCenterMap(latLng);

            }   // onClick
        });


    }

    private void setUpConstant() {
        loginStrings = getIntent().getStringArrayExtra("Login");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }

        Log.d("2febV3", "userLat ==> " + userLatADouble);
        Log.d("2febV3", "userLng ==> " + userLngADouble);


        createUserMarker();


    }   // onResume

    private void createUserMarker() {

        try {

            //Create Marker of User
            LatLng latLng = new LatLng(userLatADouble, userLngADouble);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bird48)));

        } catch (Exception e) {
            Log.d("2febV3", "e create Marker User ==> " + e.toString());
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        }

        return location;
    }


    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng;


        if (userLatADouble == 0.0) {
            latLng = new LatLng(13.842958, 100.491554);
        } else {
            latLng = new LatLng(userLatADouble, userLngADouble);
        }


        moveCenterMap(latLng);

        try {

            SynFarmer synFarmer = new SynFarmer(MapsActivity.this);
            synFarmer.execute();
            String s = synFarmer.get();
            Log.d("18DecV2", "JSON==>" + s);

            JSONArray jsonArray = new JSONArray(s);
            titleString = new String[jsonArray.length()];
            String[] typeStrings = new String[jsonArray.length()];
            latDoubles = new double[jsonArray.length()];
            lngDoubles = new double[jsonArray.length()];
            String[] mem_idStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                titleString[i] = jsonObject.getString("mem_farm_name");
                typeStrings[i] = jsonObject.getString("mem_farm_type");
                latDoubles[i] = Double.parseDouble(jsonObject.getString("mem_farm_latitude"));
                lngDoubles[i] = Double.parseDouble(jsonObject.getString("mem_farm_longtitude"));
                mem_idStrings[i] = jsonObject.getString("mem_id");

                LatLng latLng1 = new LatLng(latDoubles[i], lngDoubles[i]);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng1)
                        .title(titleString[i])
                        .snippet(mem_idStrings[i]));
            }//for

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Log.d("21decV1", "titleMarker ==> " + marker.getTitle());
                    showAlert(marker.getTitle(), marker.getSnippet());

                    return true;
                }
            });

            //Create Spinner

            Spinner spinner = (Spinner) findViewById(R.id.spnGarden);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MapsActivity.this,
                    android.R.layout.simple_list_item_1, titleString);
            spinner.setAdapter(stringArrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    index[0] = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    index[0] = 0;
                }
            });

            buttonController();

        } catch (Exception e) {
            Log.d("18DecV2", "e ==>" + e.toString());
        }//try

        createUserMarker();


    }//onMapReady

    private void moveCenterMap(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void showAlert(final String title, final String mem_id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.dule_icon);
        builder.setTitle(title);
        builder.setMessage("คุณต้องการไปร้าน " + title + " หรือ ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MapsActivity.this, ListPostByUser.class);
                intent.putExtra("mem_id", mem_id);

                intent.putExtra("Login", loginStrings);
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

}//Main Class