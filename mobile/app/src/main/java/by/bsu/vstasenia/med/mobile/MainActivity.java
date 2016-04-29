package by.bsu.vstasenia.med.mobile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Location currentLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, null)
//                .addApi(Api)

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 4/26/16 permissions denied
            ((TextView) findViewById(R.id.textCoord)).setText("Coord: no permission.");
        } else {
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (isNewLocationBetter(location)) {
                        updateLocation(location);
                    }
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        }
    }

    public void onAddCall(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://192.168.0.41:8080/rest/call/add_coor";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((TextView) findViewById(R.id.textMain)).setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView) findViewById(R.id.textMain)).setText("Ops... error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lastname", "Pushkin");
                if (currentLocation != null) {
                    params.put("latitude", Double.toString(currentLocation.getLatitude()));
                    params.put("longitude", Double.toString(currentLocation.getLongitude()));
                }
                return params;
            }
        };

        queue.add(request);
    }

    private boolean isNewLocationBetter(Location newLoc) {
        return currentLocation == null || (newLoc.getTime() - currentLocation.getTime() > 1000);
    }

    private void updateLocation(Location newLoc) {
        this.currentLocation = newLoc;
        TextView textCoord = (TextView) findViewById(R.id.textCoord);
        textCoord.setText(String.format("Coord: %s; %s", currentLocation.getLatitude(), currentLocation.getLongitude()));
    }
}
