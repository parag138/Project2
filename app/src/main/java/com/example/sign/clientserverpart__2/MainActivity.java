package com.example.sign.clientserverpart__2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView latitude,longitude,city,editloc;
    Button search;
    String lati,longi,cit;
    String address;
    Double latitudes,longitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {
        latitude = (TextView) findViewById(R.id.latit);
        longitude = (TextView) findViewById(R.id.lon);
        city = (TextView) findViewById(R.id.city);
        editloc = (EditText) findViewById(R.id.edit_location);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = String.valueOf(editloc.getText());
                //  getLatLongFromAddress(addr);

                new NetworkThread().execute(address);
                Log.d("Hello", "On network thread");
            }
        });

        Button locationbtn = (Button)findViewById(R.id.locationonmap);
        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lati!=null && longi!= null) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("latitude",lati);
                    intent.putExtra( "longitude",longi);
                    startActivity(intent);
                }
            }
        });
    }

    public class NetworkThread extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            double lat = 0.0, lng = 0.0;
            Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocationName(address, 1);
                if (addresses.size() > 0) {
                    longitudes = addresses.get(0).getLongitude();
                    latitudes=addresses.get(0).getLatitude();
                    lati = String.valueOf(addresses.get(0).getLatitude());
                    longi = String.valueOf(addresses.get(0).getLongitude());
                    cit = String.valueOf(addresses.get(0).getLocality());
                    Log.d("Hello","On network thread");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            //super.onPostExecute(aVoid);
            latitude.setText("Latitude : "+lati);
            longitude.setText("Longitude : "+longi);
            city.setText(" CITY : " + cit);
            editloc.setText("");

        }

    }
}
