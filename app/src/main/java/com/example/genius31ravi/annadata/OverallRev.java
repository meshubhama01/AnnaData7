package com.example.genius31ravi.annadata;

import android.*;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class OverallRev extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    TextView temp1,city1,date1,desc1;
    public static final String TAG ="TAG";
    public static final int REQUEST_CODE=1000;
    private GoogleApiClient googleApiClient;
    private Location location;
    private TextView txtLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_rev);


        temp1=(TextView)findViewById(R.id.temp);
        city1=(TextView)findViewById(R.id.city);
        date1=(TextView)findViewById(R.id.date);
        desc1=(TextView)findViewById(R.id.desc);
        txtLocation=(TextView)findViewById(R.id.txtLocation);
        googleApiClient = new GoogleApiClient.Builder(OverallRev.this).addConnectionCallbacks(OverallRev.this)
                .addOnConnectionFailedListener(OverallRev.this).addApi(LocationServices.API).build();       // calling the activities
        find_weather();


    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG,"Connected");
        showTheUserLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"Connection Suspended");

    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG,"Connection Failed");
        // user must have access to google play services
        if(connectionResult.hasResolution())
        {
            try
            {
                connectionResult.startResolutionForResult(OverallRev.this,REQUEST_CODE);
            }
            catch (IntentSender.SendIntentException e) {
                e.printStackTrace() ;
            }
        }
        else{
            Toast.makeText(OverallRev.this,"Play store is unable to fetch gps location",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode== RESULT_OK){

            googleApiClient.connect();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(googleApiClient!=null){
            googleApiClient.connect();
        }
    }

    // custom methods
    double latitude=location.getLatitude();
    double longitude=location.getLongitude();

    private void showTheUserLocation(){
        // run time permission
        int permissionCheck= ContextCompat.checkSelfPermission(OverallRev.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED){

            FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi; // use the fused.. api interface
            location= fusedLocationProviderApi.getLastLocation(googleApiClient);

            if(location!=null){

                txtLocation.setText("Longitude :- "+longitude + " " + " Latitude" + latitude);

            }

            else
            {
                txtLocation.setText("Sorry for the interrupt");
            }

        }
        else{
            txtLocation.setText("The app not allowed to access the location ");
            ActivityCompat.requestPermissions(OverallRev.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }

    }
   // RequestBody formbody= new FormBody().Builder().add("Longitude",longitude).add("Latitude",latitude).build();
  //  Request request= new Request() {
   // }
//
    private void find_weather() {
// accessing from the local host

       String url ="https://drive.google.com/open?id=1Rzb260zjz-rbJYOZ67f1YbhtZHx4c0hy";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {

                    JSONObject main_object = response.getJSONObject("main");// go to main obj for the data
                    JSONArray array= response.getJSONArray("weather");
                    JSONObject obj=array.getJSONObject(0);
                    String temp=String.valueOf(main_object.getDouble("temp"));
                    String desc=obj.getString("description");
                    String city=response.getString("name");

                    temp1.setText(temp+"");
                    desc1.setText(desc);
                    city1.setText(city);
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat sdf= new SimpleDateFormat("EEEE-MM-DD");
                    String formatted_date=sdf.format(calendar.getTime());
                    date1.setText(formatted_date);

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);


    }
}
