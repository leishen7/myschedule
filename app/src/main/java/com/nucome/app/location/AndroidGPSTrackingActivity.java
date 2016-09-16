package com.nucome.app.location;

/**
 * Created by lei on 2016-09-03.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nucome.app.crm.R;
import com.squareup.picasso.Picasso;

public class AndroidGPSTrackingActivity extends Activity {
    private EditText nameEditText;
    Button btnShowLocation;

    ImageView mapView;
    private int width;
    private int height;
    // GPSTracker class
    GPSTracker gps;

    String mapURL="https://maps.googleapis.com/maps/api/staticmap?size=$widthx$height&maptype=roadmap&markers=color:blue%7Clabel:S%7C43.64581,-79.38581&markers=color:green%7Clabel:G%7C$$1,$$2&key=AIzaSyCtx-fxKrox_GBrnNASBtbBImo0Ev7YfKA";
  //  String mapURL="https://maps.googleapis.com/maps/api/staticmap?size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C43.64581,-79.38581&markers=color:green%7Clabel:G%7C43.77705,-79.41378&key=AIzaSyCtx-fxKrox_GBrnNASBtbBImo0Ev7YfKA
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);
        width = this.getResources().getDisplayMetrics().widthPixels;
        height = this.getResources().getDisplayMetrics().heightPixels;

        nameEditText=(EditText) findViewById(R.id.nameEditText);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        mapView = (ImageView) findViewById(R.id.mapImage);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(AndroidGPSTrackingActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){
                    mapURL= mapURL.replace("$width",Integer.toString(width));
                    mapURL= mapURL.replace("$height",Integer.toString(height));

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    double distance=DistanceCalculator.distance(latitude,longitude,43.64581,-79.38581,2);
                    nameEditText.setText(Double.toString(distance));
                    mapURL= mapURL.replace("$$1",Double.toString(latitude));
                    mapURL= mapURL.replace("$$2",Double.toString(longitude));
                    Picasso.with(mapView.getContext()).load(mapURL).into(mapView);

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }

}
