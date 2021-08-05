package sg.edu.rp.s19024292.c302_p12;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import sg.edu.rp.s19024292.c302_p12.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Double latitude = getIntent().getDoubleExtra("latitude",0);
        Double longitude = getIntent().getDoubleExtra("longitude",0);

        if (latitude == 0 && longitude == 0){

            client = new AsyncHttpClient();
            client.addHeader("accountkey","cYsiznKuReChgmNVjkun9Q==");
            client.addHeader("Accept","application/json");

            client.get("http://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents", new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try{
                        Log.i("JSON Results: ", response.toString());
                        JSONArray jsonArray = response.getJSONArray("value");
                        for(int i = 0; i<jsonArray.length(); i ++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String type = jsonObj.getString("Type");
                            Double latitude = jsonObj.getDouble( "Latitude");
                            Double longitude = jsonObj.getDouble( "Longitude");
                            String message = jsonObj.getString("Message");

                            LatLng singapore = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(singapore).title(type));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,12));
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        String type = getIntent().getStringExtra("type");
        // Add a marker in Sydney and move the camera
        LatLng singapore = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(singapore).title(type));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,12));
    }
}