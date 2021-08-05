package sg.edu.rp.s19024292.c302_p12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client;
    ListView lv;
    ArrayList<Incident> al;
    ArrayAdapter<Incident> aa;
    Button btn, btnall;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;
    CollectionReference colRef;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btn = findViewById(R.id.btn);
        btnall = findViewById(R.id.btnAll);
        al = new ArrayList<Incident>();
        aa = new IncidentAdapter(this,R.layout.row,al);

        db = FirebaseFirestore.getInstance();
        colRef = db.collection("incidents");

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


                        Incident incident = new Incident(type, latitude, longitude, message);
                        al.add(incident);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                aa = new IncidentAdapter(getApplicationContext(), R.layout.row, al);
                lv.setAdapter(aa);

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("latitude",al.get(position).getLatitude());
                intent.putExtra("longitude",al.get(position).getLongitude());
                intent.putExtra("type",al.get(position).getType());
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < al.size();i++) {
                            colRef.add(al.get(i));
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);

                startActivity(intent);
            }
        });
    }
}